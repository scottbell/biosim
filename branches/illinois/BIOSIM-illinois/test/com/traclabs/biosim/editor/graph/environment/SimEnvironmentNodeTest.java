/*
 * Created on May 17, 2005
 *
 */
package com.traclabs.biosim.editor.graph.environment;

import junit.framework.TestCase;

import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;

/**
 * @author scott
 *
 */
public class SimEnvironmentNodeTest extends TestCase {
    private SimEnvironmentNode mySimEnvironmentNode;
    
    protected void setUp() throws Exception {
        super.setUp();
        mySimEnvironmentNode = new SimEnvironmentNode();
    } 

    public void testGetSimBioModuleImpl() {
         assertTrue(mySimEnvironmentNode.getSimBioModuleImpl() instanceof SimEnvironmentImpl);
    }
    
    protected void tearDown() throws Exception { 
        super.tearDown();
        mySimEnvironmentNode = null; 
   }

}