package biosim.server.air;

import biosim.idl.air.*;
import biosim.server.framework.*;
/**
 * The CO2 Store Implementation.  Used by the AirRS to store excess CO2 for plants.
 * Not really used right now.
 *
 * @author    Scott Bell
 */

public class CO2StoreImpl extends StoreImpl implements CO2StoreOperations {
	
	public CO2StoreImpl(int pID){
		super(pID);
	}
	
	/**
	* Returns the name of this module (CO2Store)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "CO2Store"+getID();
	}
}
