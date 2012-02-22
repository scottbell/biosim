package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.CommandListener;
import com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;

/**
 * The SimBioModule Implementation. Modules specific to the simulation.
 * 
 * @author Scott Bell
 */

public abstract class SimBioModuleImpl extends BioModuleImpl implements
        SimBioModuleOperations {

    /**
     * Constructor to create a BioModule, should only be called by those
     * deriving from BioModule.
     * 
     * @param pID
     *            The unique ID for this module (all the modules this module
     *            communicates with should have the same ID)
     */
    protected SimBioModuleImpl(int pID, String pName) {
        super(pID, pName);
    }
    
	public void notifyCommandSent(String commandName){
		
	}
	public void registerCommandListener(CommandListener listener){
		
	}

}

