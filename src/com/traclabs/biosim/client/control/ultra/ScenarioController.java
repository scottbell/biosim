package com.traclabs.biosim.client.control.ultra;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.util.OrbUtils;

public abstract class ScenarioController {

	protected static final String DEFAULT_OUTPUT_DIRECTORY = "/home/tishtell/logs";

	protected static final String LOG_SUFFIX = ".csv";

	protected static final String LOG_DELIMETER = ",";
	
	protected static final int NUMBER_OF_RUNS = 10;

	protected int myNumberOfRuns;

	protected BufferedWriter myWriter;

	protected BioDriver myBioDriver;

	protected BioHolder myBioHolder;

	protected Logger myLogger;
	

	public ScenarioController(int numberOfRuns, String outputDirectoryPath)
			throws IOException {

		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
		this.myNumberOfRuns = numberOfRuns;
		File outputDirectory = new File(outputDirectoryPath);

		if (!outputDirectory.exists()) {
			throw new IllegalArgumentException(outputDirectoryPath
					+ " does not exist ");
		}
		if (!outputDirectory.isDirectory()) {
			throw new IllegalArgumentException(outputDirectoryPath
					+ " is NOT an Output Directory ");
		}
		Date date = new Date();
		String outputFileName = date.toString() + LOG_SUFFIX;
		File outputFile = new File(outputDirectory, outputFileName);
		myWriter = new BufferedWriter(new FileWriter(outputFile));
		myWriter.write("Condition Type");
		myWriter.write(LOG_DELIMETER);
		myWriter.write("Run");
		myWriter.write(LOG_DELIMETER);
		myWriter.write("Result");
		myWriter.newLine();
		myWriter.flush();

	}
	/**
	 * Collects references to BioModules we'll need to run/observer/poke the
	 * sim. The BioHolder is a utility for clients to easily access different
	 * parts of BioSim.
	 * 
	 */
	
	public BioHolder collectReferences(String configurationFile) {
		BioHolderInitializer.setFile(configurationFile);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
		return myBioHolder;
	}
	
	/**
	 * Main loop of controller. Pauses the simulation, then ticks it one tick at
	 * a time until end condition is met.
	 * 
	 * @throws IOException
	 */
	public void runSim(List modules) {
		int numberofModules = modules.size();
		for (int runNumber = 1; runNumber <= myNumberOfRuns; runNumber++) {
			myLogger.info("Starting run " + runNumber);
			for (int systemsAllowedToFail = 1; systemsAllowedToFail <= numberofModules; systemsAllowedToFail++) {
				myLogger.info("Starting configuration " + systemsAllowedToFail
						+ " of " + numberofModules);
				myBioDriver.setPauseSimulation(true);
				myBioDriver.startSimulation();

				while (!endConditionMet(systemsAllowedToFail, modules))
					stepSim();
				// if we get here, the end condition has been met
				myBioDriver.endSimulation();

				writeToLog(systemsAllowedToFail, numberofModules,
						runNumber, myBioDriver.getTicks());
				myLogger.info("Ended configuration " + systemsAllowedToFail
						+ " of " + numberofModules);
			}
			myLogger.info("Ended run " + runNumber + " on tick "
					+ myBioDriver.getTicks());

		}

	}
	

	/**
	 * Executed every tick. Looks at a sensor, looks at an actuator, then
	 * increments the actuator.
	 */
	public void stepSim() {

		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
	}
	
	/**
	 * If the oxygen in the cabin drifts below 10%, stop the sim.
	 */
	protected abstract boolean endConditionMet(int systemsAllowedToFail, List modules);
	


	private void writeToLog(int systemsAllowedToFail, int totalSystems,
			int runNumber, int ticks) {
		try {
			myWriter.write(systemsAllowedToFail + " of " + totalSystems);
			myWriter.write(LOG_DELIMETER);
			myWriter.write(runNumber + "");
			myWriter.write(LOG_DELIMETER);
			myWriter.write(ticks + " ");
			myWriter.newLine();
			myWriter.flush();
		} catch (IOException e) {
			myLogger.error("Error writing to buffer", e);
		}
	}

}
