package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BioDriver;
import com.traclabs.biosim.server.framework.IBioModule;

public class TimeSensor extends GenericSensor {
	private BioDriver myBioDriver;
	
	public TimeSensor(int pid, String name) {
		super(pid, name);
	}

	@Override
	protected void gatherData() {
		myValue = getMyTicks();
	}

	@Override
	public IBioModule getInputModule() {
		return null;
	}

	@Override
	public float getMax() {
		return Integer.MAX_VALUE;
	}

}
