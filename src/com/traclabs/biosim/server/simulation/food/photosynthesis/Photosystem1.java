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
    private Chloroplast myChloroplast;
    private static final float LIGHT_ENERGY_NEEDED = 1;
    private boolean energized = false;
    private Ferredoxin myFerredoxin;
    private Plastocyanin myPlastocyanin;
    
    public Photosystem1(Chloroplast pChloroplast, Ferredoxin pFerredoxin, Plastocyanin pPlastocyanin){
        myChloroplast = pChloroplast;
        myFerredoxin = pFerredoxin;
        myPlastocyanin = pPlastocyanin;
    }
    
    
    public void tick() {
        if (energized)
            attemptToReduceFerredoxin();
        else
            attemptToEnergize(); 
    }


    /**
     * 
     */
    private void attemptToReduceFerredoxin() {
        if (!myFerredoxin.hasElectron()){
            myFerredoxin.reduce();
            energized = false;
            myLogger.debug("reduced ferredoxin!");
        }
        
    }


    /**
     * 
     */
    private void attempToOxidizePlastocyanin() {
        if (myPlastocyanin.hasElectron()){
            myPlastocyanin.oxidize();
            energized = true;
            myLogger.debug("oxidized plastocyanin!");
        }
    }


    /**
     * 
     */
    private void attemptToEnergize() {
        //need 700 nm for optimal absorption
        float lightEnergy = myChloroplast.getRedLight(LIGHT_ENERGY_NEEDED);
        if (lightEnergy >= LIGHT_ENERGY_NEEDED)
            attempToOxidizePlastocyanin();
    }

    /**
     * @return Returns the energized.
     */
    public boolean isEnergized() {
        return energized;
    }


	@Override
	public void reset() {
		energized = false;
	}
}
