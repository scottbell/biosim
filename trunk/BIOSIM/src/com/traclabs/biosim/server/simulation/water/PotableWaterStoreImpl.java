package biosim.server.water;

// The package containing our stubs.
import ALSS.*;

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
	
	void setWaterCapacity(float liters){
		potableWaterCapacity = liters;
	}

	void setWaterLevel(float liters){
		potableWaterLevel = liters;
	}

	float addWater(float liters){
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

	float takeWater(float liters){
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
	float getWaterLevel(){
		return potableWaterLevel;
	}

	public void tick(){
		System.out.println("PotableWater has been ticked!");
	}
	public String getModuleName(){
		return "PotableWaterStore";
	}
}
