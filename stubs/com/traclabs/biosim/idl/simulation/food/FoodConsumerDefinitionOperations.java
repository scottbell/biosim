package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "FoodConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface FoodConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setFoodInputs(com.traclabs.biosim.idl.simulation.food.FoodStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
