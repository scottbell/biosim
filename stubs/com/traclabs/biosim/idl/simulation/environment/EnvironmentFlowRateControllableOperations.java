package com.traclabs.biosim.idl.simulation.environment;


/**
 * Generated from IDL interface "EnvironmentFlowRateControllable".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface EnvironmentFlowRateControllableOperations
	extends com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] getEnvironments();
	boolean connectsTo(com.traclabs.biosim.idl.simulation.environment.SimEnvironment pSimEnvironment);
}
