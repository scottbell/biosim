package com.traclabs.biosim.editor.graph.environment;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class SimEnvironmentNode extends PassiveNode{
    private SimEnvironmentImpl mySimEnvironmentImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {AirProducerOperations.class, O2ProducerOperations.class, CO2ProducerOperations.class, NitrogenProducerOperations.class};
    private final static Class[] myConsumersAllowed = {AirConsumerOperations.class, O2ConsumerOperations.class, CO2ConsumerOperations.class, NitrogenConsumerOperations.class};
    
    public SimEnvironmentNode() {
        mySimEnvironmentImpl = new SimEnvironmentImpl(0, "SimEnvironment"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigSimEnvironmentNode node = new FigSimEnvironmentNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return mySimEnvironmentImpl;
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
    
    public void addSensor() {
    	
    }

    /**
     * 
     */
    public void removeSensor() {
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "SimEnvironment";
    }
}
