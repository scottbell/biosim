package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.PassiveModuleOperations;

/**
 * The SimBioModule Implementation. Modules specific to the simulation.
 * 
 * @author Scott Bell
 */

public abstract class PassiveModuleImpl extends SimBioModuleImpl implements
        PassiveModuleOperations {

    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     * 
     * @param pID
     *            The unique ID for this module (all the modules this module
     *            communicates with should have the same ID)
     */
    protected PassiveModuleImpl(int pID, String pName) {
        super(pID, pName);
    }
}

