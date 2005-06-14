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
public class Photosystem2 extends ActiveEnzyme{
    private boolean energized = false;
    private Plastoquinone myPlastoquinone;
    private Chloroplast myChloroplast;
    private static final float LIGHT_ENERGY_NEEDED = 1;

    private void hydrolyze(){
        // TODO get water, split it
        
        
    }
    
    public void tick(){
        if (energized)
            myPlastoquinone.reduce();
        else
            attemptToEnergize();
    }

    /**
     * 
     */
    private void attemptToEnergize() {
        //need 680 nm for optimal absorption
        float lightEnergy = myChloroplast.getOrangeLight();
        if (lightEnergy > LIGHT_ENERGY_NEEDED)
            hydrolyze();
    }
}
