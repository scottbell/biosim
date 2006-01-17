package com.traclabs.biosim.client.simulation.power.schematic.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerOperations;
import com.traclabs.biosim.server.simulation.air.NitrogenStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class NitrogenStoreNode extends StoreNode{
    private NitrogenStoreImpl myNitrogenStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {NitrogenProducerOperations.class};
    private final static Class[] myConsumersAllowed = {NitrogenConsumerOperations.class};
    
    public NitrogenStoreNode() {
        myNitrogenStoreImpl = new NitrogenStoreImpl(0, "NitrogenStore"+nameID++);
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
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.PassiveNode#getProducersAllowed()
     */
    public Class[] getProducersAllowed() {
        return myProducersAllowed;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.PassiveNode#getConsumersAllowed()
     */
    public Class[] getConsumersAllowed() {
        return myConsumersAllowed;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "NitrogenStore";
    }
}
