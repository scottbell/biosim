package biosim.server.water;

import biosim.idl.water.*;

public class PotableWaterStoreImpl extends PotableWaterStorePOA {
	private float potableWaterLevel;
	private float potableWaterCapacity;
	
	public PotableWaterStoreImpl(){
		potableWaterLevel = 0.0f;
		potableWaterCapacity = 10.0f;
	}

	public PotableWaterStoreImpl (float initialPotableWaterLevel, float  initialPotableWaterCapacity){
		potableWaterLevel = initialPotableWaterLevel;
		potableWaterCapacity = initialPotableWaterCapacity;
	}
	
	public void setWaterCapacity(float liters){
		potableWaterCapacity = liters;
	}

	public void setWaterLevel(float liters){
		potableWaterLevel = liters;
	}

	public float addWater(float liters){
		if ((liters +potableWaterLevel) > potableWaterCapacity){
			float returnValue = (potableWaterCapacity - potableWaterLevel);
			potableWaterLevel = potableWaterCapacity;
			return returnValue;
		}
		else{
			potableWaterLevel = potableWaterLevel + liters;
			return liters;
		}
	}

	public float takeWater(float liters){
		if ((potableWaterLevel - liters) < 0){
			potableWaterLevel = 0;
			if (liters < 0)
				return 0;
			else
				return potableWaterLevel;
		}
		else{
			potableWaterLevel = potableWaterLevel - liters;
			return liters;
		}
	}
	public float getWaterLevel(){
		return potableWaterLevel;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "PotableWaterStore";
	}
}
