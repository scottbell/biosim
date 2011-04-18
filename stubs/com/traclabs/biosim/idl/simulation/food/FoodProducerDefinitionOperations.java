package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "FoodProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface FoodProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setFoodOutputs(com.traclabs.biosim.idl.simulation.food.FoodStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
