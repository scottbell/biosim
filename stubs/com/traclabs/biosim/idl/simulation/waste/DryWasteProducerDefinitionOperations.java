package com.traclabs.biosim.idl.simulation.waste;


/**
 * Generated from IDL interface "DryWasteProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface DryWasteProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setDryWasteOutputs(com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
