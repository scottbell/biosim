package com.traclabs.biosim.client.simulation.power.schematic.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.H2ProducerOperations;
import com.traclabs.biosim.server.simulation.air.H2StoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class H2StoreNode extends StoreNode{
    private H2StoreImpl myH2StoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {H2ProducerOperations.class};
    private final static Class[] myConsumersAllowed = {H2ConsumerOperations.class};
    
    public H2StoreNode() {
        myH2StoreImpl = new H2StoreImpl(0, "H2Store"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigH2StoreNode node = new FigH2StoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myH2StoreImpl;
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
        return "H2Store";
    }
}
