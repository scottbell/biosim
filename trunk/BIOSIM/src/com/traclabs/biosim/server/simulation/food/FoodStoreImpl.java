package biosim.server.food;

// The package containing our stubs.
import ALSS.*;

public class FoodStoreImpl extends FoodStorePOA {
	private float foodLevel;
	private float foodCapacity;

	float addFood(float kilograms){
		if ((kilograms +foodLevel) > foodCapacity){
			foodLevel = foodCapacity;
			if (kilograms >=  foodCapacity)
				return 0;
			else
				return (foodCapacity - foodLevel);
		}
		else{
			foodLevel = foodLevel + kilograms;
			return kilograms;
		}
	}

	float takeFood(float kilograms){
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
	float getFoodLevel(){
		return foodLevel;
	}

	public void tick(){
		System.out.println("Food Store has been ticked!");
	}
	public String getModuleName(){
		return "FoodStore";
	}
}
