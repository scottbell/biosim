package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface BiomassProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setBiomassOutputs(com.traclabs.biosim.idl.simulation.food.BiomassStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
