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

	public float addFood(float kilosRequested){
		float acutallyAdded = 0f;
		if ((kilosRequested + foodLevel) > foodCapacity){
			//adding more food than capacity
			acutallyAdded = (foodCapacity - foodLevel);
			foodLevel += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = kilosRequested;
			foodLevel += kilosRequested;
			return acutallyAdded;
		}
	}

	public float takeFood(float kilosRequested){
		//idiot check
		if (kilosRequested < 0){
			return 0f;
		}
		//asking for more food than exists
		if (kilosRequested > foodLevel){
			float takenFood = foodLevel;
			foodLevel = 0;
			return takenFood;
		}
		//food exists for request
		else{
			float takenFood = kilosRequested;
			foodLevel -= kilosRequested; 
			return takenFood;
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
