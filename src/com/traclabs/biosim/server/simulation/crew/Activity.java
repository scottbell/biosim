package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.server.simulation.crew.ActivityPOA;

/**
 * Activities are performed by crew members (CrewPerson) for a certain amount of
 * time with a certain intensity in a certain order.
 * 
 * @author Scott Bell
 */

public class Activity extends ActivityPOA {
    //The name of this activity
    private String myName = "unknown";

    //How long this activity will be performed
    private int myTimeLength = 0;

    //The intensity of this activity (how much exertion it takes to perform it)
    private int myActivityIntensity = 0;

    /**
     * Returns the power consumption of the AirRS at the current tick.
     * 
     * @param pName
     *            The name of this activity
     * @param pTimeLength
     *            How long this activity will be performed
     * @param pActivityIntensity
     *            The intensity of this activity (how much exertion it takes to
     *            perform it)
     */
    public Activity(String pName, int pTimeLength, int pActivityIntensity) {
        myName = pName;
        myTimeLength = pTimeLength;
        myActivityIntensity = pActivityIntensity;
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