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
public class Plastoquinone extends PassiveEnzyme{
    private CytochromeB6F myCytochromeB6F;
    /**
     * 
     */
    public void reduce() {
        // TODO move protons into lumen
        myCytochromeB6F.reduce();
    }
    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
        // TODO Auto-generated method stub
        
    }

}
