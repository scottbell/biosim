/*
 * Created on Jun 16, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import junit.framework.TestCase;

/**
 * @author scott
 * 
 */
public class CytochromeB6FTest extends TestCase {
    private final static int ITERATIONS_TO_RUN = 107;

    private CytochromeB6F myCytochromeB6F;

    private Stroma myStroma;

    private Lumen myLumen;

    private Plastoquinone myPlastoquinone;

    private Plastocyanin myPlastocyanin;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        myPlastocyanin = new Plastocyanin();
        myStroma = new Stroma();
        myLumen = new Lumen();
        myPlastoquinone = new Plastoquinone();
        myCytochromeB6F = new CytochromeB6F(myPlastoquinone, myPlastocyanin,
                myLumen, myStroma);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        myPlastocyanin = null;
        myStroma = null;
        myLumen = null;
        myPlastoquinone = null;
        myCytochromeB6F = null;
    }

    public void testTick() {
        myStroma.getProtons().setQuantity(ITERATIONS_TO_RUN * 3);
        myLumen.getProtons().setQuantity(0);
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
            //act like PS2 and ready PQ with protons and electrons
            myPlastoquinone.addProtonsAndElectron();
            myPlastoquinone.tick();
            
            assertEquals(myCytochromeB6F.getNumberOfElectrons(), 0);
            assertEquals(myPlastoquinone.hasProtons(), true);
            float lumenProtonLevel = myLumen.getProtons().getQuantity();
            float stromaProtonLevel = myStroma.getProtons().getQuantity();

            myCytochromeB6F.tick();
            myStroma.tick();
            myLumen.tick();
            myPlastoquinone.tick();
            myPlastocyanin.tick();

            assertEquals(myCytochromeB6F.getNumberOfElectrons(), 2);
            assertEquals(myPlastoquinone.hasProtons(), false);
            assertEquals(lumenProtonLevel, myStroma.getProtons()
                    .getQuantity(), stromaProtonLevel + 2);
            assertEquals(stromaProtonLevel, myStroma.getProtons()
                    .getQuantity(), stromaProtonLevel);

            myCytochromeB6F.tick();
            myStroma.tick();
            myLumen.tick();
            myPlastoquinone.tick();
            myPlastocyanin.tick();

            assertEquals(myCytochromeB6F.getNumberOfElectrons(), 0);
            assertEquals(myPlastoquinone.hasProtons(), true);
            assertEquals(lumenProtonLevel, myStroma.getProtons()
                    .getQuantity(), stromaProtonLevel + 2);
            assertEquals(stromaProtonLevel, myStroma.getProtons()
                    .getQuantity(), stromaProtonLevel - 2);

            //act like PS1 and oxidize PC
            myPlastocyanin.oxidize();
            myPlastocyanin.tick();
        }
        
    }

}
