package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.simulation.crew.EVAActivityOperations;

/**
 * An EVA Activity for a crew member.  During this activity, the crew member
 * detaches herself from the current environment and attachs to another (for the length of the activity).
 * When the activity is finished, the crewmember reattachs herself to the original environment.
 * 
 * @author Scott Bell
 */

public class EVAActivityImpl extends ActivityImpl implements
        EVAActivityOperations {
    private String myBaseEnvironmentName;

    private String myOutsideEnvironmentName;

    private int bob;

    public EVAActivityImpl(String pBaseEnvironmentName,
            String pOutsideEnvironmentName, int pTimeLength) {
        super("eva", pTimeLength, 4);
        myBaseEnvironmentName = pBaseEnvironmentName;
        myOutsideEnvironmentName = pBaseEnvironmentName;
    }

    public String getBaseEnvironmentName() {
        return myBaseEnvironmentName;
    }

    public String getOutsideEnvironmentName() {
        return myOutsideEnvironmentName;
    }
}