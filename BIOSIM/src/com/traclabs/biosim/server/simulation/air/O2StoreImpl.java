package biosim.server.air;

import biosim.idl.air.*;
import biosim.server.framework.*;
/**
 * The O2 Store Implementation.  Used by the AirRS to store excess O2 for the crew.
 * Not really used right now.
 *
 * @author    Scott Bell
 */
public class O2StoreImpl extends StoreImpl implements O2StoreOperations {
	public O2StoreImpl(int pID){
		super(pID);
	}
	/**
	* Returns the name of this module (O2Store)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "O2Store"+getID();
	}
}
