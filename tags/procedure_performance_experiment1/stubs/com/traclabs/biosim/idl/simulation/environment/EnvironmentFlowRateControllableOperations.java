package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "EnvironmentFlowRateControllable"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface EnvironmentFlowRateControllableOperations
	extends com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] getEnvironments();
	boolean connectsTo(com.traclabs.biosim.idl.simulation.environment.SimEnvironment pSimEnvironment);
}
