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
	
    //During any given tick, this much power is needed for the CDRS
    // to run at all (sum of desired flows for each power source)
    private static final float POWER_NEEDED_BASE = 900;
	 private static final float POWER_DESIRED_MULTIPLIER = 0.9f;

	// flags to determine if the CDRS has enough power to function
	private boolean hasEnoughPower = false;
	private boolean hasPowerAirInlet = false;
	private boolean hasPowerAirReturn = false;
	private boolean hasPowerCo2Isolation = false;
	private boolean hasPowerCo2Vent = false;
	private boolean hasPowerCdrs = false;
	private boolean hasPowerWaterPump = false;
	private boolean hasPowerBlower = false;
	
	//The power consumed (in watts) by the CDRS at the current tick
	private float currentPowerConsumed = 0f;
	
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
		gatherPower();
		if (hasEnoughPower) {
			gatherAir();
			gatherWater();
		}
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
	
	private void gatherPower() {
		float powerNeeded = POWER_NEEDED_BASE * getTickLength();
		float powerAvailable = setPowerAvailability();
		currentPowerConsumed = powerAvailable;
		if (currentPowerConsumed < powerNeeded) {
			hasEnoughPower = false;
		} else {
			hasEnoughPower = true;
		}
	}
	
	/** Check power availability for particular power sources, reacting if
	 * supply is insufficient, then recording unavailability. Reactions to
	 * insufficient power must be handled prior to recording, as the flags
	 * are used to prevent associated commands. */
	private float setPowerAvailability() {
		float powerAvailable = 0; // running total
		// air inlet power
		float powerAirInlet = myPowerConsumerDefinitionImpl.getMostResourceFromStore(AIR_INLET_VALVE_POWER_INDEX);
		if (powerAirInlet <
		     (POWER_DESIRED_MULTIPLIER * myPowerConsumerDefinitionImpl.getDesiredFlowRate(AIR_INLET_VALVE_POWER_INDEX))) {
			hasPowerAirInlet = false;
		} else {
			hasPowerAirInlet = true;
		}
		powerAvailable += powerAirInlet;
		
		// air return power
		float powerAirReturn = myPowerConsumerDefinitionImpl.getMostResourceFromStore(AIR_RETURN_VALVE_POWER_INDEX);
		if (powerAirReturn <
		     (POWER_DESIRED_MULTIPLIER * myPowerConsumerDefinitionImpl.getDesiredFlowRate(AIR_RETURN_VALVE_POWER_INDEX))) {
			hasPowerAirReturn = false;
		} else {
			hasPowerAirReturn = true;
		}
		powerAvailable += powerAirReturn;
		
		// co2 isolation power
		float powerCo2Isolation = myPowerConsumerDefinitionImpl.getMostResourceFromStore(CO2_ISOLATION_VALVE_POWER_INDEX);
		if (powerCo2Isolation <
		     (POWER_DESIRED_MULTIPLIER * myPowerConsumerDefinitionImpl.getDesiredFlowRate(CO2_ISOLATION_VALVE_POWER_INDEX))) {
			hasPowerCo2Isolation = false;
		} else {
			hasPowerCo2Isolation = true;
		}
		powerAvailable += powerCo2Isolation;
		
		// co2 vent power
		float powerCo2Vent = myPowerConsumerDefinitionImpl.getMostResourceFromStore(CO2_VENT_VALVE_POWER_INDEX);
		if (powerCo2Vent <
		     (POWER_DESIRED_MULTIPLIER * myPowerConsumerDefinitionImpl.getDesiredFlowRate(CO2_VENT_VALVE_POWER_INDEX))) {
			hasPowerCo2Vent = false;
		} else {
			hasPowerCo2Vent = true;
		}
		powerAvailable += powerCo2Vent;
		
		// cdrs power
		float powerCdrs = myPowerConsumerDefinitionImpl.getMostResourceFromStore(CDRS_POWER_INDEX);
		if (powerCdrs <
		     (POWER_DESIRED_MULTIPLIER * myPowerConsumerDefinitionImpl.getDesiredFlowRate(CDRS_POWER_INDEX))) {
			transitionToInactive();
			myTicksWaited = 0;
			myStateToTransition = CDRSState.transitioning;
			hasPowerCdrs = false;
		} else {
			hasPowerCdrs = true;
		}
		powerAvailable += powerCdrs;
		
		// water pump power
		float powerWaterPump = myPowerConsumerDefinitionImpl.getMostResourceFromStore(WATER_PUMP_POWER_INDEX);
		if (powerWaterPump <
		     (POWER_DESIRED_MULTIPLIER * myPowerConsumerDefinitionImpl.getDesiredFlowRate(WATER_PUMP_POWER_INDEX))) {
			setWaterPumpState(CDRSPowerState.off);
			hasPowerWaterPump = false;
		} else {
			hasPowerWaterPump = true;
		}
		powerAvailable += powerWaterPump;
		
		float powerBlower = myPowerConsumerDefinitionImpl.getMostResourceFromStore(BLOWER_POWER_INDEX);
		if (powerBlower <
		     (POWER_DESIRED_MULTIPLIER * myPowerConsumerDefinitionImpl.getDesiredFlowRate(BLOWER_POWER_INDEX))) {
			setBlowerState(CDRSPowerState.off);
			hasPowerBlower = false;
		} else {
			hasPowerBlower = true;
		}
		powerAvailable += powerBlower;
		return powerAvailable;
	}
	
	/**
	 * Resets production/consumption levels.
	 */
	public void reset() {
		super.reset();
		currentPowerConsumed = 0f;
		myPowerConsumerDefinitionImpl.reset();
		myGreyWaterConsumerDefinitionImpl.reset();
		myGreyWaterProducerDefinitionImpl.reset();
		myAirConsumerDefinitionImpl.reset();
		myAirProducerDefinitionImpl.reset();
		myCO2ProducerDefinitionImpl.reset();
		hasEnoughPower = false;
		hasPowerAirInlet = false;
		hasPowerAirReturn = false;
		hasPowerCo2Isolation = false;
		hasPowerCo2Vent = false;
		hasPowerCdrs = false;
		hasPowerWaterPump = false;
		hasPowerBlower = false;
		currentPowerConsumed = 0f;
		
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

	/**
	 * Returns the power consumed (in watts) by the CDRS during the
	 * current tick
	 * 
	 * @return the power consumed (in watts) by the CDRS during the
	 *         current tick
	 */
	public float getPowerConsumed() {
		return currentPowerConsumed;
	}
	
	/**
	 * Checks whether CDRS has enough power or not
	 * 
	 * @return <code>true</code> if the CDRS has enough power,
	 *         <code>false</code> if not.
	 */
	public boolean hasPower() {
		return hasEnoughPower;
	}
	
	public void setState(CDRSState state) {
		if (!hasPowerCdrs) {
			return;
		}
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
		if (!hasPowerCdrs) {
			return;
		}
		this.myArmedStatus = status; 
	}

	public void setAirInletValveState(CDRSValveState state) {
		if (!hasPowerAirInlet) {
			return;
		}
		if (getAirInletValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myAirInletValveState = state;
	}

	public void setAirInletValveArmedStatus(CDRSCommandStatus status) {
		if (!hasPowerAirInlet) {
			return;
		}
		this.myAirInletValveEnabledStatus = status;
	}

	public void setAirReturnValveState(CDRSValveState state) {
		if (!hasPowerAirReturn) {
			return;
		}
		if (getAirReturnValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myAirReturnValveState = state;
	}

	public void setAirReturnValveArmedStatus(CDRSCommandStatus status) {
		if (!hasPowerAirReturn) {
			return;
		}
		this.myAirReturnValveEnabledStatus = status;
	}

	public void setCO2IsolationValveState(CDRSValveState state) {
		if (!hasPowerCo2Isolation) {
			return;
		}
		if (getCO2IsolationValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myCO2IsolationValveState = state;
	}

	public void setCO2IsolationValveArmedStatus(CDRSCommandStatus status) {
		if (!hasPowerCo2Isolation) {
			return;
		}
		this.myCO2IsolationValveEnabledStatus = status;
	}

	public void setCO2VentValveState(CDRSValveState state) {
		if (!hasPowerCo2Vent) {
			return;
		}
		if (getCO2VentValveArmedStatus() == CDRSCommandStatus.enabled)
			this.myCO2VentValveState = state;
	}

	public void setCO2VentValveArmedStatus(CDRSCommandStatus status) {
		if (!hasPowerCo2Vent) {
			return;
		}
		this.myCO2VentValveEnabledStatus = status;
	}

	public void setWaterPumpState(CDRSPowerState state) {
		if (getWaterPumpArmedStatus() == CDRSCommandStatus.enabled)
			this.myWaterPumpState = state;
	}

	public void setWaterPumpArmedStatus(CDRSCommandStatus status) {
		if (!hasPowerCdrs) {
			return;
		}
		this.myWaterPumpEnabledStatus = status;
	}

	public void setBlowerState(CDRSPowerState state) {
		if (getBlowerArmedStatus() == CDRSCommandStatus.enabled)
			this.myBlowerState = state;
	}

	public void setBlowerArmedStatus(CDRSCommandStatus status) {
		if (!hasPowerCdrs) {
			return;
		}
		this.myBlowerEnabledStatus = status;
	}

	public void setDayNightState(CDRSDayNightState state) {
		if (!hasPowerCdrs) {
			return;
		}
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

}
