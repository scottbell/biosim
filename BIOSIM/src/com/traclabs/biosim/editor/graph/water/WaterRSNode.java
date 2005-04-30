package com.traclabs.biosim.editor.graph.water;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
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
}
