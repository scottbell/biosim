/*
 * Created on Apr 28, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import org.apache.log4j.Logger;

import com.traclabs.biosim.editor.graph.FigModuleEdge;
import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;


public class SimEnvironmentFlowratePropertiesFrame extends FlowratePropertiesFrame {
    private Logger myLogger;
    
    private SimEnvironmentImpl mySimEnvironmentImpl;

    public SimEnvironmentFlowratePropertiesFrame(FigModuleEdge pEdge, SingleFlowRateControllable pOpertations, SimEnvironmentImpl pSimEnvironmentImpl) {
        super(pEdge, pOpertations);
    }
}