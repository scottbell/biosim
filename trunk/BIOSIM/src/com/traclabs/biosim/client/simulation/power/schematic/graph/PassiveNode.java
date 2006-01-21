package com.traclabs.biosim.client.simulation.power.schematic.graph;


public abstract class PassiveNode extends ModuleNode {
    
    /**
     * @return
     */
    public abstract Class[] getProducersAllowed();

    /**
     * @return
     */
    public abstract Class[] getConsumersAllowed();

}
