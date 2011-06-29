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

    private float currentCO2Consumed;

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
		// TODO Auto-generated method stub
		
	}

	public void setArmedStatus(CDRSArmedStatus status) {
		// TODO Auto-generated method stub
		
	}

	public void setAirInletValveState(CDRSValveState state) {
		// TODO Auto-generated method stub
		
	}

	public void setAirInletValveArmedStatus(CDRSCommandStatus status) {
		// TODO Auto-generated method stub
		
	}

	public void setAirReturnValveState(CDRSValveState state) {
		// TODO Auto-generated method stub
		
	}

	public void setAirReturnValveArmedStatus(CDRSCommandStatus status) {
		// TODO Auto-generated method stub
		
	}

	public void setCO2IsolationValveState(CDRSValveState state) {
		// TODO Auto-generated method stub
		
	}

	public void setCO2IsolationValveArmedStatus(CDRSCommandStatus status) {
		// TODO Auto-generated method stub
		
	}

	public void setCO2VentValveState(CDRSValveState state) {
		// TODO Auto-generated method stub
		
	}

	public void setCO2VentValveArmedStatus(CDRSCommandStatus status) {
		// TODO Auto-generated method stub
		
	}

	public void setWaterPumpState(CDRSPowerState state) {
		// TODO Auto-generated method stub
		
	}

	public void setWaterPumpArmedStatus(CDRSCommandStatus status) {
		// TODO Auto-generated method stub
		
	}

	public void setBlowerState(CDRSPowerState state) {
		// TODO Auto-generated method stub
		
	}

	public void setBlowerArmedStatus(CDRSCommandStatus status) {
		// TODO Auto-generated method stub
		
	}

	public void setDayNightState(CDRSDayNightState status) {
		// TODO Auto-generated method stub
		
	}

	public CDRSState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSArmedStatus getArmedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSValveState getAirInletValveState() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSCommandStatus getAirInletValveArmedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSValveState getAirReturnValveState() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSCommandStatus getAirReturnValveArmedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSValveState getCO2IsolationValveState() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSCommandStatus getCO2IsolationValveArmedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSValveState getCO2VentValveState() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSCommandStatus getCO2VentValveArmedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSPowerState getWaterPumpState() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSCommandStatus getWaterPumpArmedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSPowerState getBlowerState() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSCommandStatus getBlowerArmedStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public CDRSDayNightState getDayNightState() {
		// TODO Auto-generated method stub
		return null;
	}

}