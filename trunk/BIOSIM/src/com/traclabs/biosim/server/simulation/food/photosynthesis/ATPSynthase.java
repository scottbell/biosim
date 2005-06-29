/*
 * Created on Jun 14, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import java.util.Random;

import com.traclabs.biosim.server.util.MathUtils;

/**
 * @author scott
 *
 */
public class ATPSynthase extends ActiveEnzyme {
	private boolean energized = false;
    private final static float PROTON_NEEDED = 20;
    private final static float ADP_NEEDED = 1f;
    private final static float PHOSPHATE_NEEDED = 1f;
    private Lumen myLumen;
    private Stroma myStroma;
    private Random myRandomGen;
    
    public ATPSynthase(Lumen pLumen, Stroma pStroma){
        myLumen = pLumen;
        myStroma = pStroma;
        myRandomGen = new Random();
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
    	if (energized)
            attemptToSynthesizeATP();
    	else
    		attemptToPumpProtons();
    }

    private void attemptToPumpProtons() {
        if (protonThresholdMet()){
            pumpProtons();
            energized = true;
        }
        else
        	energized = false;
	}

	/**
     * 
     */
    private void attemptToSynthesizeATP() {
        float ADPTaken = myStroma.getADPs().take(ADP_NEEDED);
        float phosphateTaken = myStroma.getPhosphates().take(PHOSPHATE_NEEDED);
        if ((ADPTaken == ADP_NEEDED) && (phosphateTaken == PHOSPHATE_NEEDED)){
            myStroma.getATPs().add(ADPTaken);
            myLogger.debug("synthesized ATP!");
        	energized = false;
        }
        else{
            myStroma.getADPs().add(ADPTaken);
            myStroma.getPhosphates().add(phosphateTaken);
        }
    }

    /**
     * Do the protons aid in ATP formation?
     */
    private void pumpProtons() {
        float protonsToTake = myLumen.getProtons().getQuantity();
        myLumen.getProtons().take(protonsToTake);
        myStroma.getProtons().add(protonsToTake);
        myLogger.debug("pumped "+ protonsToTake+ " protons!");
    }

    /**
     * @return
     */
    private boolean protonThresholdMet() {
    	float protons = myLumen.getProtons().getQuantity();
    	if (protons <= 0)
    		return false;
        float randomNumber = myRandomGen.nextFloat();
        float protonThreshold = MathUtils.calculateSCurve(protons, PROTON_NEEDED * 2f);
        return (protonThreshold > randomNumber);
    }

	public boolean isEnergized() {
		return energized;
	}

	public void reset() {
		energized = false;
	}

}
