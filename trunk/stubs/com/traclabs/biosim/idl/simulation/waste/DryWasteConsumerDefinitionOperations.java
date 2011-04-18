package com.traclabs.biosim.idl.simulation.waste;

/**
 *	Generated from IDL interface "DryWasteConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface DryWasteConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setDryWasteInputs(com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
