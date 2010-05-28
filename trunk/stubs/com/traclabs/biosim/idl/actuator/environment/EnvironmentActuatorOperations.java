package com.traclabs.biosim.idl.actuator.environment;

/**
 *	Generated from IDL interface "EnvironmentActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface EnvironmentActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.environment.SimEnvironment source);
	com.traclabs.biosim.idl.simulation.environment.SimEnvironment getOutput();
}
