package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerOperations;
import com.traclabs.biosim.server.simulation.air.MethaneStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class MethaneStoreNode extends StoreNode{
    private MethaneStoreImpl myMethaneStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {MethaneProducerOperations.class};
    private final static Class[] myConsumersAllowed = {MethaneConsumerOperations.class};
    
    public MethaneStoreNode() {
        myMethaneStoreImpl = new MethaneStoreImpl(0, "MethaneStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigMethaneStoreNode node = new FigMethaneStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myMethaneStoreImpl;
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
