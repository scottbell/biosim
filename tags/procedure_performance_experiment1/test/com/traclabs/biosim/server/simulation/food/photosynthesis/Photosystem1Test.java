/*
 * Created on Jun 16, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import junit.framework.TestCase;

import com.traclabs.biosim.util.OrbUtils;

/**
 * @author scott
 *
 */
public class Photosystem1Test extends TestCase {
    private final static int ITERATIONS_TO_RUN = 103;

    private Photosystem1 myPhotosystem1;

    private Chloroplast myChloroplast;

    private Ferredoxin myFerredoxin;

    private Plastocyanin myPlastocyanin;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        OrbUtils.initializeLog();
        myPlastocyanin = new Plastocyanin();
        myChloroplast = new Chloroplast();
        myFerredoxin = new Ferredoxin();
        myPhotosystem1 = new Photosystem1(myChloroplast, myFerredoxin, myPlastocyanin);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        myPlastocyanin = null;
        myFerredoxin = null;
        myPhotosystem1 = null;
    }

    public void testTick() {
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
            //act like FNR and oxidize FDX
            myFerredoxin.oxidize();
            myFerredoxin.tick();
            
            //act like Cytb6f and ready PC with electron
            myPlastocyanin.reduce();
            myPlastocyanin.tick();
            
            assertEquals(myPhotosystem1.isEnergized(), false);
            assertEquals(myPlastocyanin.hasElectron(), true);
            assertEquals(myFerredoxin.hasElectron(), false);

            myPhotosystem1.tick();
            myPlastocyanin.tick();
            myFerredoxin.tick();

            assertEquals(myPhotosystem1.isEnergized(), true);
            assertEquals(myPlastocyanin.hasElectron(), false);
            assertEquals(myFerredoxin.hasElectron(), false);

            myPhotosystem1.tick();
            myPlastocyanin.tick();
            myFerredoxin.tick();

            assertEquals(myPhotosystem1.isEnergized(), false);
            assertEquals(myPlastocyanin.hasElectron(), false);
            assertEquals(myFerredoxin.hasElectron(), true);
        }
    }

}
