package biosim.server.simulation.waste;

import biosim.idl.simulation.waste.*;
import biosim.server.simulation.framework.*;
/**
 * The DryWaste Store Implementation.
 *
 * @author    Scott Bell
 */

public class DryWasteStoreImpl extends StoreImpl implements DryWasteStoreOperations {
	public DryWasteStoreImpl(int pID, String pName){
		super(pID, pName);
	}
}