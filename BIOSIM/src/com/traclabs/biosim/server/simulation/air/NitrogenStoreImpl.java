package biosim.server.simulation.air;

import biosim.idl.simulation.air.*;
import biosim.server.simulation.framework.*;
/**
 * The Nitrogen Store Implementation.  Used by the AirRS to store excess Nitrogen for the crew.
 * Not really used right now.
 *
 * @author    Scott Bell
 */
public class NitrogenStoreImpl extends StoreImpl implements NitrogenStoreOperations {
	public NitrogenStoreImpl(int pID, String pName){
		super(pID, pName);
	}
}
