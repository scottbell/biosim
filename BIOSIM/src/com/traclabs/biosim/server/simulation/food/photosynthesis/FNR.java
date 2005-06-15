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
public class FNR extends ActiveEnzyme{
    private Ferredoxin myFerredoxin;
    private boolean complexHasFormed = false;
    private static final float PROTONS_NEEDED = 1;
    private static final float NADP_NEEDED = 1;
    private Stroma myStroma;

    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.simulation.food.photosynthesis.Enzyme#tick()
     */
    public void tick() {
        if (complexHasFormed)
            attemptToFormNADPH();
        else
            attemptToFormFerredoxinFNRComplex();
    }

    /**
     * 
     */
    private void attemptToFormNADPH() {
        float protonsTaken = myStroma.getProtons().take(PROTONS_NEEDED);
        float NADPTaken = myStroma.getNADPs().take(NADP_NEEDED);
        if ((protonsTaken == PROTONS_NEEDED) && (NADPTaken == NADP_NEEDED)){
            myStroma.getNADPHs().add(NADPTaken);
            debindComplex();
        }
    }
    
    /**
     * 
     */
    private void debindComplex() {
        complexHasFormed = false;
    }

    /**
     * 
     */
    public void attemptToFormFerredoxinFNRComplex() {
        if (myFerredoxin.hasElectron()){
            myFerredoxin.oxidize();
            complexHasFormed = true;
        }
    }

}
