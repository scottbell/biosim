package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "NitrogenConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface NitrogenConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setNitrogenInputs(com.traclabs.biosim.idl.simulation.air.NitrogenStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
