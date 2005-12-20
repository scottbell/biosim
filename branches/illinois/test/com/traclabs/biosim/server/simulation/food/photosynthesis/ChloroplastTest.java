/*
 * Created on Jun 20, 2005
 *
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.OrbUtils;

/**
 * @author scott
 *
 */
public class ChloroplastTest extends TestCase {
    private final static int ITERATIONS_TO_RUN = 104;
    private Chloroplast myChloroplast;
    private Logger myLogger;

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        OrbUtils.initializeLog();
        myLogger = Logger.getLogger(ChloroplastTest.class);
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
        float initialNumberOfProtons = countProtons();
        
        for (int i = 0; i < ITERATIONS_TO_RUN; i++){
            myChloroplast.tick();
            float currentNumberOfProtons = countProtons();
            assertEquals(initialNumberOfProtons, currentNumberOfProtons, 0);
        }
        float finalNumberOfProtons = countProtons();
        
        assertEquals(initialNumberOfProtons, finalNumberOfProtons, 0);
    }
    
    private float countProtons(){
        Stroma theStroma = myChloroplast.getStroma();
        Lumen theLumen = myChloroplast.getThylakoid().getLumen();
        Plastoquinone thePlastoquinone = myChloroplast.getThylakoid().getMembrane().getPlastoquinone();
        float lumenProtons = theLumen.getProtons().getQuantity();
        float lumenWaterMolecules = theLumen.getWaterMolecules().getQuantity();
        float stromaProtons = theStroma.getProtons().getQuantity();
        float stromaNADPHs = theStroma.getNADPHs().getQuantity();
        float plastoquinoneProtons = thePlastoquinone.getNumberWithProtons();
        float totalProtons = plastoquinoneProtons + lumenProtons + stromaProtons + stromaNADPHs + (2 * lumenWaterMolecules);
        myLogger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        myLogger.debug("lumenProtons = "+lumenProtons);
        myLogger.debug("lumenWaterMolecules = "+lumenWaterMolecules);
        myLogger.debug("stromaProtons = "+stromaProtons);
        myLogger.debug("stromaNADPHs = "+stromaNADPHs);
        myLogger.debug("plastoquinoneProtons = "+plastoquinoneProtons);
        myLogger.debug("totalProtons = "+totalProtons);
        return totalProtons;
    }

}
