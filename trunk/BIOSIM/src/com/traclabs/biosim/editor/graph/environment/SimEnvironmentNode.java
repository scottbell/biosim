package com.traclabs.biosim.editor.graph.environment;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerOperations;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class SimEnvironmentNode extends PassiveNode{
    private SimEnvironmentImpl mySimEnvironmentImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {AirProducerOperations.class, CO2AirProducerOperations.class, O2AirProducerOperations.class, NitrogenAirProducerOperations.class, WaterAirProducerOperations.class};
    private final static Class[] myConsumersAllowed = {AirConsumerOperations.class, CO2AirConsumerOperations.class, O2AirConsumerOperations.class, NitrogenAirConsumerOperations.class, WaterAirConsumerOperations.class};
    
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
}
