package com.traclabs.biosim.editor.graph.water;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreImpl;


public class DirtyWaterStoreNode extends PassiveNode{
    private DirtyWaterStoreImpl myDirtyWaterStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {DirtyWaterProducerOperations.class};
    private final static Class[] myConsumersAllowed = {DirtyWaterConsumerOperations.class};
    
    public DirtyWaterStoreNode() {
        myDirtyWaterStoreImpl = new DirtyWaterStoreImpl(0, "DirtyWaterStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigDirtyWaterStoreNode node = new FigDirtyWaterStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myDirtyWaterStoreImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.PassiveNode#getProducersAllowed()
     */
    public Class[] getProducersAllowed() {
        return myProducersAllowed;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.PassiveNode#getConsumersAllowed()
     */
    public Class[] getConsumersAllowed() {
        return myConsumersAllowed;
    }
}
