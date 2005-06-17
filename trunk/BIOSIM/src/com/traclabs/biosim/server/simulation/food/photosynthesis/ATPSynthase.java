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
        float protonThreshold = MathUtils.calculateSCurve(myLumen.getProtons().getQuantity(), PROTON_NEEDED);
        return (protonThreshold > randomNumber);
    }

}
