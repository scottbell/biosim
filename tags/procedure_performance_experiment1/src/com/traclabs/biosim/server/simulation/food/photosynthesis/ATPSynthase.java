/*
 * Created on Jun 14, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import java.util.Random;

import com.traclabs.biosim.server.util.MathUtils;
import com.traclabs.biosim.util.MersenneTwister;

/**
 * @author scott
 *
 */
public class ATPSynthase extends ActiveEnzyme {
	private boolean energized = false;
    private final static float PROTON_NEEDED_BASE = 20;
    private final static float ADP_NEEDED_BASE = 1f;
    private final static float PHOSPHATE_NEEDED_BASE = 1f;
    private Lumen myLumen;
    private Stroma myStroma;
    private Random myRandomGen = new MersenneTwister();
    
    public ATPSynthase(Lumen pLumen, Stroma pStroma){
        myLumen = pLumen;
        myStroma = pStroma;
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
    	/*
        float ADPTaken = myStroma.getADPs().take(getADPsNeeded());
        float phosphateTaken = myStroma.getPhosphates().take(getPhosphatesNeeded());
        if ((ADPTaken == getADPsNeeded()) && (phosphateTaken == getPhosphatesNeeded())){
            myStroma.getATPs().add(ADPTaken);
            myLogger.debug("synthesized ATP!");
        	energized = false;
        }
        else{
            myStroma.getADPs().add(ADPTaken);
            myStroma.getPhosphates().add(phosphateTaken);
        }
        */
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
    	float protonsFromLumen = myLumen.getProtons().getQuantity();
    	float protonsFromStroma = myStroma.getProtons().getQuantity();
    	float protonDifference = protonsFromLumen - protonsFromStroma;
    	if (protonDifference <= 0)
    		return false;
        float randomNumber = myRandomGen.nextFloat();
        float protonThreshold = MathUtils.calculateSCurve(protonDifference, PROTON_NEEDED_BASE * 2f);
        return (protonThreshold > randomNumber);
    }

	public boolean isEnergized() {
		return energized;
	}

	public void reset() {
		energized = false;
	}
	
}
