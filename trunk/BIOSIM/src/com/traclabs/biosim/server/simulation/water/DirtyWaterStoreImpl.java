/**
 * The Dirty Water Store Implementation.  Takes dirty water from the crew members and stores it for either purification by the Water RS.
 *
 * @author    Scott Bell
 */

package biosim.server.water;

import biosim.idl.water.*;
import biosim.server.framework.*;

public class DirtyWaterStoreImpl extends StoreImpl implements DirtyWaterStoreOperations {
	/**
	* Returns the name of this module (DirtyWaterStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "DirtyWaterStore";
	}
}
