/*
 * Created on May 17, 2005
 *
 */
package com.traclabs.biosim.editor.graph.environment;

import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;

import junit.framework.TestCase;

/**
 * @author scott
 *
 */
public class SimEnvironmentNodeTest extends TestCase {
    private SimEnvironmentNode mySimEnvironmentNode;
    
    protected void setUp() { 
        mySimEnvironmentNode = new SimEnvironmentNode();
    } 

    public void testGetSimBioModuleImpl() {
         assertTrue(mySimEnvironmentNode.getSimBioModuleImpl() instanceof SimEnvironmentImpl);
    }
    
    protected void tearDown() { 
        mySimEnvironmentNode = null; 
   }

}
