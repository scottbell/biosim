package biosim.server.simulation.water;

import biosim.idl.simulation.water.*;
import biosim.server.simulation.framework.*;
/**
 * The Grey Water Store Implementation.  Takes dirty water from the crew members and stores it for either purification by the WaterRS
 * or use for the crops (in the Biomass RS).
 *
 * @author    Scott Bell
 */

public class GreyWaterStoreImpl extends StoreImpl implements GreyWaterStoreOperations {
	public GreyWaterStoreImpl(int pID, String pName){
		super(pID, pName);
	}
}
