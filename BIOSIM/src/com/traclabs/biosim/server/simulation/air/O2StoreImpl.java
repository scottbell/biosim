package biosim.server.simulation.air;

import biosim.idl.simulation.air.*;
import biosim.server.simulation.framework.*;
/**
 * The O2 Store Implementation.  Used by the AirRS to store excess O2 for the crew.
 * Not really used right now.
 *
 * @author    Scott Bell
 */
public class O2StoreImpl extends StoreImpl implements O2StoreOperations {
	public O2StoreImpl(int pID, String pName){
		super(pID, pName);
	}
}
