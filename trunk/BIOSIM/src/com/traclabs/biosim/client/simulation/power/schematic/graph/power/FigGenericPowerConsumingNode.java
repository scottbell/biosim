package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;

public class FigGenericPowerConsumingNode extends FigActiveNode {

    private static final Color GOLD = new Color(255,215,0);
    
    public FigGenericPowerConsumingNode() {
        super();
        setFillColor(Color.WHITE);
        setLineColor(GOLD);
    }

}
