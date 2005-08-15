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
public class Photosystem2Test extends TestCase {
    private final static int ITERATIONS_TO_RUN = 102;

    private Photosystem2 myPhotosystem2;

    private Stroma myStroma;

    private Lumen myLumen;

    private Chloroplast myChloroplast;

    private Plastoquinone myPlastoquinone;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        myChloroplast = new Chloroplast();
        myStroma = new Stroma();
        myLumen = new Lumen();
        myPlastoquinone = new Plastoquinone();
        myPhotosystem2 = new Photosystem2(myPlastoquinone, myChloroplast,
                myLumen, myStroma);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        myChloroplast = null;
        myStroma = null;
        myLumen = null;
        myPlastoquinone = null;
        myPhotosystem2 = null;
    }

    public void testTick() {
        myLumen.getWaterMolecules().setQuantity(ITERATIONS_TO_RUN * 3);
        myLumen.getProtons().setQuantity(0);
        myLumen.getOxygen().setQuantity(0);
        myStroma.getProtons().setQuantity(ITERATIONS_TO_RUN * 3);
        
        float initialStromaProtons = myStroma.getProtons().getQuantity();
        float initialLumenProtons = myLumen.getProtons().getQuantity();
        float initialWaterMolecules = myLumen.getWaterMolecules().getQuantity();
        float initialProtons = initialStromaProtons + initialLumenProtons + (initialWaterMolecules * 2f);
        
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
        }
        
        float finalStromaProtons = myStroma.getProtons().getQuantity();
        float finalLumenProtons = myLumen.getProtons().getQuantity();
        float finalWaterMolecules = myLumen.getWaterMolecules().getQuantity();
        float finalProtons = finalStromaProtons + finalLumenProtons + (finalWaterMolecules * 2f);
        assertEquals(initialProtons, finalProtons, 0);
    }
}
