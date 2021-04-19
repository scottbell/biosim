package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "FoodStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface FoodStoreOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreOperations
{
	/* constants */
	/* operations  */
	void setInitialFoodMatterLevel(com.traclabs.biosim.idl.simulation.food.FoodMatter pFoodMatter);
	float addFoodMatterMass(com.traclabs.biosim.idl.simulation.food.FoodMatter pMatter);
	float addFoodMatterArray(com.traclabs.biosim.idl.simulation.food.FoodMatter[] pList);
	com.traclabs.biosim.idl.simulation.food.FoodMatter[] takeFoodMatterCalories(float calories, float limitingMass);
}
