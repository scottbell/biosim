package biosim.server.power;

import biosim.idl.power.*;
import biosim.server.framework.*;
/**
 * The Power Store Implementation.  Takes power from the Power Production System and stores it here for other modules to use.
 *
 * @author    Scott Bell
 */

public class PowerStoreImpl extends StoreImpl implements PowerStoreOperations {
	public PowerStoreImpl(int pID){
		super(pID);
	}
	/**
	* Returns the name of this module (PowerStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "PowerStore"+getID();
	}
}
