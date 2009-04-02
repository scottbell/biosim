package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.TimeSensorOperations;

public class TimeSensorImpl extends GenericSensorImpl implements TimeSensorOperations{
	private BioDriver myBioDriver;
	
	public TimeSensorImpl(int pid, String name) {
		super(pid, name);
	}

	@Override
	protected void gatherData() {
		myValue = getMyTicks();
	}

	@Override
	public BioModule getInputModule() {
		return null;
	}

	@Override
	public float getMax() {
		return Integer.MAX_VALUE;
	}

}
