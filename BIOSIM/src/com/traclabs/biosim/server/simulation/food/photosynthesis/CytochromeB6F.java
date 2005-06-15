/*
 * Created on Jun 8, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 */
public class CytochromeB6F extends ActiveEnzyme{
    private Plastoquinone myPlastoquinone;
    private Plastocyanin myPlastocyanin;
    private Lumen myLumen;
    private Stroma myStroma;
    private static final float PROTONS_NEEDED = 2;
    private float electrons = 0f;
    
    public CytochromeB6F(Plastoquinone pPlastoquinone, Plastocyanin pPlastocyanin, Lumen pLumen, Stroma pStroma){
        myPlastoquinone = pPlastoquinone;
        myPlastocyanin = pPlastocyanin;
        myLumen = pLumen;
        myStroma = pStroma;
    }
    
    public void tick() {
        if (electrons >= 2){
            attemptToReducePlastocyanin();
            attemptToReducePlastoquinone();
        }
        if (electrons >= 1)
            attemptToReducePlastocyanin();
        else
            attemptToOxidizePlastoquinone();
    }

    /**
     * 
     */
    private void attemptToReducePlastocyanin() {
        if (!myPlastocyanin.hasElectron()){
            myPlastocyanin.reduce();
            electrons--;
        }
    }

    /**
     * 
     */
    private void attemptToOxidizePlastoquinone() {
        if (myPlastoquinone.hasProtons()){
            myPlastoquinone.removeElectronAndProtons();
            myLumen.getProtons().add(PROTONS_NEEDED);
            electrons = PROTONS_NEEDED;
        }
    }
    
    /**
     * back reaction
     */
    private void attemptToReducePlastoquinone() {
        if (!myPlastoquinone.hasProtons()){
            float protonsTaken = myStroma.getProtons().take(PROTONS_NEEDED);
            if (protonsTaken == PROTONS_NEEDED){
                myPlastoquinone.addProtonsAndElectron();
                electrons--;
            }
        }
    }

}
