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
public class Plastoquinone {
    private CytochromeB6F myCytochromeB6F;
    /**
     * 
     */
    public void reduce() {
        // TODO move protons into lumen
        myCytochromeB6F.reduce();
    }

}
