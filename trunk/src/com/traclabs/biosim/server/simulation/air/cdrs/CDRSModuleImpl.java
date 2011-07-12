package com.traclabs.biosim.server.simulation.air.cdrs;

import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleOperations;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionImpl;

public class CDRSModuleImpl extends SimBioModuleImpl implements CDRSModuleOperations, PowerConsumerOperations, AirConsumerOperations, AirProducerOperations, CO2ProducerOperations
, PotableWaterConsumerOperations{
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;
    
    private CDRSState myState = CDRSState.init;
    private CDRSArmedStatus myArmedStatus = CDRSArmedStatus.not_armed;
    private CDRSValveState myAirInletValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirInletValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myAirReturnValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirReturnValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2IsolationValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2IsolationValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2VentValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2VentValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myWaterPumpState = CDRSPowerState.on;
    private CDRSCommandStatus myWaterPumpEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myBlowerState = CDRSPowerState.on;
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
    
    public CDRSModuleImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl(this);
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl(this);
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl(this);
    }
    
    public void tick() {
        super.tick();
        gatherPower();
    }


    private void gatherPower() {
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(AIR_INLET_VALVE_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(AIR_RETURN_VALVE_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(CO2_ISOLATION_VALVE_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(CO2_VENT_VALVE_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(CDRS_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(PRIMARY_HEATER_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(SECONDARY_HEATER_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(WATER_PUMP_POWER_INDEX);
    	myPowerConsumerDefinitionImpl.getMostResourceFromStore(BLOWER_POWER_INDEX);
	}

	/**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinitionImpl.reset();
        myPotableWaterConsumerDefinitionImpl.reset();
        myAirConsumerDefinitionImpl.reset();
        myAirProducerDefinitionImpl.reset();
        myCO2ProducerDefinitionImpl.reset();
        
        myState = CDRSState.init;
        myArmedStatus = CDRSArmedStatus.not_armed;
        myAirInletValveState = CDRSValveState.open;
        myAirInletValveEnabledStatus = CDRSCommandStatus.enabled;
        myAirReturnValveState = CDRSValveState.open;
        myAirReturnValveEnabledStatus = CDRSCommandStatus.enabled;
        myCO2IsolationValveState = CDRSValveState.open;
        myCO2IsolationValveEnabledStatus = CDRSCommandStatus.enabled;
        myCO2VentValveState = CDRSValveState.open;
        myCO2VentValveEnabledStatus = CDRSCommandStatus.enabled;
        myWaterPumpState = CDRSPowerState.on;
        myWaterPumpEnabledStatus = CDRSCommandStatus.enabled;
        myBlowerState = CDRSPowerState.on;
        myBlowerEnabledStatus = CDRSCommandStatus.enabled;
        myDayNightState = CDRSDayNightState.day;

        myPrimaryHeaterProduction = 0;
        mySecondaryHeaterProduction = 0;
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

	public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
		return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
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
			this.myState = CDRSState.transitioning;
			if (state == CDRSState.init)
				transitionToInit();
			else if (state == CDRSState.standby)
				transitionToStandby();
			else if (state == CDRSState.dual_bed)
				transitionToDualBed();
			else if (state == CDRSState.single_bed)
				transitionToSingleBed();
			else if (state == CDRSState.inactive)
				transitionToInactive();
		}
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
		myPotableWaterConsumerDefinitionImpl.setDesiredFlowRate(myPotableWaterConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myPrimaryHeaterProduction = 0;
		mySecondaryHeaterProduction = 0;
		myState = CDRSState.init;
	}

	private void transitionToInactive() {
		myBlowerState = CDRSPowerState.off;
		myWaterPumpState = CDRSPowerState.off;
		myAirConsumerDefinitionImpl.setDesiredFlowRate(0, 0);
		myAirProducerDefinitionImpl.setDesiredFlowRate(0, 0);
		myPotableWaterConsumerDefinitionImpl.setDesiredFlowRate(0, 0);
		myPrimaryHeaterProduction = 0;
		mySecondaryHeaterProduction = 0;
		myState = CDRSState.inactive;
	}

	private void transitionToSingleBed() {
		myBlowerState = CDRSPowerState.on;
		myWaterPumpState = CDRSPowerState.on;
		myAirConsumerDefinitionImpl.setDesiredFlowRate(myAirConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		myAirProducerDefinitionImpl.setDesiredFlowRate(myAirProducerDefinitionImpl.getMaxFlowRate(0), 0);
		myPotableWaterConsumerDefinitionImpl.setDesiredFlowRate(myPotableWaterConsumerDefinitionImpl.getMaxFlowRate(0), 0);
		
		myState = CDRSState.single_bed;
	}

	private void transitionToDualBed() {
		myBlowerState = CDRSPowerState.on;
		myWaterPumpState = CDRSPowerState.on;
		myState = CDRSState.dual_bed;
	}

	private void transitionToStandby() {
		myBlowerState = CDRSPowerState.on;
		myWaterPumpState = CDRSPowerState.on;
		myState = CDRSState.standby;
	}

	public float getPrimaryHeatProduction() {
		return myPrimaryHeaterProduction;
	}

	public float getSecondaryHeatProduction() {
		return mySecondaryHeaterProduction;
	}

}