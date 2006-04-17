/*
 * Created on Jun 16, 2005
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
public class FNRTest extends TestCase {
    private final static int ITERATIONS_TO_RUN = 103;

    private Ferredoxin myFerredoxin;

    private Stroma myStroma;
    
    private FNR myFNR;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        myFerredoxin = new Ferredoxin();
        myStroma = new Stroma();
        myFNR = new FNR(myFerredoxin, myStroma);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        myFerredoxin = null;
        myStroma = null;
        myFNR = null;
    }

    public void testTick() {
        myStroma.getProtons().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getNADPs().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getNADPHs().setQuantity(0);
        
        float initialStromaProtonLevel = myStroma.getProtons().getQuantity() + myStroma.getNADPHs().getQuantity();
        
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
            //act like PS1 and reduce FDX
            myFerredoxin.reduce();
            myFerredoxin.tick();
            
            assertEquals(myFNR.hasComplexHasFormed(), false);
            assertEquals(myFerredoxin.hasElectron(), true);
            float stromaProtonLevel = myStroma.getProtons().getQuantity();
            float stromaNAPDLevel = myStroma.getNADPs().getQuantity();
            float stromaNAPDHLevel = myStroma.getNADPHs().getQuantity();

            myFNR.tick();
            myFerredoxin.tick();
            myStroma.tick();

            assertEquals(myFNR.hasComplexHasFormed(), true);
            assertEquals(myFerredoxin.hasElectron(), false);
            assertEquals(myStroma.getProtons().getQuantity(), stromaProtonLevel, 0);
            assertEquals(myStroma.getNADPs().getQuantity(), stromaNAPDLevel, 0);
            assertEquals(myStroma.getNADPHs().getQuantity(), stromaNAPDHLevel, 0);

            myFNR.tick();
            myFerredoxin.tick();
            myStroma.tick();
            
            assertEquals(myFNR.hasComplexHasFormed(), false);
            assertEquals(myFerredoxin.hasElectron(), false);
            assertEquals(myStroma.getProtons().getQuantity(), stromaProtonLevel - 1, 0);
            assertEquals(myStroma.getNADPs().getQuantity(), stromaNAPDLevel - 1, 0);
            assertEquals(myStroma.getNADPHs().getQuantity(), stromaNAPDHLevel + 1, 0);
        }
        
        float finalStromaProtonLevel = myStroma.getProtons().getQuantity() + myStroma.getNADPHs().getQuantity();
        assertEquals(initialStromaProtonLevel, finalStromaProtonLevel, 0);
    }

}
