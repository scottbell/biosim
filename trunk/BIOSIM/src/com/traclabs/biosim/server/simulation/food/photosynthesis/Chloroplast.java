/*
 * Created on Jun 8, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.OrbUtils;

/**
 * @author scott
 *  
 */
public class Chloroplast {
    private ActiveEnzyme[] myActiveEnzymes = new ActiveEnzyme[5];

    private PassiveEnzyme[] myPassiveEnzymes = new PassiveEnzyme[5];

    private Stroma myStroma;

    private Thylakoid myThylakoid;
    
    private Logger myLogger;

    public Chloroplast() {
        myLogger = Logger.getLogger(Chloroplast.class);
        myStroma = new Stroma();
        myThylakoid = new Thylakoid(this, myStroma);

        myPassiveEnzymes[0] = myStroma;
        myPassiveEnzymes[1] = myThylakoid.getLumen();
        myPassiveEnzymes[2] = myThylakoid.getMembrane().getPlastoquinone();
        myPassiveEnzymes[3] = myThylakoid.getMembrane().getPlastocyanin();
        myPassiveEnzymes[4] = myThylakoid.getMembrane().getFerredoxin();

        myActiveEnzymes[0] = myThylakoid.getMembrane().getPhotosystem2();
        myActiveEnzymes[1] = myThylakoid.getMembrane().getCytochromeB6F();
        myActiveEnzymes[2] = myThylakoid.getMembrane().getPhotosystem1();
        myActiveEnzymes[3] = myThylakoid.getMembrane().getFNR();
        myActiveEnzymes[4] = myThylakoid.getMembrane().getATPSynthase();

    }

    /**
     * @return
     */
    public float getOrangeLight() {
        // TODO Auto-generated method stub
        return 1;
    }

    /**
     * @return
     */
    public float getRedLight() {
        // TODO Auto-generated method stub
        return 1;
    }

    public void tick() {
        int[] randomIndexesForActiveEnzymes = generateRandomIndexes(myActiveEnzymes.length);
        int[] randomIndexesForPassiveEnzymes = generateRandomIndexes(myPassiveEnzymes.length);
        //tick the active enzymes
        for (int i = 0; i < myActiveEnzymes.length; i++) {
            int randomIndex = randomIndexesForActiveEnzymes[i];
            myActiveEnzymes[randomIndex].tick();
        }
        //tick the passive enzymes
        for (int i = 0; i < myPassiveEnzymes.length; i++) {
            int randomIndex = randomIndexesForPassiveEnzymes[i];
            myPassiveEnzymes[randomIndex].tick();
        }
    }

    /**
     * @param length
     * @return
     */
    private int[] generateRandomIndexes(int length) {
        int[] randomArray = new int[length];
        for (int i = 0; i < randomArray.length; i++)
            randomArray[i] = i;
        return randomArray;
    }

    public static void main(String args[]) {
        OrbUtils.initializeLog();
        Logger theLogger = Logger.getLogger(Chloroplast.class + ".main()");
        Chloroplast newChloroplast = new Chloroplast();
        while (true) {
            theLogger.debug("********TICK********");
            newChloroplast.tick();
        }
    }

    /**
     * @return Returns the Stroma.
     */
    public Stroma getStroma() {
        return myStroma;
    }
    /**
     * @return Returns the Thylakoid.
     */
    public Thylakoid getThylakoid() {
        return myThylakoid;
    }
}
