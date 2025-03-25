package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.server.simulation.crew.EVAActivityPOA;

/**
 * An EVA Activity for a crew member. During this activity, the crew member
 * detaches herself from the current environment and attachs to another (for the
 * length of the activity). When the activity is finished, the crewmember
 * reattachs herself to the original environment.
 * 
 * @author Scott Bell
 */

public class EVAActivity extends EVAActivityPOA {
    private String myBaseCrewGroupName;

    private String myEVACrewGroupName;

    //  The name of this activity
    private String myName = "unknown";

    //How long this activity will be performed
    private int myTimeLength = 0;

    //The intensity of this activity (how much exertion it takes to perform it)
    private int myActivityIntensity = 0;

    public EVAActivity(String pName, int pTimeLength, int pIntensity,
            String pBaseCrewGroupName, String pEVACrewGroupName) {

        myName = pName;
        myTimeLength = pTimeLength;
        myActivityIntensity = pIntensity;
        myBaseCrewGroupName = pBaseCrewGroupName;
        myEVACrewGroupName = pEVACrewGroupName;
    }

    public String getBaseCrewGroupName() {
        return myBaseCrewGroupName;
    }

    public String getEVACrewGroupName() {
        return myEVACrewGroupName;
    }

    /**
     * Returns the name of this activity
     * 
     * @return The name of this activity
     */
    public String getName() {
        return myName;
    }

    /**
     * Returns how long this activity will be performed (in ticks)
     * 
     * @return How long this activity will be performed
     */
    public int getTimeLength() {
        return myTimeLength;
    }

    /**
     * Returns the intensity of this activity (how much exertion it takes to
     * perform it)
     * 
     * @return The intensity of this activity (how much exertion it takes to
     *         perform it)
     */
    public int getActivityIntensity() {
        return myActivityIntensity;
    }

    /**
     * Returns the name of this activity
     * 
     * @return The name of this activity
     */
    public String toString() {
        return myName;
    }
}