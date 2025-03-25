package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.server.simulation.crew.MaitenanceActivityOperations;

/**
 * Repair Activities.
 * 
 * @author Scott Bell
 */

public class MaitenanceActivity extends Activity implements
        MaitenanceActivityOperations {
    private String myModuleNameToMaintain = "Unknown";

    public MaitenanceActivity(String pName, int pTimeLength,
            int pActivityIntensity, String pModuleToMaintain) {
        super(pName, pTimeLength, pActivityIntensity);
        myModuleNameToMaintain = pModuleToMaintain;
    }

    public String getModuleNameToMaintain() {
        return myModuleNameToMaintain;
    }
}