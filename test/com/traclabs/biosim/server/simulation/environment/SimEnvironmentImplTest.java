package com.traclabs.biosim.server.simulation.environment;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.OrbUtils;

public class SimEnvironmentImplTest extends TestCase {
	private SimEnvironmentImpl mySimEnvironmentImpl;
	private Logger myLogger;

	protected void setUp() throws Exception {
		super.setUp();
		OrbUtils.initializeLog(false);
		OrbUtils.initializeServerForStandalone();
		myLogger = Logger.getLogger(this.getClass());
		mySimEnvironmentImpl = new SimEnvironmentImpl();
		mySimEnvironmentImpl.setInitialVolumeAtSeaLevel(10);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		mySimEnvironmentImpl = null;
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl.tick()'
	 */
	public void testTick() {
		float o2Moles = mySimEnvironmentImpl.getO2Store().getCurrentLevel();
		myLogger.debug("o2Moles = "+o2Moles);
		mySimEnvironmentImpl.tick();
		assertEquals(o2Moles, mySimEnvironmentImpl.getO2Store().getCurrentLevel());
		mySimEnvironmentImpl.getO2Store().take(o2Moles * 0.1f);
		mySimEnvironmentImpl.tick();
		assertEquals(o2Moles - (o2Moles * 0.1f), mySimEnvironmentImpl.getO2Store().getCurrentLevel());
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl.reset()'
	 */
	public void testReset() {

	}

}
