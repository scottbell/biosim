/*
 * Created on Jun 20, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import junit.framework.TestCase;

/**
 * @author scott
 *
 */
public class ChloroplastTest extends TestCase {
    private final static int ITERATIONS_TO_RUN = 10000000;
    private Chloroplast myChloroplast;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        myChloroplast = new Chloroplast();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        myChloroplast = null;
    }

    public void testTick() {
        Stroma theStroma = myChloroplast.getStroma();
        Lumen theLumen = myChloroplast.getThylakoid().getLumen();
        float initialLumenProtons = theLumen.getProtons().getQuantity();
        float initialLumenWaterMolecules = theLumen.getWaterMolecules().getQuantity();
        float initialStromaProtons = theStroma.getProtons().getQuantity();
        float initialStromaNADPHs = theStroma.getNADPHs().getQuantity();
        float initialNumberOfProtons = initialLumenProtons + initialStromaProtons + initialStromaNADPHs + (2 * initialLumenWaterMolecules);
        
        for (int i = 0; i < ITERATIONS_TO_RUN; i++){
            myChloroplast.tick();
        }
        float finalLumenProtons = theLumen.getProtons().getQuantity();
        float finalLumenWaterMolecules = theLumen.getWaterMolecules().getQuantity();
        float finalStromaProtons = theStroma.getProtons().getQuantity();
        float finalStromaNADPHs = theStroma.getNADPHs().getQuantity();
        float finalNumberOfProtons = finalLumenProtons + finalStromaProtons + finalStromaNADPHs + (2 * finalLumenWaterMolecules);
        
        assertEquals(initialNumberOfProtons, finalNumberOfProtons, 0);
    }

}
