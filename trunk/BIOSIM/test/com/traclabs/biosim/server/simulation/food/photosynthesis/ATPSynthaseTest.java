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
public class ATPSynthaseTest extends TestCase {
    private final static int ITERATIONS_TO_RUN = 8203;
    
    private ATPSynthase myATPSynthase;

    private Stroma myStroma;

    private Lumen myLumen;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        myStroma = new Stroma();
        myLumen = new Lumen();
        myATPSynthase = new ATPSynthase(myLumen, myStroma);
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        myStroma = null;
        myLumen = null;
        myATPSynthase = null;
    }

    public void testTick() {
        myLumen.getProtons().setQuantity(0);
        myStroma.getADPs().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getPhosphates().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getATPs().setQuantity(0);
        myStroma.getProtons().setQuantity(0);
        float initialStromaPhosphateLevel = myStroma.getPhosphates().getQuantity();
        float initialStromaADPLevel = myStroma.getADPs().getQuantity();
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
            myATPSynthase.tick();
            myStroma.tick();
            myLumen.tick();
            float lumenProtonLevel = myLumen.getProtons().getQuantity();
            float stromaProtonLevel = myStroma.getProtons().getQuantity();
            assertEquals(i, stromaProtonLevel + lumenProtonLevel, 0);
            myLumen.getProtons().setQuantity(myLumen.getProtons().getQuantity() + 1);
        }
        float stromaPhosphateLevel = myStroma.getPhosphates().getQuantity();
        float stromaADPLevel = myStroma.getADPs().getQuantity();
        float stromaATPLevel = myStroma.getATPs().getQuantity();
        float lumenProtonLevel = myLumen.getProtons().getQuantity();
        float stromaProtonLevel = myStroma.getProtons().getQuantity();
        
        assertEquals(stromaADPLevel, initialStromaADPLevel - stromaATPLevel, 0);
        assertEquals(stromaPhosphateLevel, initialStromaPhosphateLevel - stromaATPLevel, 0);
        assertEquals(ITERATIONS_TO_RUN, stromaProtonLevel + lumenProtonLevel, 0);
        
        assertEquals(ITERATIONS_TO_RUN / stromaATPLevel, 20, 3);
    }

}
