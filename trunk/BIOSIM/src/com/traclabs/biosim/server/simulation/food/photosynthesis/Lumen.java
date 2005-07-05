/*
 * Created on Jun 8, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;


/**
 * @author scott
 *
 */
public class Lumen extends PassiveEnzyme{
    private Chemical myWaterMolecules = new Chemical(Float.MAX_VALUE);
    private Chemical myProtons = new Chemical(0);
    private Chemical myOxygen = new Chemical(0);
    private Chemical[] myChemicals = {myWaterMolecules, myProtons, myOxygen};

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
        for (Chemical chemical : myChemicals) 
        	chemical.tick();
    }
	public void reset() {
        for (Chemical chemical : myChemicals) 
        	chemical.reset();
		
	}
}
