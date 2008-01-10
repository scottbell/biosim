package com.traclabs.biosim.client.control;


/**
 * A controller for analytical approach.
 * @author Haibei
 * Configuration 6, change total area and volume, implement Crop Mix with crop death
 */
public class AnalyticalController6 extends AnalyticalController5 {
	public static final String CONFIGURATION_FILE = "kirsten/MurderControllerInit_CropDeath.xml";

	private static final String DEFAULT_LOG_FILE = "AnalyticalController6.log";
	
	public AnalyticalController6() {
		super(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}
	
	public AnalyticalController6(String configurationFileName, String logFileName) {
		super(configurationFileName, logFileName);
	}

	public static void main(String[] args) {
		int max = 5000;
		for (int i = 0; i < max; i ++){
			AnalyticalController6 myController = new AnalyticalController6();
			myController.collectReferences();
			myController.runSim();
		}
	}
}
