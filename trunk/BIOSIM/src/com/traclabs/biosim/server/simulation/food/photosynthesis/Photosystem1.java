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
public class Photosystem1 extends ActiveEnzyme{
    private Ferredoxin myFerredoxin;
    /**
     * 
     */
    public void reduce() {
        // TODO wait for more light
        myFerredoxin.reduce();
    }
    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
        // TODO Auto-generated method stub
        
    }

}
