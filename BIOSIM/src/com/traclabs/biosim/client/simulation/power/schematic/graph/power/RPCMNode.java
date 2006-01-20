package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.RPCMImpl;


public class RPCMNode extends ActiveNode{
    private static int nameID = 0;
    
    private RPCMImpl myRPCMImpl;
    public RPCMNode() {
    }

    public FigNode makePresentation(Layer lay) {
        FigRPCMNode node = new FigRPCMNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myRPCMImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "RPCM";
    }
}
