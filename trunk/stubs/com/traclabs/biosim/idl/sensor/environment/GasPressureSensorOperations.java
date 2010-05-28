package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "GasPressureSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GasPressureSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.environment.EnvironmentStore source);
	com.traclabs.biosim.idl.simulation.environment.EnvironmentStore getInput();
}
