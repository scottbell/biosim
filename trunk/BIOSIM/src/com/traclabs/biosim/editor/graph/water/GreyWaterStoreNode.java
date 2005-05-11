package com.traclabs.biosim.editor.graph.water;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterStoreImpl;


public class GreyWaterStoreNode extends PassiveNode{
    private GreyWaterStoreImpl myGreyWaterStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {WaterProducerOperations.class, GreyWaterProducerOperations.class};
    private final static Class[] myConsumersAllowed = {WaterConsumerOperations.class, GreyWaterConsumerOperations.class};
    
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
