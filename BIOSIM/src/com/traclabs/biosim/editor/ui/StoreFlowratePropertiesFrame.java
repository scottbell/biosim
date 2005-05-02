/*
 * Created on Apr 28, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import org.apache.log4j.Logger;

import com.traclabs.biosim.editor.graph.FigModuleEdge;
import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;


public class StoreFlowratePropertiesFrame extends FlowratePropertiesFrame {
    private Logger myLogger;
    
    private StoreImpl myStoreImpl;

    public StoreFlowratePropertiesFrame(FigModuleEdge pEdge, SingleFlowRateControllable pOpertations, StoreImpl pStoreImpl) {
        super(pEdge, pOpertations);
    }
}