package biosim.server.simulation.mission;

import biosim.idl.simulation.mission.MissionModuleOperations;
import biosim.server.simulation.framework.SimBioModuleImpl;
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

