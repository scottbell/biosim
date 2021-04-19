package com.traclabs.biosim.idl.simulation.power;


/**
 * Generated from IDL interface "PowerConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PowerConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setPowerInputs(com.traclabs.biosim.idl.simulation.power.PowerStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
