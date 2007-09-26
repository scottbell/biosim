package com.traclabs.biosim.client.simulation.environment;

import java.util.List;

import ptolemy.plot.PlotLive;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

public class EnvironmentGraph extends PlotLive {
	private List<SimEnvironment> myEnvironments;
	private BioDriver myBioDriver;

	public EnvironmentGraph() {
		setPointsPersistence(2000);
		myEnvironments = BioHolderInitializer.getBioHolder().theSimEnvironments;
		myBioDriver = BioHolderInitializer.getBioHolder().theBioDriver;
		for (int i = 0; i < myEnvironments.size(); i++)
			addLegend(i, myEnvironments.get(i).getModuleName()
					+ " pressue (kPA)");
		setButtons(true);
	}

	@Override
	public synchronized void addPoints() {
		for (int i = 0; i < myEnvironments.size(); i++)
			addPoint(i, myBioDriver.getTicks(), myEnvironments.get(i).getTotalPressure(), true);
		repaint();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

}
