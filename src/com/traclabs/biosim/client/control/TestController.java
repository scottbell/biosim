package com.traclabs.biosim.client.control;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.util.OrbUtils;


public class TestController implements BiosimController{
	public static final String CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;
	
	private BiosimServer myServer;

	public TestController() {
		OrbUtils.initializeLog();
        OrbUtils.startStandaloneNameServer();
        OrbUtils.sleepAwhile();
        OrbUtils.initializeServerForStandalone();
        OrbUtils.initializeClientForStandalone();
        myServer = new BiosimServer(0, 0, CONFIGURATION_FILE);
		myLogger = Logger.getLogger(this.getClass());
	}

	public static void main(String[] args) {
		TestController myController = new TestController();
		myController.collectReferences();
		myController.runSim();
	}

	/**
	 * Collects references to BioModules we'll need
	 * to run/observer/poke the sim.  The BioHolder is 
	 * a utility for clients to easily access different parts 
	 * of BioSim.
	 *
	 */
	public void collectReferences() {
		BioHolderInitializer.setFile(CONFIGURATION_FILE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
	}
	
	/**
	 * Main loop of controller.  Pauses the simulation, then
	 * ticks it one tick at a time until end condition is met.
	 */
	public void runSim() {
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		myLogger.info("Controller starting run");
		while (!myBioDriver.isDone()){
			myBioDriver.advanceOneTick();
			myLogger.info("tick #"+myBioDriver.getTicks());
		}
		myBioDriver.endSimulation();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
	}
	

}
