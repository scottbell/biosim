package biosim.server.water;

// The package containing our stubs.
import ALSS.*;

public class DirtyWaterStoreImpl extends DirtyWaterStorePOA {
	private float dirtyWaterLevel;
	private float dirtyWaterCapacity;
	
	public DirtyWaterStoreImpl(){
		dirtyWaterLevel = 0.0f;
		dirtyWaterCapacity = 10.0f;
	}

	public DirtyWaterStoreImpl (float initialDirtyWaterLevel, float  initialDirtyWaterCapacity){
		dirtyWaterLevel = initialDirtyWaterLevel;
		dirtyWaterCapacity = initialDirtyWaterCapacity;
	}
	
	void setWaterCapacity(float liters){
		dirtyWaterCapacity = liters;
	}

	void setWaterLevel(float liters){
		dirtyWaterLevel = liters;
	}

	float addWater(float liters){
		if ((liters +dirtyWaterLevel) > dirtyWaterCapacity){
			dirtyWaterLevel = dirtyWaterCapacity;
			if (liters >=  dirtyWaterCapacity)
				return 0;
			else
				return (dirtyWaterCapacity - dirtyWaterLevel);
		}
		else{
			dirtyWaterLevel = dirtyWaterLevel + liters;
			return liters;
		}
	}

	float takeWater(float liters){
		if ((dirtyWaterLevel - liters) < 0){
			dirtyWaterLevel = 0;
			if (liters < 0)
				return 0;
			else
				return dirtyWaterLevel;
		}
		else{
			dirtyWaterLevel = dirtyWaterLevel - liters;
			return liters;
		}
	}
	float getWaterLevel(){
		return dirtyWaterLevel;
	}

	public void tick(){
		System.out.println("DirtyWater has been ticked!");
	}
	public String getModuleName(){
		return "DirtyWaterStore";
	}
}
