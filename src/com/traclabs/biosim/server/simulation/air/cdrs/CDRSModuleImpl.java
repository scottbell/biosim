package com.traclabs.biosim.server.simulation.air.cdrs;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleOperations;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState;
import com.traclabs.biosim.idl.simulation.environment.Air;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinitionImpl;

public class CDRSModuleImpl extends SimBioModuleImpl implements CDRSModuleOperations, PowerConsumerOperations, AirConsumerOperations, AirProducerOperations, CO2ProducerOperations
, GreyWaterConsumerOperations, GreyWaterProducerOperations{
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

    private GreyWaterProducerDefinitionImpl myGreyWaterProducerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;
    
    private CDRSState myState = CDRSState.inactive;
    private CDRSArmedStatus myArmedStatus = CDRSArmedStatus.not_armed;
    private CDRSValveState myAirInletValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirInletValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myAirReturnValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirReturnValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2IsolationValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2IsolationValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2VentValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2VentValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myWaterPumpState = CDRSPowerState.off;
    private CDRSCommandStatus myWaterPumpEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myBlowerState = CDRSPowerState.off;
    private CDRSCommandStatus myBlowerEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSDayNightState myDayNightState = CDRSDayNightState.day;
    
    private static final int AIR_INLET_VALVE_POWER_INDEX = 0;
    private static final int AIR_RETURN_VALVE_POWER_INDEX = 1;
    private static final int CO2_ISOLATION_VALVE_POWER_INDEX = 2;
    private static final int CO2_VENT_VALVE_POWER_INDEX = 3;
    private static final int CDRS_POWER_INDEX = 4;
    private static final int PRIMARY_HEATER_POWER_INDEX = 5;
    private static final int SECONDARY_HEATER_POWER_INDEX = 6;
    private static final int WATER_PUMP_POWER_INDEX = 7;
    private static final int BLOWER_POWER_INDEX = 8;
    
    private float myPrimaryHeaterProduction = 0;
    private float mySecondaryHeaterProduction = 0;
    private static final float MAX_HEATER_PRODUCTION = 200;
    private CDRSState myStateToTransition = CDRSState.transitioning;
    private final static int TICKS_TO_WAIT = 20;
    private int myTicksWaited = 0;
    
    public CDRSModuleImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl(this);
        myGreyWaterProducerDefinitionImpl = new GreyWaterProducerDefinitionImpl(this);
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl(this);
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl(this);
    }
    
    public void tick() {
        super.tick();
        boolean gatheredProperPower =  gatherPower();
        if (!gatheredProperPower) {
        	transitionToInactive();
        	return;
        }
        gatherAir();
        gatherWater();
        if (myStateToTransition != CDRSState.transitioning){
        	myTicksWaited++;
        	if (myTicksWaited >= TICKS_TO_WAIT)
        		transitionState();
        }
    }

    private void gatherWater() {
		float waterGathered = myGreyWaterConsumerDefinitionImpl.getMostResourceFromStores();
		if ((getState() == CDRSState.init) || (getState() == CDRSState.inactive) || (getState() == CDRSState.transitioning)){
			myGreyWaterProducerDefinitionImpl.pushResourceToStores(waterGathered, 22f);
		}
		else{
			myGreyWaterProducerDefinitionImpl.pushResourceToStores(waterGathered, 30f);
		}
	}

	private void gatherAir() {
        Air airConsumed = myAirConsumerDefinitionImpl.getMostAirFromEnvironments();
        float co2Produced = 0;
        if (myState == CDRSState.dual_bed){
        	co2Produced = airConsumed.co2Moles;
        	airConsumed.co2Moles = 0;
        }
        else if (myState == CDRSState.single_bed){
        	co2Produced = airConsumed.co2Moles / 2;
        	airConsumed.co2Moles = co2Produced;
        }
        
        myAirProducerDefinitionImpl.pushAirToEnvironment(airConsumed, 0);
        myCO2ProducerDefinitionImpl.pushResourceToStores(co2Produced);
	}

	private void transitionState() {
    	if (myStateToTransition == CDRSState.init)
			transitionToInit();
		else if (myStateToTransition == CDRSState.standby)
			transitionToStandby();
		else if (myStateToTransition == CDRSState.dual_bed)
			transitionToDualBed();
		else if (myStateToTransition == CDRSState.single_bed)
			transitionToSingleBed();
		else if (myStateToTransition == CDRSState.inactive)
			transitionToInactive();
    	myTicksWaited = 0;
    	myStateToTransition = CDRSState.transitioning;
	}

	private boolean gatherPower() {
		float cdrsPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(CDRS_POWER_INDEX);
		float airInletPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(AIR_INLET_VALVE_POWER_INDEX);
		float airReturnPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(AIR_RETURN_VALVE_POWER_INDEX);
		float co2IsolationPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(CO2_ISOLATION_VALVE_POWER_INDEX);
		float co2VentPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(CO2_VENT_VALVE_POWER_INDEX);
		float primaryHeaterPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(PRIMARY_HEATER_POWER_INDEX);
		float secondaryHeaterPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(SECONDARY_HEATER_POWER_INDEX);
		float waterPumpPowerConsumed = myPowerConsumerDefinitionImpl.getMostResourceFromStore(WATER_PUMP_POWER_INDEX);
		float blowerPowerConsumed =  myPowerConsumerDefinitionImpl.getMostResourceFromStore(BLOWER_POWER_INDEX);
		if (cdrsPowerConsumed <= 0 || airInletPowerConsumed <= 0 || airReturnPowerConsumed <= 0 ||
				co2IsolationPowerConsumed <= 0 || co2VentPowerConsumed <= 0 ||
				primaryHeaterPowerConsumed <= 0 || secondaryHeaterPowerConsumed <= 0 ||
				waterPumpPowerConsumed <= 0 || blowerPowerConsumed <= 0) {
			return false;
		}
		return true;
	}

	/**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinitionImpl.reset();
        myGreyWaterConsumerDefinitionImpl.reset();
        myGreyWaterProducerDefinitionImpl.reset();
        myAirConsumerDefinitionImpl.reset();
        myAirProducerDefinitionImpl.reset();
        myCO2ProducerDefinitionImpl.reset();
        
        myState = CDRSState.inactive;
        myArmedStatus = CDRSArmedStatus.not_armed;
        myAirInletValveState = CDRSValveState.open;
        myAirInletValveEnabledStatus = CDRSCommandStatus.inhibited;
        myAirReturnValveState = CDRSValveState.open;
        myAirReturnValveEnabledStatus = CDRSCommandStatus.inhibited;
        myCO2IsolationValveState = CDRSValveState.open;
        myCO2IsolationValveEnabledStatus = CDRSCommandStatus.inhibited;
        myCO2VentValveState = CDRSValveState.open;
        myCO2VentValveEnabledStatus = CDRSCommandStatus.inhibited;
        myWaterPumpState = CDRSPowerState.off;
        myWaterPumpEnabledStatus = CDRSCommandStatus.inhibited;
        myBlowerState = CDRSPowerState.off;
        myBlowerEnabledStatus = CDRSCommandStatus.inhibited;
        myDayNightState = CDRSDayNightState.day;

        myPrimaryHeaterProduction = 0;
        mySecondaryHeaterProduction = 0;
        transitionToInactive();
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

	public CO2ProducerDefinition getCO2ProducerDefinition() {
		return myCO2ProducerDefinitionImpl.getCorbaObject();
	}

	public AirProducerDefinition getAirProducerDefinition() {
		return myAirProducerDefinitionImpl.getCorbaObject();
	}

	public AirConsumerDefinition getAirConsumerDefinition() {
		return myAirConsumerDefinitionImpl.getCorbaObject();
	}

	public void setState(CDRSState state) {
		if (getArmedStatus() == CDRSArmedStatus.armed){
			if (transitionAllowed(state)){
				myState = CDRSState.transitioning;
				myStateToTransition = state;
				myArmedStatus = CDRSArmedStatus.not_armed;
			}
		}
	}

	private boolean transitionAllowed(CDRSState stateToTransition) {
		if (myState == CDRSState.inactive){
			return (stateToTransition == CDRSState.init);
		}
		else if (myState == CDRSState.init){
			return ((stateToTransition == CDRSState.inactive) || (stateToTransition == CDRSState.standby));
		}
		else if (myState == CDRSState.standby){
			return ((stateToTransition == CDRSState.inactive) || (stateToTransition == CDRSState.dual_bed)|| (stateToTransition == CDRSState.single_bed));
		}
		else if (myState == CDRSState.single_bed){
			return (stateToTransition == CDRSState.inactive);
		}
		else if (myState == CDRSState.dual_bed){
			return (stateToTransition == CDRSState.inactive);
		}
		return false;
	}

	public void setArmedStatus(CDRSArmedStatus status) {
		this.myArmedStatus = status; 
	}

	public void setAirInletValveState(CDRSValveState state) {
		if (getAirInletValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myAirInletValveState = state;
	}

	public void setAirInletValveArmedStatus(CDRSCommandStatus status) {
		this.myAirInletValveEnabledStatus = status;
	}

	public void setAirReturnValveState(CDRSValveState state) {
		if (getAirReturnValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myAirReturnValveState = state;
	}

	public void setAirReturnValveArmedStatus(CDRSCommandStatus status) {
		this.myAirReturnValveEnabledStatus = status;
	}

	public void setCO2IsolationValveState(CDRSValveState state) {
		if (getCO2IsolationValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myCO2IsolationValveState = state;
	}

	public void setCO2IsolationValveArmedStatus(CDRSCommandStatus status) {
		this.myCO2IsolationValveEnabledStatus = status;
	}

	public void setCO2VentValveState(CDRSValveState state) {
		if (getCO2VentValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myCO2VentValveState = state;
	}

	public void setCO2VentValveArmedStatus(CDRSCommandStatus status) {
		this.myCO2VentValveEnabledStatus = status;
	}

	public void setWaterPumpState(CDRSPowerState state) {
		if (getWaterPumpArmedStatus() == CDRSCommandStatus.enabled)
			this.myWaterPumpState = state;
	}

	public void setWaterPumpArmedStatus(CDRSCommandStatus status) {
		this.myWaterPumpEnabledStatus = status;
	}

	public void setBlowerState(CDRSPowerState state) {
		if (getBlowerArmedStatus() == CDRSCommandStatus.enabled)
			this.myBlowerState = state;
	}

	public void setBlowerArmedStatus(CDRSCommandStatus status) {
		this.myBlowerEnabledStatus = status;
	}

	public void setDayNightState(CDRSDayNightState state) {
		this.myDayNightState = state;
	}

	public CDRSState getState() {
		return myState;
	}

	public CDRSArmedStatus getArmedStatus() {
		return myArmedStatus;
	}

	public CDRSValveState getAirInletValveState() {
		return myAirInletValveState;
	}

	public CDRSCommandStatus getAirInletValveArmedStatus() {
		return myAirInletValveEnabledStatus;
	}

	public CDRSValveState getAirReturnValveState() {
		return myAirReturnValveState;
	}

	public CDRSCommandStatus getAirReturnValveArmedStatus() {
		return myAirReturnValveEnabledStatus;
	}

	public CDRSValveState getCO2IsolationValveState() {
		return myCO2IsolationValveState;
	}

	public CDRSCommandStatus getCO2IsolationValveArmedStatus() {
		return myCO2IsolationValveEnabledStatus;
	}

	public CDRSValveState getCO2VentValveState() {
		return myCO2VentValveState;
	}

	public CDRSCommandStatus getCO2VentValveArmedStatus() {
		return myCO2VentValveEnabledStatus;
	}

	public CDRSPowerState getWaterPumpState() {
		return myWaterPumpState;
	}

	public CDRSCommandStatus getWaterPumpArmedStatus() {
		return myWaterPumpEnabledStatus;
	}

	public CDRSPowerState getBlowerState() {
		return myBlowerState;
	}

	public CDRSCommandStatus getBlowerArmedStatus() {
		return myBlowerEnabledStatus;
	}

	public CDRSDayNightState getDayNightState() {
		return myDayNightState;
	}
	
	private void transitionToInit() {
		myBlowerState = CDRSPowerState.on;
		myWaterPumpState = CDRSPowerState.on;
		myAirConsumerDefinitionImpl.setDesiredFlowRate(myAirConsumerDefinitionImpl.getMaxFlowRate(0) / 2, 0);
		myAirProducerDefinitionImpl.setDesiredFlowRate(myAirProducerDefinitionImpl.getMaxFlowRate(0) / 2, 0);
		myGreyWaterConsumerDefinitionImpl.setDesiredFlowRate(myGreyWaterConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myPrimaryHeaterProduction = 0;
		mySecondaryHeaterProduction = 0;
		myState = CDRSState.init;
	}

	private void transitionToInactive() {
		myBlowerState = CDRSPowerState.off;
		myWaterPumpState = CDRSPowerState.off;
		myAirConsumerDefinitionImpl.setDesiredFlowRate(0, 0);
		myAirProducerDefinitionImpl.setDesiredFlowRate(0, 0);
		myGreyWaterConsumerDefinitionImpl.setDesiredFlowRate(0, 0);
		myPrimaryHeaterProduction = 0;
		mySecondaryHeaterProduction = 0;
		myState = CDRSState.inactive;
	}

	private void transitionToSingleBed() {
		myBlowerState = CDRSPowerState.on;
		myWaterPumpState = CDRSPowerState.on;
		myAirConsumerDefinitionImpl.setDesiredFlowRate(myAirConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myAirProducerDefinitionImpl.setDesiredFlowRate(myAirProducerDefinitionImpl.getMaxFlowRate(0), 0);
		myGreyWaterConsumerDefinitionImpl.setDesiredFlowRate(myGreyWaterConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myPrimaryHeaterProduction = MAX_HEATER_PRODUCTION * 0.5f;
		mySecondaryHeaterProduction = 0;
		myState = CDRSState.single_bed;
	}

	private void transitionToDualBed() {
		myBlowerState = CDRSPowerState.on;
		myWaterPumpState = CDRSPowerState.on;
		myState = CDRSState.dual_bed;
		myAirConsumerDefinitionImpl.setDesiredFlowRate(myAirConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myAirProducerDefinitionImpl.setDesiredFlowRate(myAirProducerDefinitionImpl.getMaxFlowRate(0), 0);
		myGreyWaterConsumerDefinitionImpl.setDesiredFlowRate(myGreyWaterConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myPrimaryHeaterProduction = MAX_HEATER_PRODUCTION;
		mySecondaryHeaterProduction = 0;
	}

	private void transitionToStandby() {
		myBlowerState = CDRSPowerState.on;
		myWaterPumpState = CDRSPowerState.on;
		myState = CDRSState.standby;
		myAirConsumerDefinitionImpl.setDesiredFlowRate(myAirConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myAirProducerDefinitionImpl.setDesiredFlowRate(myAirProducerDefinitionImpl.getMaxFlowRate(0), 0);
		myPrimaryHeaterProduction = MAX_HEATER_PRODUCTION * 0.25f;
		myGreyWaterConsumerDefinitionImpl.setDesiredFlowRate(myGreyWaterConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		mySecondaryHeaterProduction = 0;
	}

	public float getPrimaryHeatProduction() {
		return myPrimaryHeaterProduction;
	}

	public float getSecondaryHeatProduction() {
		return mySecondaryHeaterProduction;
	}
	
	@Override
	protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "Broken";
    }
    
	@Override
    protected void performMalfunctions() {
		for (Malfunction malfunction : myMalfunctions.values()) {
			malfunction.setPerformed(true);
		}
		if (myMalfunctions.values().size() > 0) {
			myPowerConsumerDefinitionImpl.malfunction();
			myGreyWaterConsumerDefinitionImpl.malfunction();
			myGreyWaterProducerDefinitionImpl.malfunction();
			myAirProducerDefinitionImpl.malfunction();
			myAirConsumerDefinitionImpl.malfunction();
			myCO2ProducerDefinitionImpl.malfunction();
			transitionToInactive();
		}
    }

}