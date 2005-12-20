/*
 * Created on Apr 28, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import com.traclabs.biosim.editor.graph.FigModuleEdge;
import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;


public class SimEnvironmentFlowratePropertiesFrame extends FlowratePropertiesFrame {
    public SimEnvironmentFlowratePropertiesFrame(FigModuleEdge pEdge, SingleFlowRateControllable pOpertations) {
        super(pEdge, pOpertations);
    }
}