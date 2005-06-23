/*
 * Created on Jun 8, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 */
public class Photosystem2 extends ActiveEnzyme{
    private boolean energized = false;
    private Plastoquinone myPlastoquinone;
    private Chloroplast myChloroplast;
    private static final float LIGHT_ENERGY_NEEDED = 1;
    private static final float WATER_MOLECULES_NEEDED = 2;
    private static final float PROTONS_NEEDED = 2;
    private Lumen myLumen;
    private Stroma myStroma;
    
    public Photosystem2(Plastoquinone pPlastoquinone, Chloroplast pChloroplast, Lumen pLumen, Stroma pStroma){
        myPlastoquinone = pPlastoquinone;
        myChloroplast = pChloroplast;
        myLumen = pLumen;
        myStroma = pStroma;
    }

    private void hydrolyze(){
        float waterMoleculesTaken = myLumen.getWaterMolecules().take(WATER_MOLECULES_NEEDED);
        if (waterMoleculesTaken == WATER_MOLECULES_NEEDED){
            myLumen.getProtons().add(WATER_MOLECULES_NEEDED * 2);
            myLumen.getOxygen().add(WATER_MOLECULES_NEEDED / 2);
            energized = true;
            myLogger.debug("hydrolyzed!");
        }
        else{
            myLumen.getWaterMolecules().add(waterMoleculesTaken);
        }
    }
    
    public void tick(){
        if (energized)
            attempToReducePlastoquinone();
        else
            attemptToEnergize();
    }

    /**
     * 
     */
    private void attempToReducePlastoquinone() {
        if (!myPlastoquinone.hasProtons()){
            float protonsTaken = myStroma.getProtons().take(PROTONS_NEEDED);
            if (protonsTaken == PROTONS_NEEDED){
                myPlastoquinone.addProtonsAndElectron();
                energized = false;
                myLogger.debug("reduced plastoquinone!");
            }
            else{
                myStroma.getProtons().add(protonsTaken);
            }
        }
    }

    /**
     * 
     */
    private void attemptToEnergize() {
        //need 680 nm for optimal absorption
        float lightEnergy = myChloroplast.getOrangeLight();
        if (lightEnergy >= LIGHT_ENERGY_NEEDED)
            hydrolyze();
    }
    /**
     * @return Returns the energized.
     */
    public boolean isEnergized() {
        return energized;
    }
}
