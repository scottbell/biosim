package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.simulation.crew.MaitenanceActivityOperations;

/**
 * Repair Activities.
 * 
 * @author Scott Bell
 */

public class MaitenanceActivityImpl extends ActivityImpl implements
        MaitenanceActivityOperations {
    private String myModuleToMaitenance = "Unknown";

    public MaitenanceActivityImpl(String pName, int pTimeLength,
            int pActivityIntensity, String pModuleToMaitenance) {
        super(pName, pTimeLength, pActivityIntensity);
        myModuleToMaitenance = pModuleToMaitenance;
    }

    public String getModuleToMaitenance() {
        return myModuleToMaitenance;
    }
}