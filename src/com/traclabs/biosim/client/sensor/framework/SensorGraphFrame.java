package com.traclabs.biosim.client.sensor.framework;

import java.awt.BorderLayout;

import com.traclabs.biosim.client.simulation.framework.SimulationPanel;

public class SensorGraphFrame extends SimulationPanel {
	SensorGraphPanel myGraph = new SensorGraphPanel();
	
	public SensorGraphFrame(){
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
