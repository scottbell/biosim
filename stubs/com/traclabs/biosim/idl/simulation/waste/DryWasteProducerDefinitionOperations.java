package com.traclabs.biosim.idl.simulation.waste;

/**
 *	Generated from IDL interface "DryWasteProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface DryWasteProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setDryWasteOutputs(com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
