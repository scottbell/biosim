package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.server.simulation.crew.RepairActivityOperations;

/**
 * Repair Activities.
 * 
 * @author Scott Bell
 */

public class RepairActivity extends Activity implements
        RepairActivityOperations {
    private long myMalfunctionID = 0;

    private String myModuleNameToRepair = "unknown";

    public RepairActivity(String pModuleNameToRepair, long pMalfunctionID,
            int pTimeLength) {
        super("repair", pTimeLength, pTimeLength);
        myMalfunctionID = pMalfunctionID;
        myModuleNameToRepair = pModuleNameToRepair;
    }

    public String getModuleNameToRepair() {
        return myModuleNameToRepair;
    }

    public long getMalfunctionIDToRepair() {
        return myMalfunctionID;
    }
}