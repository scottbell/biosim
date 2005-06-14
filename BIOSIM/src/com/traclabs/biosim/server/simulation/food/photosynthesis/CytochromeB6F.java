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
public class CytochromeB6F extends ActiveEnzyme{
    private Plastocyanin myPlastocyanin;
    /**
     * 
     */
    public void reduce() {
        myPlastocyanin.reduce();
        
    }
    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
        // TODO Auto-generated method stub
        
    }

}
