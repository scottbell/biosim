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
public class ATPSynthaseTest extends TestCase {
    private final static int ITERATIONS_TO_RUN = 103;
    
    private ATPSynthase myATPSynthase;

    private Stroma myStroma;

    private Lumen myLumen;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        OrbUtils.initializeLog();
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
    	/*
        myLumen.getProtons().setQuantity(0);
        myStroma.getADPs().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getPhosphates().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getATPs().setQuantity(0);
        myStroma.getProtons().setQuantity(0);
        float initialStromaProtonLevel = myStroma.getProtons().getQuantity();
        float initialLumenProtonLevel = myLumen.getProtons().getQuantity();
        float initialStromaPhosphateLevel = myStroma.getPhosphates().getQuantity();
        float initialStromaADPLevel = myStroma.getADPs().getQuantity();
        float initialProtonsToAdd = ITERATIONS_TO_RUN;
        float initialProtons = initialStromaProtonLevel + initialLumenProtonLevel + initialProtonsToAdd;
        
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
            myATPSynthase.tick();
            myStroma.tick();
            myLumen.tick();
            float lumenProtonLevel = myLumen.getProtons().getQuantity();
            float stromaProtonLevel = myStroma.getProtons().getQuantity();
            assertEquals(i, stromaProtonLevel + lumenProtonLevel, 0);
            myLumen.getProtons().setQuantity(myLumen.getProtons().getQuantity() + 1);
        }
        float finalStromaPhosphateLevel = myStroma.getPhosphates().getQuantity();
        float finalStromaADPLevel = myStroma.getADPs().getQuantity();
        float finalStromaATPLevel = myStroma.getATPs().getQuantity();
        float finalLumenProtonLevel = myLumen.getProtons().getQuantity();
        float finalStromaProtonLevel = myStroma.getProtons().getQuantity();
        float finalProtonLevel = finalLumenProtonLevel + finalStromaProtonLevel;
        
        assertEquals(finalStromaADPLevel, initialStromaADPLevel - finalStromaATPLevel, 0);
        assertEquals(finalStromaPhosphateLevel, initialStromaPhosphateLevel - finalStromaATPLevel, 0);
        assertEquals(ITERATIONS_TO_RUN, finalStromaProtonLevel + finalLumenProtonLevel, 0);
        
        assertEquals(ITERATIONS_TO_RUN / finalStromaATPLevel, 20, 3);
        assertEquals(initialProtons, finalProtonLevel, 0);
        
        //make sure when there aren't any protons in lumen, no ATP is generated
        myLumen.getProtons().setQuantity(0);
        myStroma.getADPs().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getPhosphates().setQuantity(ITERATIONS_TO_RUN * 2);
        myStroma.getATPs().setQuantity(0);
        myStroma.getProtons().setQuantity(0);
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
            myATPSynthase.tick();
            myStroma.tick();
            myLumen.tick();
            float atpLevel = myStroma.getATPs().getQuantity();
            assertEquals(0, atpLevel, 0);
        }
        */
    }

}
