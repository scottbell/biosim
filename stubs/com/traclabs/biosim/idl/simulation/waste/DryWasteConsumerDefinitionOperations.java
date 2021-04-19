package com.traclabs.biosim.idl.simulation.waste;


/**
 * Generated from IDL interface "DryWasteConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface DryWasteConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setDryWasteInputs(com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
