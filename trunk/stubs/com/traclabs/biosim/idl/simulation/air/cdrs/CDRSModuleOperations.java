package com.traclabs.biosim.idl.simulation.air.cdrs;

/**
 *	Generated from IDL interface "CDRSModule"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CDRSModuleOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations , com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations , com.traclabs.biosim.idl.simulation.environment.AirProducerOperations , com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations
{
	/* constants */
	/* operations  */
	void setState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState state);
	void setArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus status);
	void setAirInletValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state);
	void setAirInletValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status);
	void setAirReturnValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state);
	void setAirReturnValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status);
	void setCO2IsolationValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state);
	void setCO2IsolationValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status);
	void setCO2VentValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state);
	void setCO2VentValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status);
	void setWaterPumpState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState state);
	void setWaterPumpArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status);
	void setBlowerState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState state);
	void setBlowerArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status);
	void setDayNightState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState status);
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState getState();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus getArmedStatus();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getAirInletValveState();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getAirInletValveArmedStatus();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getAirReturnValveState();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getAirReturnValveArmedStatus();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getCO2IsolationValveState();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getCO2IsolationValveArmedStatus();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getCO2VentValveState();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getCO2VentValveArmedStatus();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState getWaterPumpState();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getWaterPumpArmedStatus();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState getBlowerState();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getBlowerArmedStatus();
	com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState getDayNightState();
	float getPrimaryHeatProduction();
	float getSecondaryHeatProduction();
}
