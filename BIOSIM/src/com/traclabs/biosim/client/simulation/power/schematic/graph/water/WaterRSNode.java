package com.traclabs.biosim.client.simulation.power.schematic.graph.water;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.water.WaterRSImpl;


public class WaterRSNode extends ActiveNode{
    private static int nameID = 0;
    
    private WaterRSImpl myWaterRSImpl;
    public WaterRSNode() {
        myWaterRSImpl = new WaterRSImpl(0, "WaterRS" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigWaterRSNode node = new FigWaterRSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myWaterRSImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "WaterRS";
    }
}
