package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;
import com.traclabs.biosim.idl.simulation.power.GenericPowerConsumer;

public class FigGenericPowerConsumerNode extends FigActiveNode {
    
    public FigGenericPowerConsumerNode() {
        super();
        setLineColor(Color.WHITE);
        setFillColor(Color.LIGHT_GRAY);
    }

	@Override
	public void refresh() {
		GenericPowerConsumerNode consumingNode = (GenericPowerConsumerNode)getOwner();
		GenericPowerConsumer myConsumer = (GenericPowerConsumer)consumingNode.getSimBioModule();
		float percentage = myConsumer.getPowerConsumerDefinition().getAveragePercentageFull();
		myLogger.debug("percentage is " + percentage);
		setFillColor(computeColorOnPercentage(percentage));
	}

}
