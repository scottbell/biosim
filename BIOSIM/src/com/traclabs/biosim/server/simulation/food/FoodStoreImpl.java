package biosim.server.food;

import biosim.idl.food.*;
import biosim.server.framework.*;

public class FoodStoreImpl extends StoreImpl implements FoodStoreOperations {

	public void tick(){
	}
	
	public String getModuleName(){
		return "FoodStore";
	}
}
