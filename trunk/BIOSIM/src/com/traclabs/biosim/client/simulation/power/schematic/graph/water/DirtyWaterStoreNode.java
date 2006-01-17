package com.traclabs.biosim.client.simulation.power.schematic.graph.water;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreImpl;


public class DirtyWaterStoreNode extends StoreNode{
    private DirtyWaterStoreImpl myDirtyWaterStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {WaterProducerOperations.class, DirtyWaterProducerOperations.class};
    private final static Class[] myConsumersAllowed = {WaterConsumerOperations.class, DirtyWaterConsumerOperations.class};
    
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
        return "DirtyWaterStore";
    }
}
