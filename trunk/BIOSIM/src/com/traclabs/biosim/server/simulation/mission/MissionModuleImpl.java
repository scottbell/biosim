package biosim.server.simulation.mission;

import biosim.server.simulation.framework.*;
import biosim.idl.simulation.mission.*;
/**
 * The MissionModule Implementation.
 *
 * @author    Scott Bell
 */

public abstract class MissionModuleImpl extends SimBioModuleImpl implements MissionModuleOperations{
	protected float missionProductivity = 0f;
	
	protected MissionModuleImpl(int pID, String pName){
		super(pID, pName);
	}
	
	public float getMissionProductivity(){
		return missionProductivity;
	}
	
}

