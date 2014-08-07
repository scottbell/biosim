package com.traclabs.biosim.server.simulation.thermal;

import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.thermal.IATCSActivation;
import com.traclabs.biosim.idl.simulation.thermal.IATCSOperations;
import com.traclabs.biosim.idl.simulation.thermal.IATCSState;
import com.traclabs.biosim.idl.simulation.thermal.IFHXBypassState;
import com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus;
import com.traclabs.biosim.idl.simulation.thermal.IFHXValveState;
import com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatus;
import com.traclabs.biosim.idl.simulation.thermal.SoftwareState;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterStoreHelper;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinitionImpl;

/**
 * The IATCS is the Internal Active Thermal Control System. It takes power and cools water
 * 
 * @author Scott Bell
 */

public class IATCSImpl extends SimBioModuleImpl implements
        IATCSOperations, PowerConsumerOperations,
        GreyWaterConsumerOperations, GreyWaterProducerOperations {
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

    private GreyWaterProducerDefinitionImpl myGreyWaterProducerDefinitionImpl;

    //During any given tick, this much power is needed for the IATCS
    // to run at all
    private static final float POWER_NEEDED_BASE = 100;

    //Flag to determine if the IATCS has enough power to function
    private boolean hasEnoughPower = false;

    //The power consumed (in watts) by the IATCS at the current tick
    private float currentPowerConsumed = 0f;

    //References to the servers the IATCS takes/puts resources
    private float myProductionRate = 1f;
    
    private IATCSState stateToTransition = IATCSState.transitioning;
    private final static int TICKS_TO_WAIT = 20;
    private int ticksWaited = 0;
    
	private IATCSState iatcsState = IATCSState.idle;
	private IATCSActivation activateState = IATCSActivation.notInProgress;
	private SoftwareState iatcsSoftwareState = SoftwareState.shutdown;
	

	private SoftwareState twvmSoftwareState = SoftwareState.shutdown;
	private PPAPumpSpeedStatus ppaPumpSpeedCommandStatus = PPAPumpSpeedStatus.notArmed;
	private float pumpSpeed = 0; //rpm
    
    private IFHXBypassState bypassValveState = IFHXBypassState.bypass;
    private IFHXValveCommandStatus bypassValveCommandStatus = IFHXValveCommandStatus.inhibited;
    
    private IFHXValveState isloationValveState = IFHXValveState.closed;
    private IFHXValveCommandStatus isolationValveCommandStatus = IFHXValveCommandStatus.inhibited;
    
    private SoftwareState heaterSoftwareState = SoftwareState.shutdown;

    public IATCSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl(this);
        myGreyWaterProducerDefinitionImpl = new GreyWaterProducerDefinitionImpl(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
        return myGreyWaterProducerDefinitionImpl.getCorbaObject();
    }

    /**
     * Resets production/consumption levels
     */
    public void reset() {
        super.reset();
        currentPowerConsumed = 0f;
        myPowerConsumerDefinitionImpl.reset();
        myGreyWaterConsumerDefinitionImpl.reset();
        myGreyWaterProducerDefinitionImpl.reset();
        hasEnoughPower = false;
        currentPowerConsumed = 0f;
        myProductionRate = 1f;
        stateToTransition = IATCSState.transitioning;
        ticksWaited = 0;
    	iatcsState = IATCSState.idle;
    	activateState = IATCSActivation.notInProgress;
    	iatcsSoftwareState = SoftwareState.shutdown;
    	twvmSoftwareState = SoftwareState.shutdown;
    	ppaPumpSpeedCommandStatus = PPAPumpSpeedStatus.notArmed;
    	pumpSpeed = 0;
        bypassValveState = IFHXBypassState.bypass;
        bypassValveCommandStatus = IFHXValveCommandStatus.inhibited;
        isloationValveState = IFHXValveState.closed;
        isolationValveCommandStatus = IFHXValveCommandStatus.inhibited;
        heaterSoftwareState = SoftwareState.shutdown;
    }

    /**
     * Returns the power consumed (in watts) by the IATCS during the
     * current tick
     * 
     * @return the power consumed (in watts) by the IATCS during the
     *         current tick
     */
    public float getPowerConsumed() {
        return currentPowerConsumed;
    }

    /**
     * Checks whether IATCS has enough power or not
     * 
     * @return <code>true</code> if the IATCS has enough power,
     *         <code>false</code> if not.
     */
    public boolean hasPower() {
        return hasEnoughPower;
    }

    /**
     * Attempts to collect enough power from the Power PS to run the IATCS
     * for one tick.
     */
    private void gatherPower() {
    	float powerNeeded = POWER_NEEDED_BASE * getTickLength();
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getResourceFromStores(powerNeeded);
        if (currentPowerConsumed < powerNeeded)
            hasEnoughPower = false;
        else
            hasEnoughPower = true;
    }
    
    private void gatherWater() {
    	if ((getIsloationValveState() == IFHXValveState.closed) && (getBypassValveState() == IFHXBypassState.flowthrough)){
    		//water can't get to heat exchange
    		return;
    	}
 		float waterGathered = myGreyWaterConsumerDefinitionImpl.getMostResourceFromStores();
 		if ((getIatcsState() == IATCSState.operational) && (getBypassValveState() == IFHXBypassState.flowthrough) && (getIsloationValveState() == IFHXValveState.open)){
 			myGreyWaterProducerDefinitionImpl.pushResourceToStores(waterGathered, 10f);
 		}
 		else{
 			if (myGreyWaterConsumerDefinitionImpl.getStores().length > 0){
 				WaterStore inputWaterStore = WaterStoreHelper.narrow(myGreyWaterConsumerDefinitionImpl.getStores()[0]);
 				float inputTemperature = inputWaterStore.getCurrentTemperature();
 				myGreyWaterProducerDefinitionImpl.pushResourceToStores(waterGathered, inputTemperature);
 			}
 		}
 	}

    /**
     * Attempts to consume resource (power and dryWaste) for IATCS
     */
    private void consumeResources() {
        gatherPower();
        if (hasEnoughPower)
        	gatherWater();
    }

    private void setProductionRate(float pProductionRate) {
        myProductionRate = pProductionRate;
    }

    protected void performMalfunctions() {
        float productionRate = 1f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            }
        }
        setProductionRate(productionRate);
    }

    /**
     * When ticked, the IATCS does the following: 1) attempts to collect
     * references to various server (if not already done). 2) consumes power and
     * dryWaste. 3) creates food (if possible)
     */
    public void tick() {
        super.tick();
        consumeResources();
        if (iatcsState != IATCSState.transitioning){
        	ticksWaited++;
        	if (ticksWaited >= TICKS_TO_WAIT)
        		transitionState();
        }
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        StringBuffer returnBuffer = new StringBuffer();
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            returnBuffer.append("Severe ");
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            returnBuffer.append("Medium ");
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            returnBuffer.append("Low ");
        if (pLength == MalfunctionLength.TEMPORARY_MALF)
            returnBuffer.append("Production Rate Decrease (Temporary)");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Production Rate Decrease (Permanent)");
        return returnBuffer.toString();
    }

    public void log() {
        myLogger.debug("power_needed=" + POWER_NEEDED_BASE);
        myLogger.debug("has_enough_power=" + hasEnoughPower);
        myLogger.debug("current_power_consumed=" + currentPowerConsumed);
    }
    
    private boolean transitionAllowed(IATCSState stateToTransition) {
		if (iatcsState == IATCSState.idle){
			return (stateToTransition == IATCSState.operational);
		}
		else if ((iatcsState == IATCSState.operational) && (iatcsSoftwareState == SoftwareState.softwareArmed)){
			return (stateToTransition == IATCSState.idle);
		}
		return false;
	}
    
    private void transitionState() {
    	if (stateToTransition == IATCSState.idle)
			transitionToIdle();
		else if (stateToTransition == IATCSState.operational)
			transitionToOperational();
    	ticksWaited = 0;
    	stateToTransition = IATCSState.transitioning;
	}
    
    private void transitionToIdle() {
    	pumpSpeed = 0f;
    	activateState = IATCSActivation.inProgress;
		iatcsState = IATCSState.idle;
		iatcsSoftwareState = SoftwareState.shutdown;
		twvmSoftwareState = SoftwareState.shutdown;
	}
    
    private void transitionToOperational() {
		pumpSpeed = 9250;
    	activateState = IATCSActivation.notInProgress;
		iatcsState = IATCSState.operational;
		iatcsSoftwareState = SoftwareState.running;
		twvmSoftwareState = SoftwareState.running;
	}
    
    public IATCSState getIatcsState() {
		return iatcsState;
	}

	public void setIatcsState(IATCSState state) {
		if (transitionAllowed(stateToTransition)){
			this.iatcsState = IATCSState.transitioning;
			stateToTransition = state;
		}
	}

	public IATCSActivation getActivateState() {
		return activateState;
	}

	public void setActivateState(IATCSActivation activateState) {
		this.activateState = activateState;
	}

	public SoftwareState getIatcsSoftwareState() {
		return iatcsSoftwareState;
	}

	public void setIatcsSoftwareState(SoftwareState iatcsSoftwareState) {
		this.iatcsSoftwareState = iatcsSoftwareState;
	}

	public SoftwareState getTwvmSoftwareState() {
		return twvmSoftwareState;
	}

	public void setTwvmSoftwareState(SoftwareState twvmSoftwareState) {
		this.twvmSoftwareState = twvmSoftwareState;
	}

	public PPAPumpSpeedStatus getPpaPumpSpeedCommandStatus() {
		return ppaPumpSpeedCommandStatus;
	}

	public void setPpaPumpSpeedCommandStatus(PPAPumpSpeedStatus ppaPumpSpeedCommandStatus) {
		this.ppaPumpSpeedCommandStatus = ppaPumpSpeedCommandStatus;
	}

	public float getPumpSpeed() {
		return pumpSpeed;
	}

	public void setPumpSpeed(float pumpSpeed) {
		if (ppaPumpSpeedCommandStatus == PPAPumpSpeedStatus.pumpArmed){
			this.pumpSpeed = pumpSpeed;
			ppaPumpSpeedCommandStatus = PPAPumpSpeedStatus.notArmed;
		}
	}

	public IFHXBypassState getBypassValveState() {
		return bypassValveState;
	}

	public void setBypassValveState(IFHXBypassState bypassValveState) {
		if (bypassValveCommandStatus == IFHXValveCommandStatus.enabled)
			this.bypassValveState = bypassValveState;
	}

	public IFHXValveCommandStatus getBypassValveCommandStatus() {
		return bypassValveCommandStatus;
	}

	public void setBypassValveCommandStatus(IFHXValveCommandStatus bypassValveCommandStatus) {
		this.bypassValveCommandStatus = bypassValveCommandStatus;
	}

	public IFHXValveState getIsloationValveState() {
		return isloationValveState;
	}

	public void setIsloationValveState(IFHXValveState isloationValveState) {
		if (isolationValveCommandStatus == IFHXValveCommandStatus.enabled)
			this.isloationValveState = isloationValveState;
	}

	public IFHXValveCommandStatus getIsolationValveCommandStatus() {
		return isolationValveCommandStatus;
	}

	public void setIsolationValveCommandStatus(IFHXValveCommandStatus isolationValveCommandStatus) {
		this.isolationValveCommandStatus = isolationValveCommandStatus;
	}

	public SoftwareState getHeaterSoftwareState() {
		return heaterSoftwareState;
	}

	public void setHeaterSoftwareState(SoftwareState newHeaterSoftwareState) {
		if ((heaterSoftwareState == SoftwareState.softwareArmed) || (newHeaterSoftwareState == SoftwareState.softwareArmed)){
			this.heaterSoftwareState = newHeaterSoftwareState;
		}
	}
}