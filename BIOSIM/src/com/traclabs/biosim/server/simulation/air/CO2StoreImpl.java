package biosim.server.air;

import biosim.idl.air.*;
import biosim.server.framework.*;

public class CO2StoreImpl extends StoreImpl implements CO2StoreOperations {

	public void tick(){
	}
	
	public String getModuleName(){
		return "CO2Store";
	}
}
