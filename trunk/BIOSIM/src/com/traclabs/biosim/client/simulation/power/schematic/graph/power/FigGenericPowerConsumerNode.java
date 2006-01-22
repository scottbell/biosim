package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;
import com.traclabs.biosim.idl.simulation.power.GenericPowerConsumer;

public class FigGenericPowerConsumerNode extends FigActiveNode {

    private static final Color GOLD = new Color(255,215,0);
    
    public FigGenericPowerConsumerNode() {
        super();
        setFillColor(Color.WHITE);
        setLineColor(GOLD);
    }

	@Override
	public void refresh() {
		GenericPowerConsumerNode consumingNode = (GenericPowerConsumerNode)getOwner();
		GenericPowerConsumer myConsumer = (GenericPowerConsumer)consumingNode.getSimBioModule();
		setFillColor(computeColorOnPercentage(myConsumer.getPowerConsumerDefinition().getAveragePercentageFull()));
	}

}
