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
    private final static int ITERATIONS_TO_RUN = 1;

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
    	/*
        myStroma.getProtons().setQuantity(ITERATIONS_TO_RUN * 3);
        myLumen.getProtons().setQuantity(0);
        
        float initialLumenProtons = myLumen.getProtons().getQuantity();
        float initialStromaProtons = myStroma.getProtons().getQuantity();
        float initialProtonsToAdd = ITERATIONS_TO_RUN * 2;
        float initialProtons = initialLumenProtons + initialStromaProtons + initialProtonsToAdd;
        
        for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
            
        }
        
        float finalLumenProtons = myLumen.getProtons().getQuantity();
        float finalStromaProtons = myStroma.getProtons().getQuantity();
        float finalProtons = finalLumenProtons + finalStromaProtons;
        
        assertEquals(initialProtons, finalProtons, 0);\
        */
       
        
    }

}
