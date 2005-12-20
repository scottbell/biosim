/*
 * Created on Apr 28, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import com.traclabs.biosim.editor.graph.FigModuleEdge;
import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;


public class StoreFlowratePropertiesFrame extends FlowratePropertiesFrame {
    public StoreFlowratePropertiesFrame(FigModuleEdge pEdge, SingleFlowRateControllable pOpertations) {
        super(pEdge, pOpertations);
    }
}