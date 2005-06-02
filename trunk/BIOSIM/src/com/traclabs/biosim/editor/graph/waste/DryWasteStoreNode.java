package com.traclabs.biosim.editor.graph.waste;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteStoreImpl;


public class DryWasteStoreNode extends StoreNode{
    private DryWasteStoreImpl myDryWasteStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {DryWasteProducerOperations.class};
    private final static Class[] myConsumersAllowed = {DryWasteConsumerOperations.class};
    
    public DryWasteStoreNode() {
        myDryWasteStoreImpl = new DryWasteStoreImpl(0, "DryWasteStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigDryWasteStoreNode node = new FigDryWasteStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myDryWasteStoreImpl;
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

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "DryWasteStore";
    }
}
