package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;
import com.traclabs.biosim.idl.simulation.power.PowerPS;

public class FigPowerPSNode extends FigActiveNode {
    
    public FigPowerPSNode() {
        super();
        setFillColor(Color.WHITE);
        setLineColor(Color.RED);
    }

	@Override
	public void refresh() {
		PowerPSNode powerPSNode = (PowerPSNode)getOwner();
		PowerPS myPowerPS = (PowerPS)powerPSNode.getSimBioModule();
		setFillColor(computeColorOnPercentage(myPowerPS.getPowerProducerDefinition().getAveragePercentageFull()));
	}

}
