package com.traclabs.biosim.idl.simulation.environment;


/**
 * Generated from IDL interface "AirConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface AirConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setAirInputs(com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
