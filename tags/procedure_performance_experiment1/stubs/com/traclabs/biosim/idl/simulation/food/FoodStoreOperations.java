package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "FoodStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
