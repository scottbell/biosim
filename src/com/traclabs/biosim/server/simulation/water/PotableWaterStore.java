package com.traclabs.biosim.server.simulation.water;

/**
 * The Potable Water Store implementation. Filled with clean potable water from
 * the WaterRS and used by the crew memebers to drink.
 *
 * @author Scott Bell
 */

public class PotableWaterStore extends WaterStore {
    public PotableWaterStore(int pID, String pName) {
        super(pID, pName);
    }

    public PotableWaterStore() {
        this(0, "Unnamed PotableWaterStore");
    }
}