package com.traclabs.biosim.editor.graph.power;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.NuclearPowerPS;
import com.traclabs.biosim.server.simulation.power.PowerPSImpl;


public class NuclearPowerPSNode extends ActiveNode{
    private static int nameID = 0;
    
    private PowerPSImpl myPowerPSImpl;
    public NuclearPowerPSNode() {
        myPowerPSImpl = new NuclearPowerPS(0, "NuclearPowerPS" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigNuclearPowerPSNode node = new FigNuclearPowerPSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myPowerPSImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "PowerPS";
    }
}
