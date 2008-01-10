package com.traclabs.biosim.client.control;

import java.util.Random;

import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.util.MersenneTwister;

/**
 * A controller to end and change the simulation based on gas composition.
 * Only vary Crop Area 
 * Configuration 1 for analytical approach
 * @author Haibei Jiang
 */

public class AnalyticalController1 extends EnvironmentController implements BiosimController {

	public static final String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";

	private static final String DEFAULT_LOG_FILE = "AnalyticalController1.log";

	private static final long seed =  System.currentTimeMillis();
		
	protected static Random myRandomNumberGenerator = new MersenneTwister(seed);
	
	public AnalyticalController1() {
		super(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}
	
	public AnalyticalController1(String configurationFileName, String logFileName) {
		super(configurationFileName, logFileName);
	}
	
	protected static float getCropTotal(){
		return 200 * myRandomNumberGenerator.nextFloat();
	}

	public static void main(String[] args) {
		int max = 2000;
		for (int i = 0; i < max; i ++){
			AnalyticalController1 myController = new AnalyticalController1(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
			myController.collectReferences();
			myController.runSim();
		}
	}
	
	protected PlantType getPlantType(){
		return myBioHolder.theBiomassPSModules.get(0).getShelf(0).getCropType();
	}
	
	public void collectReferences(){
		super.collectReferences();
		//this changes the crop area and the crop type, before the first tick
		myBioHolder.theBiomassPSModules.get(0).getShelf(0).replant(getPlantType(), getCropTotal());
	}
}

