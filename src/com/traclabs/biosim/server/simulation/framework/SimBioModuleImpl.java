package com.traclabs.biosim.server.simulation.framework;

import java.util.ArrayList;
import java.util.List;

import com.traclabs.biosim.idl.simulation.framework.CommandListener;
import com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;

/**
 * The SimBioModule Implementation. Modules specific to the simulation.
 * 
 * @author Scott Bell
 */

public abstract class SimBioModuleImpl extends BioModuleImpl implements SimBioModuleOperations {
	private List<CommandListener> myListeners = new ArrayList<CommandListener>();

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
		for (CommandListener listener : myListeners) {
			listener.newCommandSent(commandName);
		}
	}
	public void registerCommandListener(CommandListener listener){
		myListeners.add(listener);
	}

}

