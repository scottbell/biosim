package biosim.server.power;

import biosim.idl.power.*;
import biosim.server.framework.*;

public class PowerStoreImpl extends StoreImpl implements PowerStoreOperations {

	public void tick(){
	}
	
	public String getModuleName(){
		return "PowerStore";
	}
}
