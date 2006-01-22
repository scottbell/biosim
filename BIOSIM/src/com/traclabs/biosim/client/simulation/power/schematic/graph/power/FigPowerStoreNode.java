package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigStoreNode;
import com.traclabs.biosim.idl.simulation.power.PowerStore;

public class FigPowerStoreNode extends FigStoreNode {
    
	
    public FigPowerStoreNode() {
        super();
        setFillColor(Color.WHITE);
        setLineColor(Color.BLUE);
    }

	@Override
	public void refresh() {
		PowerStoreNode powerStoreNode = (PowerStoreNode)getOwner();
		PowerStore myStore = (PowerStore)powerStoreNode.getSimBioModule();
		setFillColor(computeColorOnPercentage(myStore.getPercentageFilled()));
	}

}
