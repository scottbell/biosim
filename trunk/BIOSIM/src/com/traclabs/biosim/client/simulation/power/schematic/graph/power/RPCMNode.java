package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ActiveNode;
import com.traclabs.biosim.idl.simulation.framework.SimBioModule;
import com.traclabs.biosim.idl.simulation.power.RPCM;


public class RPCMNode extends ActiveNode{
    private static int nameID = 0;
    
    private RPCM myRPCM;
    public RPCMNode(RPCM pRPCM) {
    	myRPCM = pRPCM;
    }

    public FigNode makePresentation(Layer lay) {
        FigRPCMNode node = new FigRPCMNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModule getSimBioModule(){
        return myRPCM;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "RPCM";
    }
}
