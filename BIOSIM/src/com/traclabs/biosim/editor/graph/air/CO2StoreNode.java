package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.CO2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.CO2AirProducerOperations;
import com.traclabs.biosim.server.simulation.air.CO2StoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class CO2StoreNode extends StoreNode{
    private CO2StoreImpl myCO2StoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {CO2ProducerOperations.class, CO2AirProducerOperations.class};
    private final static Class[] myConsumersAllowed = {CO2ConsumerOperations.class, CO2AirConsumerOperations.class};
    
    public CO2StoreNode() {
        myCO2StoreImpl = new CO2StoreImpl(0, "CO2Store"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigCO2StoreNode node = new FigCO2StoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myCO2StoreImpl;
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
        return "COStore";
    }
}
