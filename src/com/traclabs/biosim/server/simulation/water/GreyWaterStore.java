package com.traclabs.biosim.server.simulation.water;


/**
 * The Grey Water Store implementation. Takes dirty water from the crew members
 * and stores it for either purification by the WaterRS or use for the crops (in
 * the Biomass RS).
 *
 * @author Scott Bell
 */

public class GreyWaterStore extends WaterStore {
    public GreyWaterStore(int pID, String pName) {
        super(pID, pName);
    }

    public GreyWaterStore() {
        this(0, "Unnamed GreyWaterStore");
    }
}