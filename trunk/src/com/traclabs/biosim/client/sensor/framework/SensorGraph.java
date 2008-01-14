package com.traclabs.biosim.client.sensor.framework;

import java.util.List;

import ptolemy.plot.PlotLive;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;

public class SensorGraph extends PlotLive {
	private List<GenericSensor> mySensors;
	private BioDriver myBioDriver;
	private final static int TICKS_TO_KEEP = 100;
	private boolean started = false;

	public SensorGraph() {
		setPointsPersistence(TICKS_TO_KEEP);
		setYLabel("Value");
		setXLabel("Ticks");
		mySensors = BioHolderInitializer.getBioHolder().theSensors;
		myBioDriver = BioHolderInitializer.getBioHolder().theBioDriver;
		for (int i = 0; i < mySensors.size(); i++)
			addLegend(i, mySensors.get(i).getModuleName());
		addLegend(mySensors.size(), "Total Pressure");
		setButtons(true);
		setXRange(myBioDriver.getTicks(), TICKS_TO_KEEP);
		float minTotal = Float.MAX_VALUE;
		float maxTotal = Float.MIN_VALUE;
		for (int i = 0; i < mySensors.size(); i++){
			float thisMin = mySensors.get(i).getMax();
			if (thisMin > minTotal)
				minTotal = thisMin;
			float thisMax =  mySensors.get(i).getMax();
			if (thisMax > maxTotal)
				maxTotal = thisMax;
		}
		setYRange(minTotal, maxTotal);
	}

	@Override
	public synchronized void addPoints() {
		if (myBioDriver.getTicks() <= 0)
			return;
		for (int i = 0; i < mySensors.size(); i++){
			addPoint(i, myBioDriver.getTicks(), mySensors.get(i).getValue(), true);
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
		}
			
		//sleep
		try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
        }
	}

}
