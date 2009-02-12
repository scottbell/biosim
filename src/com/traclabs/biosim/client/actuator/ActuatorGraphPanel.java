package com.traclabs.biosim.client.actuator;

import java.util.List;

import ptolemy.plot.Plot;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;

public class ActuatorGraphPanel extends Plot {
	private List<GenericActuator> myActuators;
	private BioDriver myBioDriver;
	private final static int TICKS_TO_KEEP = 1000;
	private boolean started = false;

	public ActuatorGraphPanel() {
		setPointsPersistence(TICKS_TO_KEEP);
		setYLabel("Value");
		setXLabel("Ticks");
		myActuators = BioHolderInitializer.getBioHolder().theActuators;
		myBioDriver = BioHolderInitializer.getBioHolder().theBioDriver;
		for (int i = 0; i < myActuators.size(); i++)
			addLegend(i, myActuators.get(i).getModuleName());
		setButtons(true);
	}
	
	public void addPoints() {
		if (myBioDriver.getTicks() <= 0)
			return;
		for (int i = 0; i < myActuators.size(); i++){
			addPoint(i, myBioDriver.getTicks(), myActuators.get(i).getValue(), true);
		}
		if (!started){
			started = true;
			repaint();
		}
		//move x axis
		if (getXRange()[0] >= myBioDriver.getTicks()){
			double[] yRange = getYRange();
			clear(false);
			setXRange(myBioDriver.getTicks(), myBioDriver.getTicks() + TICKS_TO_KEEP);
			setYRange(yRange[0], yRange[1]);
			_yRangeGiven = false;
			repaint();
		}
		else if (getXRange()[1] <= myBioDriver.getTicks()){
			setXRange(myBioDriver.getTicks(), myBioDriver.getTicks() + TICKS_TO_KEEP);
			repaint();
		}repaint();
	}

}
