/**
 * The CO2 Store Implementation.  Used by the AirRS to store excess CO2 for plants.
 * Not really used right now.
 *
 * @author    Scott Bell
 */
package biosim.server.air;

import biosim.idl.air.*;
import biosim.server.framework.*;

public class O2StoreImpl extends StoreImpl implements O2StoreOperations {
	
	/**
	* Processes a tick by doing nothing
	*/
	public void tick(){
	}
	
	/**
	* Returns the name of this module (O2Store)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "O2Store";
	}
}
