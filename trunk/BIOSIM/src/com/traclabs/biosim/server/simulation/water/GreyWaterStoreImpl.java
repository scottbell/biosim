package biosim.server.water;

import biosim.idl.water.*;

public class GreyWaterStoreImpl extends GreyWaterStorePOA {
	private float waterLevel;
	private float waterCapacity;

	public GreyWaterStoreImpl(){
		waterLevel = 0.0f;
		waterCapacity = 10.0f;
	}

	public GreyWaterStoreImpl (float initialGreyWaterLevel, float  initialGreyWaterCapacity){
		waterLevel = initialGreyWaterLevel;
		waterCapacity = initialGreyWaterCapacity;
	}
	
	public void setWaterCapacity(float liters){
		waterCapacity = liters;
	}

	public void setWaterLevel(float liters){
		waterLevel = liters;
	}

	public float addWater(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + waterLevel) > waterCapacity){
			//adding more water than capacity
			acutallyAdded = (waterCapacity - waterLevel);
			waterLevel += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			waterLevel += litersRequested;
			return acutallyAdded;
		}
	}

	public float takeWater(float litersRequested){
		//idiot check
		if (litersRequested < 0){
			return 0f;
		}
		//asking for more water than exists
		if (litersRequested > waterLevel){
			float takenWater = waterLevel;
			waterLevel = 0;
			return takenWater;
		}
		//water exists for request
		else{
			float takenWater = litersRequested;
			waterLevel -= litersRequested; 
			return takenWater;
		}
	}
	
	public float getWaterLevel(){
		return waterLevel;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "GreyWaterStore";
	}
}
