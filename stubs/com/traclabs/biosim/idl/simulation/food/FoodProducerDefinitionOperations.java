package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "FoodProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface FoodProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setFoodOutputs(com.traclabs.biosim.idl.simulation.food.FoodStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
