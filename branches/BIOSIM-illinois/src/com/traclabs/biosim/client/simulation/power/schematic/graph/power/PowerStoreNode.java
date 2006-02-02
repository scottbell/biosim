package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.framework.SimBioModule;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerProducerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;


public class PowerStoreNode extends StoreNode{
    private PowerStore myPowerStore;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {PowerProducerOperations.class};
    private final static Class[] myConsumersAllowed = {PowerConsumerOperations.class};
    
    public PowerStoreNode(PowerStore pPowerStore) {
    	myPowerStore = pPowerStore;
    }

    public FigNode makePresentation(Layer lay) {
        FigPowerStoreNode node = new FigPowerStoreNode();
        node.setOwner(this);
        return node;
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
        return "PowerStore";
    }

	@Override
	public SimBioModule getSimBioModule() {
		return myPowerStore;
	}
}
