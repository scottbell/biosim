package biosim.server.water;

import biosim.idl.water.*;
import biosim.server.framework.*;

public class GreyWaterStoreImpl extends StoreImpl implements GreyWaterStoreOperations {
	
	public void tick(){
	}
	
	public String getModuleName(){
		return "GreyWaterStore";
	}
}
