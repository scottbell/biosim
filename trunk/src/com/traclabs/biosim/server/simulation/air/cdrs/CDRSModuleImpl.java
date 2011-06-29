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

    private float currentPowerConsumed = 0f;
    
    private CDRSState myState = CDRSState.init;
    private CDRSArmedStatus myArmedStatus = CDRSArmedStatus.not_armed;
    private CDRSValveState myAirInletValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirInletValveArmedStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myAirReturnValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirReturnValveArmedStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2IsolationValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2IsolationValveArmedStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2VentValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2VentValveArmedStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myWaterPumpState = CDRSPowerState.on;
    private CDRSCommandStatus myWaterPumpArmedStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myBlowerState = CDRSPowerState.on;
    private CDRSCommandStatus myBlowerArmedStatus = CDRSCommandStatus.enabled;
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
        myAirInletValveArmedStatus = CDRSCommandStatus.enabled;
        myAirReturnValveState = CDRSValveState.open;
        myAirReturnValveArmedStatus = CDRSCommandStatus.enabled;
        myCO2IsolationValveState = CDRSValveState.open;
        myCO2IsolationValveArmedStatus = CDRSCommandStatus.enabled;
        myCO2VentValveState = CDRSValveState.open;
        myCO2VentValveArmedStatus = CDRSCommandStatus.enabled;
        myWaterPumpState = CDRSPowerState.on;
        myWaterPumpArmedStatus = CDRSCommandStatus.enabled;
        myBlowerState = CDRSPowerState.on;
        myBlowerArmedStatus = CDRSCommandStatus.enabled;
        myDayNightState = CDRSDayNightState.day;
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
		this.myAirInletValveArmedStatus = status;
	}

	public void setAirReturnValveState(CDRSValveState state) {
		if (getAirReturnValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myAirReturnValveState = state;
	}

	public void setAirReturnValveArmedStatus(CDRSCommandStatus status) {
		this.myAirReturnValveArmedStatus = status;
	}

	public void setCO2IsolationValveState(CDRSValveState state) {
		if (getCO2IsolationValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myCO2IsolationValveState = state;
	}

	public void setCO2IsolationValveArmedStatus(CDRSCommandStatus status) {
		this.myCO2IsolationValveArmedStatus = status;
	}

	public void setCO2VentValveState(CDRSValveState state) {
		if (getCO2VentValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myCO2VentValveState = state;
	}

	public void setCO2VentValveArmedStatus(CDRSCommandStatus status) {
		this.myCO2VentValveArmedStatus = status;
	}

	public void setWaterPumpState(CDRSPowerState state) {
		if (getWaterPumpArmedStatus() == CDRSCommandStatus.enabled)
			this.myWaterPumpState = state;
	}

	public void setWaterPumpArmedStatus(CDRSCommandStatus status) {
		this.myWaterPumpArmedStatus = status;
	}

	public void setBlowerState(CDRSPowerState state) {
		if (getBlowerArmedStatus() == CDRSCommandStatus.enabled)
			this.myBlowerState = state;
	}

	public void setBlowerArmedStatus(CDRSCommandStatus status) {
		this.myBlowerArmedStatus = status;
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
		return myAirInletValveArmedStatus;
	}

	public CDRSValveState getAirReturnValveState() {
		return myAirReturnValveState;
	}

	public CDRSCommandStatus getAirReturnValveArmedStatus() {
		return myAirReturnValveArmedStatus;
	}

	public CDRSValveState getCO2IsolationValveState() {
		return myCO2IsolationValveState;
	}

	public CDRSCommandStatus getCO2IsolationValveArmedStatus() {
		return myCO2IsolationValveArmedStatus;
	}

	public CDRSValveState getCO2VentValveState() {
		return myCO2VentValveState;
	}

	public CDRSCommandStatus getCO2VentValveArmedStatus() {
		return myCO2VentValveArmedStatus;
	}

	public CDRSPowerState getWaterPumpState() {
		return myWaterPumpState;
	}

	public CDRSCommandStatus getWaterPumpArmedStatus() {
		return myWaterPumpArmedStatus;
	}

	public CDRSPowerState getBlowerState() {
		return myBlowerState;
	}

	public CDRSCommandStatus getBlowerArmedStatus() {
		return myBlowerArmedStatus;
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
		myPowerConsumerDefinitionImpl.setDesiredFlowRate(myPowerConsumerDefinitionImpl.getMaxFlowRate(AIR_RETURN_VALVE_POWER_INDEX), AIR_RETURN_VALVE_POWER_INDEX);
		myPowerConsumerDefinitionImpl.setDesiredFlowRate(myPowerConsumerDefinitionImpl.getMaxFlowRate(AIR_RETURN_VALVE_POWER_INDEX), AIR_RETURN_VALVE_POWER_INDEX);
		
	}

	private void transitionToInactive() {
		// TODO Auto-generated method stub
		
	}

	private void transitionToSingleBed() {
		// TODO Auto-generated method stub
		
	}

	private void transitionToDualBed() {
		// TODO Auto-generated method stub
		
	}

	private void transitionToStandby() {
		// TODO Auto-generated method stub
		
	}

}