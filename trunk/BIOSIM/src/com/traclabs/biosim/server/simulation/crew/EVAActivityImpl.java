package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.simulation.crew.EVAActivityOperations;

/**
 * An EVA Activity for a crew member. During this activity, the crew member
 * detaches herself from the current environment and attachs to another (for the
 * length of the activity). When the activity is finished, the crewmember
 * reattachs herself to the original environment.
 * 
 * @author Scott Bell
 */

public class EVAActivityImpl extends ActivityImpl implements
        EVAActivityOperations {
    private String myBaseCrewGroupName;

    private String myEVACrewGroupName;

    private int bob;

    public EVAActivityImpl(String pName, int pTimeLength, int pIntensity,String pBaseCrewGroupName, String pEVACrewGroupName) {
        super(pName, pTimeLength, pIntensity);
        myBaseCrewGroupName = pBaseCrewGroupName;
        myEVACrewGroupName = pEVACrewGroupName;
    }

    public String getBaseCrewGroupName() {
        return myBaseCrewGroupName;
    }

    public String getEVACrewGroupName() {
        return myEVACrewGroupName;
    }
}