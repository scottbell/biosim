package biosim.server.water;

// The package containing our stubs.
import ALSS.*;

public class GreyWaterStoreImpl extends GreyWaterStorePOA {
	private float greyWaterLevel;
	private float greyWaterCapacity;

	public GreyWaterStoreImpl(){
		greyWaterLevel = 0.0f;
		greyWaterCapacity = 10.0f;
	}

	public GreyWaterStoreImpl (float initialGreyWaterLevel, float  initialGreyWaterCapacity){
		greyWaterLevel = initialGreyWaterLevel;
		greyWaterCapacity = initialGreyWaterCapacity;
	}
	
	void setWaterCapacity(float liters){
		greyWaterCapacity = liters;
	}

	void setWaterLevel(float liters){
		greyWaterLevel = liters;
	}

	float addWater(float liters){
		if ((liters +greyWaterLevel) > greyWaterCapacity){
			greyWaterLevel = greyWaterCapacity;
			if (liters >=  greyWaterCapacity)
				return 0;
			else
				return (greyWaterCapacity - greyWaterLevel);
		}
		else{
			greyWaterLevel = greyWaterLevel + liters;
			return liters;
		}
	}

	float takeWater(float liters){
		if ((greyWaterLevel - liters) < 0){
			greyWaterLevel = 0;
			if (liters < 0)
				return 0;
			else
				return greyWaterLevel;
		}
		else{
			greyWaterLevel = greyWaterLevel - liters;
			return liters;
		}
	}
	float getWaterLevel(){
		return greyWaterLevel;
	}

	public void tick(){
		System.out.println("GreyWater has been ticked!");
	}
	public String getModuleName(){
		return "GreyWaterStore";
	}
}
