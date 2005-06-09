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
public class Photosystem2 {
    private Plastoquinone myPlastoquinone;

    private void hydrolyze(){
        // TODO get water, split it
        myPlastoquinone.reduce();
        
    }
}
