package biosim.server.simulation.power;

import biosim.idl.simulation.power.*;
import biosim.server.simulation.framework.*;
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
