package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;
import com.traclabs.biosim.idl.simulation.power.PowerPS;

public class FigPowerPSNode extends FigActiveNode {
    
    public FigPowerPSNode() {
        super();
        setFillColor(Color.LIGHT_GRAY);
        setLineColor(Color.RED);
    }

	@Override
	public void refresh() {
		PowerPSNode powerPSNode = (PowerPSNode)getOwner();
		PowerPS myPowerPS = (PowerPS)powerPSNode.getSimBioModule();
		float percentage = myPowerPS.getPowerProducerDefinition().getAveragePercentageFull();
		myLogger.debug("percentage is " + percentage);
		setFillColor(computeColorOnPercentage(percentage));
	}

}
