package com.traclabs.biosim.server.simulation.framework;

import java.util.ArrayList;
import java.util.List;

import com.traclabs.biosim.idl.simulation.framework.BioCommandListener;
import com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;

/**
 * The SimBioModule Implementation. Modules specific to the simulation.
 * 
 * @author Scott Bell
 */

public abstract class SimBioModuleImpl extends BioModuleImpl implements SimBioModuleOperations {
	private List<BioCommandListener> myListeners = new ArrayList<BioCommandListener>();

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
		for (BioCommandListener listener : myListeners) {
			listener.newCommandSent(commandName);
		}
	}
	public void registerBioCommandListener(BioCommandListener listener){
		myListeners.add(listener);
	}

}

