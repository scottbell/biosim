package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;
import com.traclabs.biosim.idl.simulation.power.RPCM;

public class FigRPCMNode extends FigActiveNode {
    public FigRPCMNode() {
        super();
        setFillColor(Color.WHITE);
        setLineColor(Color.BLACK);
    }

	@Override
	public void refresh() {
		RPCMNode rpcmNode = (RPCMNode)getOwner();
		RPCM myRPCM = (RPCM)rpcmNode.getSimBioModule();
		if (myRPCM.isOverTripped() || myRPCM.isUnderTripped())
			setFillColor(computeColorOnPercentage(0));
		else{
			setFillColor(computeColorOnPercentage(myRPCM.getPowerConsumerDefinition().getAveragePercentageFull()));
		}
	}

}
