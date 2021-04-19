package com.traclabs.biosim.idl.actuator.environment;


/**
 * Generated from IDL interface "EnvironmentActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface EnvironmentActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.environment.SimEnvironment source);
	com.traclabs.biosim.idl.simulation.environment.SimEnvironment getOutput();
}
