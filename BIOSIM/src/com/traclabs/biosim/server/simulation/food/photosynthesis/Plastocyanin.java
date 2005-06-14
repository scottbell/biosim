/*
 * Created on Jun 8, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 * TODO
 */
public class Plastocyanin extends PassiveEnzyme{
    private Photosystem1 myPhotosystem1;
    /**
     * 
     */
    public void reduce() {
        myPhotosystem1.reduce();
        
    }
    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
        // TODO Auto-generated method stub
        
    }


}
