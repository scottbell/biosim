/*
 * Created on Jun 14, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import java.util.Random;

/**
 * @author scott
 *
 */
public class ATPSynthase extends ActiveEnzyme {
    private final static float PROTON_NEEDED = 25;
    private final static float PROTON_SLOPE = 2f;
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
        if (protonThresholdMet()){
            pumpProtons();
            synthesizeATP();
        }
    }

    /**
     * 
     */
    private void synthesizeATP() {
        float ADPTaken = myStroma.getADPs().take(ADP_NEEDED);
        float phosphateTaken = myStroma.getPhosphates().take(PHOSPHATE_NEEDED);
        if ((ADPTaken == ADP_NEEDED) && (phosphateTaken == PHOSPHATE_NEEDED)){
            myStroma.getATPs().add(ADPTaken);
        }
    }

    /**
     * Do the protons aid in ATP formation?
     */
    private void pumpProtons() {
        float protonsToTake = myLumen.getProtons().getQuantity();
        myLumen.getProtons().take(protonsToTake);
        myStroma.getProtons().add(protonsToTake);
    }

    /**
     * @return
     */
    private boolean protonThresholdMet() {
        float randomNumber = myRandomGen.nextFloat();
        float protonThreshold = sigmoidLikeProbability(myLumen.getProtons().getQuantity() / PROTON_NEEDED);
        return (protonThreshold > randomNumber);
    }
    
    private static float sigmoidLikeProbability(float x) {
        if (x >= 1f)
            return 1f;
        else if ((x < 1f) && (x > 0f))
            return 0.3f * x * (1f - Math.abs(x - 2f) / 2f);
        else
            return 0f;
    }

}
