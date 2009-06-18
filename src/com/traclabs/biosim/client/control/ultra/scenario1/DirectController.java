package com.traclabs.biosim.client.control.ultra.scenario1;

import java.io.IOException;

import com.traclabs.biosim.client.control.ultra.SystemFailureController;
import com.traclabs.biosim.client.util.BioHolder;

/**
 * @author Pritesh Patel
 */



public class DirectController extends SystemFailureController {
	
	private static final String CONFIGURATION_FILE = "reliability/ultra/scenario1/direct.biosim";

	public DirectController(int numberOfRuns, String outputDirectoryPath)
			throws IOException {
		super(numberOfRuns, outputDirectoryPath);
		
	}

	public static void main(String[] args) throws IOException {
		DirectController myController = new DirectController(NUMBER_OF_RUNS,
				DEFAULT_OUTPUT_DIRECTORY);
		BioHolder holder = myController.collectReferences(CONFIGURATION_FILE);
		myController.runSim(holder.thePotableWaterStores);

	}


}
