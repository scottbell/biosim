/*
 * Created on Jun 14, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 * TODO
 */
public class ElectronCarryingEnzyme extends PassiveEnzyme {
    private boolean hasElectronCurrent = false;
    private boolean hasElectronCached = false;
    
    public void tick() {
        hasElectronCached = hasElectronCurrent;
    }
    
    /**
     * @return
     */
    public boolean hasElectron() {
        return hasElectronCached;
    }
    
    /**
     * 
     */
    public void reduce() {
        hasElectronCurrent = true;
        
    }
    
    /**
     * 
     */
    public void oxidize() {
        hasElectronCurrent = false;
        
    }

	public void reset() {
		hasElectronCached = hasElectronCurrent = false;
	}
}
