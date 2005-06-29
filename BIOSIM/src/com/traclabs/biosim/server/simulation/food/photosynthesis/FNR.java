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
    private Stroma myStroma;
    private boolean complexHasFormed = false;
    private static final float PROTONS_NEEDED = 1;
    private static final float NADP_NEEDED = 1;
    
    public FNR(Ferredoxin pFerredoxin, Stroma pStroma){
        myFerredoxin = pFerredoxin;
        myStroma = pStroma;
    }

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
        myLogger.debug("attempting to form NADPH!");
        if ((protonsTaken == PROTONS_NEEDED) && (NADPTaken == NADP_NEEDED)){
            myStroma.getNADPHs().add(NADPTaken);
            debindComplex();
            myLogger.debug("formed NADPH!");
        }
        else{
            myLogger.debug("only got "+protonsTaken+" protons and "+NADPTaken+" NAPDs");
            myStroma.getProtons().add(protonsTaken);
            myStroma.getNADPs().add(NADPTaken);
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
    private void attemptToFormFerredoxinFNRComplex() {
        if (myFerredoxin.hasElectron()){
            myFerredoxin.oxidize();
            complexHasFormed = true;
            myLogger.debug("formed FNRComplex!");
        }
    }

    /**
     * @return Returns the complexHasFormed.
     */
    public boolean hasComplexHasFormed() {
        return complexHasFormed;
    }

	public void reset() {
		complexHasFormed = false;
	}
}
