package com.traclabs.biosim.editor.graph.food;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerOperations;
import com.traclabs.biosim.server.simulation.food.BiomassStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class BiomassStoreNode extends StoreNode{
    private BiomassStoreImpl myBiomassStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {BiomassProducerOperations.class};
    private final static Class[] myConsumersAllowed = {BiomassConsumerOperations.class};
    
    public BiomassStoreNode() {
        myBiomassStoreImpl = new BiomassStoreImpl(0, "BiomassStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigBiomassStoreNode node = new FigBiomassStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myBiomassStoreImpl;
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
