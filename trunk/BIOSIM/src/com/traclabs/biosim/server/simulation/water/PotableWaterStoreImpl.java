/**
 * The Potable Water Store Implementation.  Filled with clean potable water from the WaterRS and used by the crew memebers to drink.
 *
 * @author    Scott Bell
 */

package biosim.server.water;

import biosim.idl.water.*;
import biosim.server.framework.*;

public class PotableWaterStoreImpl extends StoreImpl implements PotableWaterStoreOperations {
	/**
	* Returns the name of this module (PotableWaterStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "PotableWaterStore";
	}
}
