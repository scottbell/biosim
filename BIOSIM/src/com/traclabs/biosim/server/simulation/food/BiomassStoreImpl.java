/**
 * The Biomass Store Implementation.  Takes raw plant matter from the Biomass RS to be used by the Food Processor.
 *
 * @author    Scott Bell
 */

package biosim.server.food;

import biosim.idl.food.*;
import biosim.server.framework.*;

public class BiomassStoreImpl extends StoreImpl implements BiomassStoreOperations {
	/**
	* Returns the name of this module (BiomassStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "BiomassStore";
	}
}
