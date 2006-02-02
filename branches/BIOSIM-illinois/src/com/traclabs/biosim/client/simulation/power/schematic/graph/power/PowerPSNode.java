package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ActiveNode;
import com.traclabs.biosim.idl.simulation.framework.SimBioModule;
import com.traclabs.biosim.idl.simulation.power.PowerPS;


public class PowerPSNode extends ActiveNode{
    private static int nameID = 0;
    
    private PowerPS myPowerPS;
    public PowerPSNode(PowerPS pPowerPS) {
        myPowerPS = pPowerPS;
    }

    public FigNode makePresentation(Layer lay) {
        FigPowerPSNode node = new FigPowerPSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModule getSimBioModule(){
        return myPowerPS;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "PowerPS";
    }
}
