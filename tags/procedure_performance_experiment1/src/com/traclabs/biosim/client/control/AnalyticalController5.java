package com.traclabs.biosim.client.control;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;


/**
 * A controller for analytical approach.
 * @author Haibei
 * Configuration 5, change total area and volume, implement Crop Mix
 */
public class AnalyticalController5 extends AnalyticalController4 {
	public static final String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit_VolumeChange.xml";

	private static final String DEFAULT_LOG_FILE = "AnalyticalController5.log";
	
	private float myPlantHeight = 0.8f + 2.4f * myRandomNumberGenerator.nextFloat();
	
	private float myVolume = getCropAreaTotal() * myPlantHeight + 20; 
	
	public AnalyticalController5() {
		super(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}
	
	public AnalyticalController5(String configurationFileName, String logFileName) {
		super(configurationFileName, logFileName);
	}

	public static void main(String[] args) {
		int max = 5000;
		for (int i = 0; i < max; i ++){
			AnalyticalController5 myController = new AnalyticalController5();
			myController.collectReferences();
			myController.runSim();
		}
	}
	
	public void collectReferences() {
		super.collectReferences();
		SimEnvironment plantEnvironment = myBioHolder.theSimEnvironments.get(1);
		plantEnvironment.setCurrentVolumeAtSeaLevel(myVolume);
	}
}
