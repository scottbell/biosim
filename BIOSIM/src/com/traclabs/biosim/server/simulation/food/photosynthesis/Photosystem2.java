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
    private float myQuantityEnergized = 0f;
    private Plastoquinone myPlastoquinone;
    private Chloroplast myChloroplast;
    private static final float LIGHT_ENERGY_RATIO_NEEDED = 1;
    private static final float WATER_MOLECULE_RATIO_NEEDED = 2;
    private static final float PROTON_RATIO_NEEDED = 2;
    private Lumen myLumen;
    private Stroma myStroma;
    
    public Photosystem2(Plastoquinone pPlastoquinone, Chloroplast pChloroplast, Lumen pLumen, Stroma pStroma){
        myPlastoquinone = pPlastoquinone;
        myChloroplast = pChloroplast;
        myLumen = pLumen;
        myStroma = pStroma;
    }

    private void hydrolyze(){
        float waterMoleculesTaken = myLumen.getWaterMolecules().take(WATER_MOLECULE_RATIO_NEEDED);
        if (waterMoleculesTaken == WATER_MOLECULE_RATIO_NEEDED){
            myLumen.getProtons().add(WATER_MOLECULE_RATIO_NEEDED * 2);
            myLumen.getOxygen().add(WATER_MOLECULE_RATIO_NEEDED / 2);
            energized = true;
            myLogger.debug("hydrolyzed!");
        }
        else{
            myLumen.getWaterMolecules().add(waterMoleculesTaken);
        }
    }
    
    public void tick(){
        if (myQuantityEnergized > 0f)
            attempToReducePlastoquinone();
        attemptToEnergize();
    }

    /**
     * 
     */
    private void attempToReducePlastoquinone() {
    	float protonsInStroma = myStroma.getProtons().getQuantity();
    	float plastoquinoneWithProtons = myPlastoquinone.getNumberWithProtons();
        if (plastoquinoneWithProtons > 0)
        	float protonsTaken = myStroma.getProtons().take(getProtonsNeeded());
            myPlastoquinone.addProtonsAndElectron();
            energized = false;
            myLogger.debug("reduced plastoquinone!");
        }
    }

    /**
     * 
     */
    private void attemptToEnergize() {
        //need 680 nm for optimal absorption
        float lightEnergy = myChloroplast.getOrangeLight();
        if (lightEnergy >= LIGHT_ENERGY_RATIO_NEEDED)
            hydrolyze();
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
