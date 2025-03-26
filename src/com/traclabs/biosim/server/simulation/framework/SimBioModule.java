package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.framework.BioModule;

/**
 * The SimBioModule implementation. Modules specific to the simulation.
 * 
 * @author Scott Bell
 */

public abstract class SimBioModule extends BioModule {
    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     * 
     * @param pID
     *            The unique ID for this module (all the modules this module
     *            communicates with should have the same ID)
     */
    protected SimBioModule(int pID, String pName) {
        super(pID, pName);
    }


}

