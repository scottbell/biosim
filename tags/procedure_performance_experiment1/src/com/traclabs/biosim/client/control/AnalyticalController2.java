package com.traclabs.biosim.client.control;

import com.traclabs.biosim.idl.simulation.food.PlantType;


/**
 * A controller to end and change the simulation based on gas composition.
 * Change Crop Area and Crop Type 
 * Configuration 2 for analytical approach
 * @author Haibei Jiang
 */

public class AnalyticalController2 extends AnalyticalController1 {
	public static final String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";

	private static final String DEFAULT_LOG_FILE = "AnalyticalController2.log";
	
	private PlantType plantType = PlantType.from_int(myRandomNumberGenerator.nextInt(10));
	
	public AnalyticalController2() {
		super(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}
	
	public AnalyticalController2(String configurationFileName, String logFileName) {
		super(configurationFileName, logFileName);
	}

	public static void main(String[] args) {
		int max = 5000;
		for (int i = 0; i < max; i ++){
			AnalyticalController2 myController = new AnalyticalController2();
			myController.collectReferences();
			myController.runSim();
		}
	}
	
	protected PlantType getPlantType(){
		return plantType;
	}
	
	
}

