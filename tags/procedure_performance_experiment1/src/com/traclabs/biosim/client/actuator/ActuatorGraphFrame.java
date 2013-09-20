package com.traclabs.biosim.client.actuator;

import java.awt.BorderLayout;

import com.traclabs.biosim.client.simulation.framework.SimulationPanel;

public class ActuatorGraphFrame extends SimulationPanel {
	ActuatorGraphPanel myGraph = new ActuatorGraphPanel();
	
	public ActuatorGraphFrame(){
		myGraph.setButtons(true);
		add(myGraph, BorderLayout.CENTER);
	}

	@Override
	protected void refresh() {
		myGraph.addPoints();
	}

	@Override
	protected void reset() {
		myGraph.clear(false);
		myGraph.repaint();
	}

}
