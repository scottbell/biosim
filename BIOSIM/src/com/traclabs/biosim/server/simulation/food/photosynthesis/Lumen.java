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
public class Lumen extends PassiveEnzyme{
    private Chemical myWaterMolecules = new Chemical(Float.MAX_VALUE / 2f);
    private Chemical myProtons = new Chemical(0);
    private Chemical myOxygen = new Chemical(0);

    /**
     * @return Returns the protons.
     */
    public Chemical getProtons() {
        return myProtons;
    }
    /**
     * @return Returns the water molecules.
     */
    public Chemical getWaterMolecules() {
        return myWaterMolecules;
    }
    /**
     * @return Returns the myOxygen.
     */
    public Chemical getOxygen() {
        return myOxygen;
    }
    
    public void tick(){
        myProtons.update();
        myWaterMolecules.update();
        myOxygen.update();
        myLogger.debug(myProtons);
        myLogger.debug(myWaterMolecules);
        myLogger.debug(myOxygen);
    }
}
