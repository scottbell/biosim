package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "EnvironmentSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface EnvironmentSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.environment.SimEnvironment source);
	com.traclabs.biosim.idl.simulation.environment.SimEnvironment getInput();
}
