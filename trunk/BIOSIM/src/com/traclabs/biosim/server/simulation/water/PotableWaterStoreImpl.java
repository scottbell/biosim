package biosim.server.water;

// The package containing our stubs.
import SIMULATION.*;

public class PotableWaterStoreImpl extends PotableWaterStorePOA {
	private float potableWaterlevel;
	private float potableWatercapacity;

	float addWater(float liters){
		if ((liters +potableWaterlevel) > potableWatercapacity){
			potableWaterlevel = potableWatercapacity;
			if (liters >=  potableWatercapacity)
				return 0;
			else
				return (potableWatercapacity - potableWaterlevel);
		}
		else{
			potableWaterlevel = potableWaterlevel + liters;
			return liters;
		}
	}

	float takeWater(float liters){
		if ((potableWaterlevel - liters) < 0){
			potableWaterlevel = 0;
			if (liters < 0)
				return 0;
			else
				return potableWaterlevel;
		}
		else{
			potableWaterlevel = potableWaterlevel - liters;
			return liters;
		}
	}
	float getWaterLevel(){
		return potableWaterlevel;
	}

	public void tick(){
		System.out.println("PotableWater has been ticked!");
	}
	public String getModuleName(){
		return "PotableWaterStore";
	}
}
