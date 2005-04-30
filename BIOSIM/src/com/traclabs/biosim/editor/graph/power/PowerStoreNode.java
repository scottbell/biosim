package com.traclabs.biosim.editor.graph.power;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerOperations;
import com.traclabs.biosim.server.simulation.power.PowerStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class PowerStoreNode extends PassiveNode{
    private PowerStoreImpl myPowerStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {PowerProducerOperations.class};
    private final static Class[] myConsumersAllowed = {PowerConsumerOperations.class};
    
    public PowerStoreNode() {
        myPowerStoreImpl = new PowerStoreImpl(0, "PowerStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigPowerStoreNode node = new FigPowerStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myPowerStoreImpl;
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
