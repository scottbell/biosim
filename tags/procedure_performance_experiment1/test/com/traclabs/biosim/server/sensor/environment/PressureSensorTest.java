package com.traclabs.biosim.server.sensor.environment;

import junit.framework.TestCase;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensorPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.util.OrbUtils;

public class PressureSensorTest extends TestCase {
	private GasPressureSensor myO2PressureSensor;
	private SimEnvironment mySimEnvironment;
	
	private BioModule[] myModules;
	
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println("Here");
		OrbUtils.initializeLog(true);
		OrbUtils.startStandaloneNameServer();
		OrbUtils.initializeServerForStandalone();
		
		mySimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myO2PressureSensor = (new GasPressureSensorPOATie(new GasPressureSensorImpl()))._this(OrbUtils.getORB());
		myO2PressureSensor.setInput(mySimEnvironment.getO2Store());
		
		myModules = new BioModule[2];
		myModules[0] = mySimEnvironment;
		myModules[1] = myO2PressureSensor;
		
		for (BioModule currentModule : myModules)
			currentModule.setTickLength(1);
	}
	
	public void testAirConsumption(){
		tick();
		assertEquals(myO2PressureSensor.getValue(), mySimEnvironment.getO2Store().getPressure());
		tick();
		assertEquals(myO2PressureSensor.getValue(), mySimEnvironment.getO2Store().getPressure());
		mySimEnvironment.getO2Store().take(10);
		tick();
		assertEquals(myO2PressureSensor.getValue(), mySimEnvironment.getO2Store().getPressure());
		assertEquals(myO2PressureSensor.getValue(), mySimEnvironment.getO2Store().getPressure());
	}
	
	private void tick(){
		for (BioModule currentModule : myModules)
			currentModule.tick();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		for (BioModule currentModule : myModules)
			currentModule = null;
		myModules = null;
	}
	
	private void reset(){
		for (BioModule currentModule : myModules)
			currentModule.reset();
	}

}
