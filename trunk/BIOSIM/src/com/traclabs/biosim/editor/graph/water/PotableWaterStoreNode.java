package com.traclabs.biosim.editor.graph.water;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.environment.WaterAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.WaterAirProducerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterStoreImpl;


public class PotableWaterStoreNode extends StoreNode{
    private PotableWaterStoreImpl myPotableWaterStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {WaterProducerOperations.class, PotableWaterProducerOperations.class, WaterAirProducerOperations.class};
    private final static Class[] myConsumersAllowed = {WaterConsumerOperations.class, PotableWaterConsumerOperations.class, WaterAirConsumerOperations.class};
    
    public PotableWaterStoreNode() {
        myPotableWaterStoreImpl = new PotableWaterStoreImpl(0, "PotableWaterStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigPotableWaterStoreNode node = new FigPotableWaterStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myPotableWaterStoreImpl;
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
        return "PotableWaterStore";
    }
}
