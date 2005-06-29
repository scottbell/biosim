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
    private static final int PROTONS_NEEDED = 2;
    private int electrons = 0;
    
    public CytochromeB6F(Plastoquinone pPlastoquinone, Plastocyanin pPlastocyanin, Lumen pLumen, Stroma pStroma){
        myPlastoquinone = pPlastoquinone;
        myPlastocyanin = pPlastocyanin;
        myLumen = pLumen;
        myStroma = pStroma;
    }
    
    public void tick() {
        if (electrons >= 2){
            attemptToReducePlastocyanin();
            //attemptToReducePlastoquinone();
        }
        else if (electrons >= 1){
            attemptToReducePlastocyanin();
        }
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
            myLogger.debug("reduced plastocyanin!");
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
            myLogger.debug("oxidized plastoquinone!");
        }
    }
    
    /**
     * back reaction
     */
    @SuppressWarnings("unused")
	private void attemptToReducePlastoquinone() {
        if (!myPlastoquinone.hasProtons()){
            float protonsTaken = myStroma.getProtons().take(PROTONS_NEEDED);
            if (protonsTaken == PROTONS_NEEDED){
                myPlastoquinone.addProtonsAndElectron();
                electrons--;
                myLogger.debug("reduced plastoquinone!");
            }
            else{
                myStroma.getProtons().add(protonsTaken);
            }
        }
    }

    /**
     * @return Returns the electrons.
     */
    public int getNumberOfElectrons() {
        return electrons;
    }

	public void reset() {
		electrons = 0;
	}
}
