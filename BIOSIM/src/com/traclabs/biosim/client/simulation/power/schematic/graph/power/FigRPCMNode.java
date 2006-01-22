package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;
import com.traclabs.biosim.idl.simulation.power.RPCM;

public class FigRPCMNode extends FigActiveNode {
    public FigRPCMNode() {
        super();
        setFillColor(Color.LIGHT_GRAY);
        setLineColor(Color.BLACK);
    }

	@Override
	public void refresh() {
		RPCMNode rpcmNode = (RPCMNode)getOwner();
		RPCM myRPCM = (RPCM)rpcmNode.getSimBioModule();
		if (myRPCM.isOverTripped() || myRPCM.isUnderTripped()){
			setFillColor(computeColorOnPercentage(0));
			myLogger.debug("percentage is 0");
		}
		else{
			float percentage = myRPCM.getPowerConsumerDefinition().getAveragePercentageFull();
			myLogger.debug("percentage is "+percentage);
			setFillColor(computeColorOnPercentage(percentage));
		}
	}

}
