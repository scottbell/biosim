package com.traclabs.biosim.server.simulation.mission;

import com.traclabs.biosim.idl.simulation.mission.MissionModuleOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The MissionModule Implementation.
 * 
 * @author Scott Bell
 */

public abstract class MissionModuleImpl extends SimBioModuleImpl implements
        MissionModuleOperations {
    protected float missionProductivity = 0f;

    protected MissionModuleImpl(int pID, String pName) {
        super(pID, pName);
    }

    public float getMissionProductivity() {
        return missionProductivity;
    }

}

