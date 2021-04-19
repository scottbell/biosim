package com.traclabs.biosim.idl.sensor.environment;


/**
 * Generated from IDL interface "GasPressureSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface GasPressureSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.environment.EnvironmentStore source);
	com.traclabs.biosim.idl.simulation.environment.EnvironmentStore getInput();
}
