package biosim.server.water;

// The package containing our stubs.
import SIMULATION.*;

public class DirtyWaterStoreImpl extends DirtyWaterStorePOA {
	private float dirtyWaterlevel;
	private float dirtyWatercapacity;

	float addWater(float liters){
		if ((liters +dirtyWaterlevel) > dirtyWatercapacity){
			dirtyWaterlevel = dirtyWatercapacity;
			if (liters >=  dirtyWatercapacity)
				return 0;
			else
				return (dirtyWatercapacity - dirtyWaterlevel);
		}
		else{
			dirtyWaterlevel = dirtyWaterlevel + liters;
			return liters;
		}
	}

	float takeWater(float liters){
		if ((dirtyWaterlevel - liters) < 0){
			dirtyWaterlevel = 0;
			if (liters < 0)
				return 0;
			else
				return dirtyWaterlevel;
		}
		else{
			dirtyWaterlevel = dirtyWaterlevel - liters;
			return liters;
		}
	}
	float getWaterLevel(){
		return dirtyWaterlevel;
	}

	public void tick(){
		System.out.println("DirtyWater has been ticked!");
	}
	public String getModuleName(){
		return "DirtyWater";
	}
}
