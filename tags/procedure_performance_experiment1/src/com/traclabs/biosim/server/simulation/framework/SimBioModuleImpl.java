package com.traclabs.biosim.server.simulation.framework;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.TRANSIENT;

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
		BioCommandListener currentListener = null;
		try{
			for (BioCommandListener listener : myListeners) {
				currentListener = listener;
				currentListener.newCommandSent(commandName);
			}
		}
		catch (TRANSIENT e){
			if (currentListener != null){
				myListeners.remove(currentListener);
				//try again
				notifyCommandSent(commandName);
			}
		}
	}
	public void registerBioCommandListener(BioCommandListener listener){
		myListeners.add(listener);
	}

}

