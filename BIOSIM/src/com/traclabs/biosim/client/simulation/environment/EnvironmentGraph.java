package com.traclabs.biosim.client.simulation.environment;

import java.util.List;

import ptolemy.plot.PlotLive;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

public class EnvironmentGraph extends PlotLive {
	private List<SimEnvironment> myEnvironments;
	private BioDriver myBioDriver;
	private final static int TICKS_TO_KEEP = 30000;

	public EnvironmentGraph() {
		setPointsPersistence(TICKS_TO_KEEP);
		setYLabel("Pressure");
		setXLabel("Ticks");
		myEnvironments = BioHolderInitializer.getBioHolder().theSimEnvironments;
		myBioDriver = BioHolderInitializer.getBioHolder().theBioDriver;
		for (int i = 0; i < myEnvironments.size(); i++)
			addLegend(i, myEnvironments.get(i).getModuleName());
		addLegend(myEnvironments.size(), "Total Pressure");
		setButtons(true);
		setXRange(myBioDriver.getTicks(), TICKS_TO_KEEP);
	}

	@Override
	public synchronized void addPoints() {
		if (myBioDriver.getTicks() <= 0)
			return;
		float totalPressure = 0f;
		for (int i = 0; i < myEnvironments.size(); i++){
			float pressure =  myEnvironments.get(i).getTotalPressure();
			totalPressure += pressure;
			addPoint(i, myBioDriver.getTicks(), myEnvironments.get(i).getTotalPressure(), true);
		}
		addPoint(myEnvironments.size(), myBioDriver.getTicks(), totalPressure, true);
		if (getXRange()[1] >= myBioDriver.getTicks() + TICKS_TO_KEEP){
			setXRange(myBioDriver.getTicks(), myBioDriver.getTicks() + TICKS_TO_KEEP);
			clear(true);
			repaint();
		}
		else if (getXRange()[1] <= myBioDriver.getTicks()){
			setXRange(myBioDriver.getTicks(), myBioDriver.getTicks() + TICKS_TO_KEEP);
			repaint();
		}
		try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
	}

}
