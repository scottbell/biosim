package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.server.simulation.framework.*;
/**
 * The Biomass Store Implementation.  Takes raw plant matter from the Biomass RS to be used by the Food Processor.
 *
 * @author    Scott Bell
 */

public class BiomassStoreImpl extends StoreImpl implements BiomassStoreOperations {
	public BiomassStoreImpl(int pID){
		super(pID);
	}
	
	/**
	* Returns the name of this module (BiomassStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "BiomassStore"+getID();
	}
}
