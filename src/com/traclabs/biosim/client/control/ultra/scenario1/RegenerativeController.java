package com.traclabs.biosim.client.control.ultra.scenario1;

import java.io.IOException;

import com.traclabs.biosim.client.control.ultra.SystemFailureController;
import com.traclabs.biosim.client.util.BioHolder;


/**
 * @author Pritesh Patel
 */

public class RegenerativeController extends SystemFailureController {


	
	private static final String CONFIGURATION_FILE = "reliability/ultra/scenario1/regenerative.biosim";

	public RegenerativeController(int numberOfRuns, String outputDirectoryPath)
			throws IOException {
		super(numberOfRuns, outputDirectoryPath);
		
	}

	public static void main(String[] args) throws IOException {
		RegenerativeController myController = new RegenerativeController (NUMBER_OF_RUNS,
				DEFAULT_OUTPUT_DIRECTORY);
		BioHolder holder = myController.collectReferences(CONFIGURATION_FILE);
		//myController.runSim(holder.theWaterRSModules);

	}
	

}
