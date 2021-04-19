package com.traclabs.biosim.idl.simulation.environment;


/**
 * Generated from IDL interface "AirProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface AirProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setAirOutputs(com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
