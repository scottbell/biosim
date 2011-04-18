package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "AirConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface AirConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setAirInputs(com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
