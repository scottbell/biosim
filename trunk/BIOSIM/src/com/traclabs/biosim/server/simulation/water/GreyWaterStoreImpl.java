package biosim.server.water;

// The package containing our stubs.
import SIMULATION.*;

public class GreyWaterStoreImpl extends GreyWaterStorePOA {
	private float greyWaterlevel;
	private float greyWatercapacity;

	float addWater(float liters){
		if ((liters +greyWaterlevel) > greyWatercapacity){
			greyWaterlevel = greyWatercapacity;
			if (liters >=  greyWatercapacity)
				return 0;
			else
				return (greyWatercapacity - greyWaterlevel);
		}
		else{
			greyWaterlevel = greyWaterlevel + liters;
			return liters;
		}
	}

	float takeWater(float liters){
		if ((greyWaterlevel - liters) < 0){
			greyWaterlevel = 0;
			if (liters < 0)
				return 0;
			else
				return greyWaterlevel;
		}
		else{
			greyWaterlevel = greyWaterlevel - liters;
			return liters;
		}
	}
	float getWaterLevel(){
		return greyWaterlevel;
	}

	public void tick(){
		System.out.println("GreyWater has been ticked!");
	}
	public String getModuleName(){
		return "GreyWaterStore";
	}
}
