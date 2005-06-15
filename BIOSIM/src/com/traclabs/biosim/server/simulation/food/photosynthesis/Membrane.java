/*
 * Created on Jun 14, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 * TODO
 */
public class Membrane {
    private Photosystem1 myPhotosystem1;
    private Photosystem2 myPhotosystem2;
    private Plastoquinone myPlastoquinone;
    private CytochromeB6F myCytochromeB6F;
    private Plastocyanin myPlastocyanin;
    private Ferredoxin myFerredoxin;
    private FNR myFNR;
    private ATPSynthase myATPSynthase;
    
    public Membrane(Chloroplast pChloroplast, Lumen pLumen, Stroma pStroma){
        myPlastoquinone = new Plastoquinone();
        myPlastocyanin = new Plastocyanin();
        myFerredoxin = new Ferredoxin();
        myPhotosystem1 = new Photosystem1(pChloroplast, myFerredoxin, myPlastocyanin);
        myPhotosystem2 = new Photosystem2(myPlastoquinone, null, pLumen, pStroma);
        myFNR = new FNR(myFerredoxin, pStroma);
        myCytochromeB6F = new CytochromeB6F(myPlastoquinone, myPlastocyanin, pLumen, pStroma);
        myATPSynthase = new ATPSynthase(pLumen, pStroma);
    }

    /**
     * @return
     */
    public Plastoquinone getPlastoquinone() {
        return myPlastoquinone;
    }

    /**
     * @return
     */
    public Plastocyanin getPlastocyanin() {
        return myPlastocyanin;
    }

    /**
     * @return
     */
    public Ferredoxin getFerredoxin() {
        return myFerredoxin;
    }

    /**
     * @return
     */
    public Photosystem2 getPhotosystem2() {
        return myPhotosystem2;
    }

    /**
     * @return
     */
    public CytochromeB6F getCytochromeB6F() {
        return myCytochromeB6F;
    }

    /**
     * @return
     */
    public Photosystem1 getPhotosystem1() {
        return myPhotosystem1;
    }

    /**
     * @return
     */
    public FNR getFNR() {
        return myFNR;
    }

    /**
     * @return
     */
    public ATPSynthase getATPSynthase() {
        return myATPSynthase;
    }
}
