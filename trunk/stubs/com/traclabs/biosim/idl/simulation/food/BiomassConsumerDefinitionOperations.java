package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface BiomassConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setBiomassInputs(com.traclabs.biosim.idl.simulation.food.BiomassStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
