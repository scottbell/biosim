package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerPSImpl;
import com.traclabs.biosim.server.simulation.power.SolarPowerPS;


public class SolarPowerPSNode extends ActiveNode{
    private static int nameID = 0;
    
    private PowerPSImpl myPowerPSImpl;
    public SolarPowerPSNode() {
        myPowerPSImpl = new SolarPowerPS(0, "SolarPowerPS" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigSolarPowerPSNode node = new FigSolarPowerPSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myPowerPSImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "PowerPS";
    }
}
