/*
 * Created on Jun 20, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import junit.framework.TestCase;

/**
 * @author scott
 *
 * TODO
 */
public class ChloroplastTest extends TestCase {
    private final static int ITERATIONS_TO_RUN = 3000;
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
        float initialNumberOfProtons = 2 * theLumen.getWaterMolecules().getQuantity() + theStroma.getNADPHs().getQuantity() + theStroma.getProtons().getQuantity() + theLumen.getProtons().getQuantity();
        for (int i = 0; i < ITERATIONS_TO_RUN; i++){
            myChloroplast.tick();
        }
        float finalNumberOfProtons = 2 * theLumen.getWaterMolecules().getQuantity() + theStroma.getNADPHs().getQuantity() + theStroma.getProtons().getQuantity() + theLumen.getProtons().getQuantity();
        assertEquals(initialNumberOfProtons, finalNumberOfProtons, 0);
    }

}
