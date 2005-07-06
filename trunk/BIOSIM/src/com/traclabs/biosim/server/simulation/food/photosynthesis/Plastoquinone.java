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
    private float myQuantityWithProtonsCurrent = getQuantity();
    
    public void addProtonsAndElectron(float quantityOfPQToAffect){
    	myQuantityWithProtonsCurrent += quantityOfPQToAffect;
    }
    
    public void removeElectronAndProtons(float quantityOfPQToAffect){
    	myQuantityWithProtonsCurrent -= quantityOfPQToAffect;
    }
    
    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
    }
    
    /**
     * @return
     */
    public float getNumberWithProtons() {
        return myQuantityWithProtonsCurrent;
    }

	@Override
	public void reset() {
		myQuantityWithProtonsCurrent = getQuantity();
	}

}
