package com.traclabs.biosim.client.control.ultra.scenario1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.control.BiosimController;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Pritesh Patel
 */

public class DirectController implements BiosimController {
	private static final String CONFIGURATION_FILE = "reliability/ultra/scenario1/direct.biosim";

	private static final int NUMBER_OF_RUNS = 10;

	private static final String DEFAULT_OUTPUT_DIRECTORY = "/home/tishtell/logs";

	private static final String LOG_SUFFIX = ".csv";

	private static final String LOG_DELIMETER = ",";

	private int myNumberOfRuns = 0;

	private BufferedWriter myWriter;

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;

	public DirectController(int numberOfRuns, String outputDirectoryPath)
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

	public static void main(String[] args) throws IOException {
		DirectController myController = new DirectController(NUMBER_OF_RUNS,
				DEFAULT_OUTPUT_DIRECTORY);
		myController.collectReferences();
		myController.runSim();

	}

	/**
	 * Collects references to BioModules we'll need to run/observer/poke the
	 * sim. The BioHolder is a utility for clients to easily access different
	 * parts of BioSim.
	 * 
	 */
	public void collectReferences() {
		BioHolderInitializer.setFile(CONFIGURATION_FILE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;

	}

	/**
	 * Main loop of controller. Pauses the simulation, then ticks it one tick at
	 * a time until end condition is met.
	 * 
	 * @throws IOException
	 */
	public void runSim() {

		int numberOfWaterStores = myBioHolder.thePotableWaterStores.size();
		for (int runNumber = 1; runNumber <= myNumberOfRuns; runNumber++) {
			myLogger.info("Starting run " + runNumber);
			for (int systemsAllowedToFail = 1; systemsAllowedToFail <= numberOfWaterStores; systemsAllowedToFail++) {
				myLogger.info("Starting configuration " + systemsAllowedToFail
						+ " of " + numberOfWaterStores);
				myBioDriver.setPauseSimulation(true);
				myBioDriver.startSimulation();

				while (!endConditionMet(systemsAllowedToFail))
					stepSim();
				// if we get here, the end condition has been met
				myBioDriver.endSimulation();

				writeToLog(systemsAllowedToFail, numberOfWaterStores,
						runNumber, myBioDriver.getTicks());
				myLogger.info("Ended configuration " + systemsAllowedToFail
						+ " of " + numberOfWaterStores);
			}
			myLogger.info("Ended run " + runNumber + " on tick "
					+ myBioDriver.getTicks());

		}

	}

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

	/**
	 * If the oxygen in the cabin drifts below 10%, stop the sim.
	 */
	private boolean endConditionMet(int systemsAllowedToFail) {
		int waterStoresFailed = 0;

		for (PotableWaterStore waterStore : myBioHolder.thePotableWaterStores) {

			if (waterStore.isMalfunctioning())
				waterStoresFailed++;

		}

		boolean tooManyWaterSystemsFailed = waterStoresFailed >= systemsAllowedToFail;

		return tooManyWaterSystemsFailed;
	}

	/**
	 * Executed every tick. Looks at a sensor, looks at an actuator, then
	 * increments the actuator.
	 */
	public void stepSim() {

		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
	}

}
