package com.traclabs.biosim.client.simulation.power.schematic.graph.framework;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.framework.AccumulatorImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class AccumulatorNode extends ActiveNode{
    private static int nameID = 0;
    
    private AccumulatorImpl myAccumulatorImpl;
    public AccumulatorNode() {
    	myAccumulatorImpl = new AccumulatorImpl(0, "Accumulator" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigAccumulatorNode node = new FigAccumulatorNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myAccumulatorImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "Accumulator";
    }
}
