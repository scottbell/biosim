package biosim.server.food;

import biosim.idl.food.*;
/**
 * Plant
 * @author    Scott Bell
 */

public abstract class Plant {
	
	public Plant(){
	}
	
	public abstract float getWaterNeeded();
	public abstract void addWater(float pWaterAdded);
	public abstract void reset();
}
