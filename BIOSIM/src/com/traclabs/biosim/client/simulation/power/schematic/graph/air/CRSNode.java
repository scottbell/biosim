package com.traclabs.biosim.client.simulation.power.schematic.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.air.CRSImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class CRSNode extends ActiveNode{
    private static int nameID = 0;
    
    private CRSImpl myCRSImpl;
    public CRSNode() {
        myCRSImpl = new CRSImpl(0, "CRS" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigCRSNode node = new FigCRSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myCRSImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "CRS";
    }
}
