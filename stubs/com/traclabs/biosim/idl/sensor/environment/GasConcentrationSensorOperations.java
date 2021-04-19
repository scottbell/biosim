package com.traclabs.biosim.idl.sensor.environment;


/**
 * Generated from IDL interface "GasConcentrationSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface GasConcentrationSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.environment.SimEnvironment _environment, com.traclabs.biosim.idl.simulation.environment.EnvironmentStore gas);
	com.traclabs.biosim.idl.simulation.environment.SimEnvironment getEnvironment();
	com.traclabs.biosim.idl.simulation.environment.EnvironmentStore getGas();
}
