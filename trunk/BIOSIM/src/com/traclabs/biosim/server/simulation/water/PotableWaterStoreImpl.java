package biosim.server.water;

import biosim.idl.water.*;
import biosim.server.framework.*;

public class PotableWaterStoreImpl extends StoreImpl implements PotableWaterStoreOperations {

	public void tick(){
	}
	
	public String getModuleName(){
		return "PotableWaterStore";
	}
}
