package biosim.server.water;

import biosim.idl.water.*;
import biosim.server.framework.*;
/**
 * The Potable Water Store Implementation.  Filled with clean potable water from the WaterRS and used by the crew memebers to drink.
 *
 * @author    Scott Bell
 */

public class PotableWaterStoreImpl extends StoreImpl implements PotableWaterStoreOperations {
	public PotableWaterStoreImpl(int pID){
		super(pID);
	}
	/**
	* Returns the name of this module (PotableWaterStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "PotableWaterStore";
	}
}
