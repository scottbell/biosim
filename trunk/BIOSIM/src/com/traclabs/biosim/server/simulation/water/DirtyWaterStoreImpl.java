package biosim.server.simulation.water;

import biosim.idl.simulation.water.*;
import biosim.server.simulation.framework.*;
/**
 * The Dirty Water Store Implementation.  Takes dirty water from the crew members and stores it for either purification by the Water RS.
 * @author    Scott Bell
 */

public class DirtyWaterStoreImpl extends StoreImpl implements DirtyWaterStoreOperations {
	public DirtyWaterStoreImpl(int pID, String pName){
		super(pID, pName);
	}
}
