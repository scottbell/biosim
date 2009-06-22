package com.traclabs.biosim.client.control.ultra.scenario1;

import java.io.IOException;

import com.traclabs.biosim.client.control.ultra.CrewDeathController;
import com.traclabs.biosim.client.util.BioHolder;

public class DirectCrewController extends CrewDeathController
{

	private static final String CONFIGURATION_FILE = "reliability/ultra/scenario2/direct_crew.biosim";
	public DirectCrewController(int numberOfRuns, String outputDirectoryPath)
			throws IOException {
		super(numberOfRuns, outputDirectoryPath);
		
	}
	public static void main(String[] args) throws IOException {
		CrewDeathController myController = new DirectCrewController(NUMBER_OF_RUNS,
				DEFAULT_OUTPUT_DIRECTORY);
		BioHolder holder = myController.collectReferences(CONFIGURATION_FILE);
		myController.runSim(holder.thePotableWaterStores, holder.theCrewGroups);
	}
	
}
