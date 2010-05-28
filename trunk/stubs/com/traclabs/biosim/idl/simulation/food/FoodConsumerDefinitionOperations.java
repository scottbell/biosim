package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "FoodConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface FoodConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setFoodInputs(com.traclabs.biosim.idl.simulation.food.FoodStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
