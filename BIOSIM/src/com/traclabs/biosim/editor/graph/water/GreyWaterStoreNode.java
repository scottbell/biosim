package com.traclabs.biosim.editor.graph.water;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterStoreImpl;


public class GreyWaterStoreNode extends PassiveNode{
    private GreyWaterStoreImpl myGreyWaterStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {GreyWaterProducerOperations.class};
    private final static Class[] myConsumersAllowed = {GreyWaterConsumerOperations.class};
    
    public GreyWaterStoreNode() {
        myGreyWaterStoreImpl = new GreyWaterStoreImpl(0, "GreyWaterStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigGreyWaterStoreNode node = new FigGreyWaterStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myGreyWaterStoreImpl;
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
