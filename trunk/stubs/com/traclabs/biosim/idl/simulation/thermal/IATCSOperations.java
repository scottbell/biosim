package com.traclabs.biosim.idl.simulation.thermal;

/**
 *	Generated from IDL interface "IATCS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface IATCSOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.thermal.IATCSState getIatcsState();
	void setIatcsState(com.traclabs.biosim.idl.simulation.thermal.IATCSState state);
	com.traclabs.biosim.idl.simulation.thermal.IATCSActivation getActivateState();
	void setActivateState(com.traclabs.biosim.idl.simulation.thermal.IATCSActivation activateState);
	com.traclabs.biosim.idl.simulation.thermal.SoftwareState getSfcaSoftwareState();
	void setSfcaSoftwareState(com.traclabs.biosim.idl.simulation.thermal.SoftwareState sfcaSoftwareState);
	com.traclabs.biosim.idl.simulation.thermal.SoftwareState getTwvmSoftwareState();
	void setTwvmSoftwareState(com.traclabs.biosim.idl.simulation.thermal.SoftwareState twvmSoftwareState);
	com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatus getPpaPumpSpeedCommandStatus();
	void setPpaPumpSpeedCommandStatus(com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatus ppaPumpSpeedCommandStatus);
	float getPumpSpeed();
	void setPumpSpeed(float pumpSpeed);
	com.traclabs.biosim.idl.simulation.thermal.IFHXBypassState getBypassValveState();
	void setBypassValveState(com.traclabs.biosim.idl.simulation.thermal.IFHXBypassState bypassValveState);
	com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus getBypassValveCommandStatus();
	void setBypassValveCommandStatus(com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus bypassValveCommandStatus);
	com.traclabs.biosim.idl.simulation.thermal.IFHXValveState getIsloationValveState();
	void setIsloationValveState(com.traclabs.biosim.idl.simulation.thermal.IFHXValveState isloationValveState);
	com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus getIsolationValveCommandStatus();
	void setIsolationValveCommandStatus(com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus isolationValveCommandStatus);
	com.traclabs.biosim.idl.simulation.thermal.SoftwareState getHeaterSoftwareState();
	void setHeaterSoftwareState(com.traclabs.biosim.idl.simulation.thermal.SoftwareState newHeaterSoftwareState);
}
