package biosim.server.simulation.water;

import biosim.idl.simulation.water.PotableWaterStoreOperations;
import biosim.server.simulation.framework.StoreImpl;
/**
 * The Potable Water Store Implementation.  Filled with clean potable water from the WaterRS and used by the crew memebers to drink.
 *
 * @author    Scott Bell
 */

public class PotableWaterStoreImpl extends StoreImpl implements PotableWaterStoreOperations {
	public PotableWaterStoreImpl(int pID, String pName){
		super(pID, pName);
	}
}
