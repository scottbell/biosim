package com.traclabs.biosim.client.simulation.power.schematic.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ProducerOperations;
import com.traclabs.biosim.server.simulation.air.O2StoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class O2StoreNode extends StoreNode{
    private O2StoreImpl myO2StoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {O2ProducerOperations.class};
    private final static Class[] myConsumersAllowed = {O2ConsumerOperations.class};
    
    public O2StoreNode() {
        myO2StoreImpl = new O2StoreImpl(0, "O2Store"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigO2StoreNode node = new FigO2StoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myO2StoreImpl;
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
        return "O2Store";
    }
}
