package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerOperations;
import com.traclabs.biosim.server.simulation.air.NitrogenStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class NitrogenStoreNode extends PassiveNode{
    private NitrogenStoreImpl myNitrogenStoreImpl;
    
    private final static Class[] myProducersAllowed = {NitrogenProducerOperations.class};
    private final static Class[] myConsumersAllowed = {NitrogenConsumerOperations.class};
    
    public NitrogenStoreNode() {
        myNitrogenStoreImpl = new NitrogenStoreImpl(0, "Unamed");
        setText("NitrogenStore");
    }

    public FigNode makePresentation(Layer lay) {
        FigNitrogenStoreNode node = new FigNitrogenStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myNitrogenStoreImpl;
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