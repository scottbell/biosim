package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "AirProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface AirProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setAirOutputs(com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
