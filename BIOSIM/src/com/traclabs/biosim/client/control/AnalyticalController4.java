package com.traclabs.biosim.client.control;


/**
 * A controller for analytical approach.
 * @author Haibei
 * Configuration 4, change total area and Crop Mix
 */
public class AnalyticalController4 extends AnalyticalController3{
	public static final String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit_CropMix.xml";

	private static final String DEFAULT_LOG_FILE = "AnalyticalController4.log";

	
	public AnalyticalController4() {
		super(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}
	
	public AnalyticalController4(String configurationFileName, String logFileName) {
		super(configurationFileName, logFileName);
	}

	public static void main(String[] args) {
		int max = 5000;
		for (int i = 0; i < max; i ++){
			AnalyticalController4 myController = new AnalyticalController4();
			myController.collectReferences();
			myController.runSim();
		}
	}
	
	protected static float getCropAreaTotal(){
		return (200 * myRandomNumberGenerator.nextFloat());
	}

}
