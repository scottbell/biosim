package biosim.server.food;

import biosim.idl.food.*;

public class FoodStoreImpl extends FoodStorePOA {
	private float foodLevel;
	private float foodCapacity;

	public FoodStoreImpl(){
		foodLevel = 0.0f;
		foodCapacity = 10.0f;
	}

	public FoodStoreImpl (float initialFoodLevel, float  initialFoodCapacity){
		foodLevel = initialFoodLevel;
		foodCapacity = initialFoodCapacity;
	}

	public void setFoodCapacity(float kilograms){
		foodCapacity = kilograms;
	}

	public void setFoodLevel(float kilograms){
		foodLevel = kilograms;
	}

	public float addFood(float kilograms){
		if ((kilograms +foodLevel) > foodCapacity){
			float returnValue = (foodCapacity - foodLevel);
			foodLevel = foodCapacity;
			return returnValue;
		}
		else{
			foodLevel = foodLevel + kilograms;
			return kilograms;
		}
	}

	public float takeFood(float kilograms){
		if ((foodLevel - kilograms) < 0){
			foodLevel = 0;
			if (kilograms < 0)
				return 0;
			else
				return foodLevel;
		}
		else{
			foodLevel = foodLevel - kilograms;
			return kilograms;
		}
	}
	public float getFoodLevel(){
		return foodLevel;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "FoodStore";
	}
}
