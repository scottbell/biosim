package biosim.server.water;

import biosim.idl.water.*;
import biosim.server.framework.*;

public class DirtyWaterStoreImpl extends StoreImpl implements DirtyWaterStoreOperations {

	public void tick(){
	}
	
	public String getModuleName(){
		return "DirtyWaterStore";
	}
}
