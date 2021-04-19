package com.traclabs.biosim.idl.simulation.power;


/**
 * Generated from IDL interface "PowerProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PowerProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setPowerOutputs(com.traclabs.biosim.idl.simulation.power.PowerStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
