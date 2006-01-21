package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;

public class FigGenericPowerConsumingNode extends FigActiveNode {
    
    public FigGenericPowerConsumingNode() {
        super();
        setFillColor(Color.YELLOW);
        setLineColor(Color.RED);
    }

}
