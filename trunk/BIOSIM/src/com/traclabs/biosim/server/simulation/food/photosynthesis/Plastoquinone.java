/*
 * Created on Jun 8, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 */
public class Plastoquinone extends PassiveEnzyme{
    private boolean hasProtonsCached = false;
    private boolean hasProtonsCurrent = false;
    
    public void addProtonsAndElectron(){
        hasProtonsCurrent = true;
    }
    
    public void removeElectronAndProtons(){
        hasProtonsCurrent = false;
    }
    
    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
        hasProtonsCached = hasProtonsCurrent;
        
    }
    /**
     * @return
     */
    public boolean hasProtons() {
        return hasProtonsCached;
    }

	@Override
	public void reset() {
		hasProtonsCurrent = hasProtonsCached = false;
	}

}
