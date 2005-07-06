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
	private static final float myHydrolysisRate = 1f;
	private static final float myPQReductionRate = 1f;
	
    private float myQuantityEnergized = 0f;
    private Plastoquinone myPlastoquinone;
    private Chloroplast myChloroplast;
    private static final float LIGHT_ENERGY_RATIO_NEEDED = 1;
    private static final float WATER_MOLECULE_RATIO_NEEDED = 2;
    private static final float PROTON_RATIO_NEEDED = 2;
    private static final float PQ_RATIO_NEEDED = 1;
    private Lumen myLumen;
    private Stroma myStroma;
    
    public Photosystem2(Plastoquinone pPlastoquinone, Chloroplast pChloroplast, Lumen pLumen, Stroma pStroma){
        myPlastoquinone = pPlastoquinone;
        myChloroplast = pChloroplast;
        myLumen = pLumen;
        myStroma = pStroma;
    }

    
    public void tick(){
        hydrolize();
        reducePlastoquinone();
    }

    /**
	 * 
	 */
    private void reducePlastoquinone() {
    	float doubleProtonsAvailable = myStroma.getProtons().getQuantity() / PROTON_RATIO_NEEDED;
    	float plastoquinoneWithProtonsAvailable = myPlastoquinone.getNumberWithProtons() / PQ_RATIO_NEEDED;
        float ps2sToOxidize = myQuantityEnergized * myPQReductionRate;
        
        float pqsAbleToReduceFirst = Math.min(doubleProtonsAvailable, plastoquinoneWithProtonsAvailable);
        float pqsAbleToReduceFinal = Math.min(pqsAbleToReduceFirst, ps2sToOxidize);
    	float protonsTaken = myStroma.getProtons().take(pqsAbleToReduceFinal * PROTON_RATIO_NEEDED);
        myPlastoquinone.addProtonsAndElectron(pqsAbleToReduceFinal);
        myQuantityEnergized -= pqsAbleToReduceFinal;
        myLogger.debug("reduced "+ pqsAbleToReduceFinal +" plastoquinone");
    }

    /**
	 * 
	 */
    private void hydrolize() {
    	float ps2sToEnergize = (getQuantity() * myHydrolysisRate) - myQuantityEnergized;
        // need 680 nm for optimal absorption
        float lightEnergyAvailable = myChloroplast.getOrangeLight(ps2sToEnergize * LIGHT_ENERGY_RATIO_NEEDED);
    	float doubleWaterMoleculesAvailable = myLumen.getWaterMolecules().getQuantity() / WATER_MOLECULE_RATIO_NEEDED;
        float ps2sAbleToEnergizeFirst = Math.min(ps2sToEnergize, doubleWaterMoleculesAvailable);
        float ps2sAbleToEnergizeFinal = Math.min(ps2sAbleToEnergizeFirst, lightEnergyAvailable);
        float waterMoleculesToTake = ps2sAbleToEnergizeFinal * WATER_MOLECULE_RATIO_NEEDED;
        myLumen.getWaterMolecules().take(waterMoleculesToTake);
        myLumen.getProtons().add(waterMoleculesToTake * 2);
        myLumen.getOxygen().add(waterMoleculesToTake / 2);
        myQuantityEnergized += ps2sAbleToEnergizeFinal;
        myLogger.debug("hydrolyzed "+myQuantityEnergized+" photosystem2s" );
    }
    

	@Override
	public void reset() {
		myQuantityEnergized = 0f;
	}
}
