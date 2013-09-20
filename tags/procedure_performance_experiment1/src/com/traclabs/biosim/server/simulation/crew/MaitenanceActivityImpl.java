package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.simulation.crew.MaitenanceActivityOperations;

/**
 * Repair Activities.
 * 
 * @author Scott Bell
 */

public class MaitenanceActivityImpl extends ActivityImpl implements
        MaitenanceActivityOperations {
    private String myModuleNameToMaintain = "Unknown";

    public MaitenanceActivityImpl(String pName, int pTimeLength,
            int pActivityIntensity, String pModuleToMaintain) {
        super(pName, pTimeLength, pActivityIntensity);
        myModuleNameToMaintain = pModuleToMaintain;
    }

    public String getModuleNameToMaintain() {
        return myModuleNameToMaintain;
    }
}