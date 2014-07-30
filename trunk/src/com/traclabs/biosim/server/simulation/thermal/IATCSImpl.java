package com.traclabs.biosim.server.simulation.thermal;

import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.thermal.IATCSOperations;
import com.traclabs.biosim.idl.simulation.thermal.IATCSState;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations;
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
	private IATCSState iatcsState = IATCSState.idle;
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

    /**
     * Attempts to consume resource (power and dryWaste) for IATCS
     */
    private void consumeResources() {
        gatherPower();
        
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
		else if (iatcsState == IATCSState.operational){
			return (stateToTransition == IATCSState.idle);
		}
		return false;
	}
}