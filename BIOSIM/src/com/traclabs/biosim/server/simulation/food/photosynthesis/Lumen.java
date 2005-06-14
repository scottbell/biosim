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
public class Lumen {
    private Chemical myWaterMolecules = new Chemical(Float.MAX_VALUE / 2f);
    private Chemical myProtons = new Chemical(Float.MAX_VALUE / 2f);

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
}
