package com.traclabs.biosim.client.simulation.power.schematic.graph.power;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigActiveNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleEdge;
import com.traclabs.biosim.client.simulation.power.schematic.ui.RPCMPropertiesFrame;
import com.traclabs.biosim.idl.simulation.power.RPCM;

public class FigRPCMNode extends FigActiveNode {
	private JFrame myEditFrame;
	
    public FigRPCMNode() {
        super();
        setFillColor(Color.LIGHT_GRAY);
        setLineColor(Color.BLACK);
    }
    
    protected JFrame getPropertiesEditor() {
        if (myEditFrame != null)
            return myEditFrame;
        myEditFrame = new RPCMPropertiesFrame(this);
        myEditFrame.pack();
        return myEditFrame;
    }

	@Override
	public void refresh() {
		RPCMNode rpcmNode = (RPCMNode)getOwner();
		RPCM myRPCM = (RPCM)rpcmNode.getSimBioModule();
		if (myRPCM.isOvertripped() || myRPCM.isUndertripped()){
			setFillColor(computeColorOnPercentage(0));
			myLogger.debug("percentage is 0");
		}
		else{
			float percentage = myRPCM.getPowerConsumerDefinition().getAveragePercentageFull();
			myLogger.debug("percentage is "+percentage);
			setFillColor(computeColorOnPercentage(percentage));
		}
		boolean[] switcheStatuses = myRPCM.getSwitchStatuses();
		List edges = getOutBoundEdges();
		for (int i = 0; i < switcheStatuses.length; i++){
			FigModuleEdge currentEdge = (FigModuleEdge)edges.get(i);
			if (switcheStatuses[i]){
				currentEdge.setFillColor(Color.BLACK);
				currentEdge.setLineColor(Color.BLACK);
				currentEdge.getDestArrowHead().setFillColor(Color.BLACK);
				currentEdge.setDashed(false);
			}
			else{
				currentEdge.setFillColor(Color.LIGHT_GRAY);
				currentEdge.setLineColor(Color.LIGHT_GRAY);
				currentEdge.setDashed(true);
				currentEdge.getDestArrowHead().setFillColor(Color.LIGHT_GRAY);
			}
		}
	}

}
