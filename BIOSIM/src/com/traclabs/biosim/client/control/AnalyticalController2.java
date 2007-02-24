package com.traclabs.biosim.client.control;

import java.util.Random;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.crew.CrewPerson;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.util.MersenneTwister;


/**
 * A controller to end and change the simulation based on gas composition.
 * Change Crop Area and Crop Type 
 * Configuration 2 for analytical approach
 * @author Haibei Jiang
 */

public class AnalyticalController2 extends EnvironmentController implements BiosimController {
	public static final String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";

	private static final String DEFAULT_LOG_FILE = "AnalyticalController2.log";

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;

	private CrewPerson myCrewPerson;

	private SimEnvironment crewEnvironment;

	private GenericSensor myO2PressureSensor;

	private GenericSensor myCO2PressureSensor;

	private GenericSensor myNitrogenPressureSensor;

	private GenericSensor myVaporPressureSensor;

	private GenericSensor myTimeTillCanopyClosureSensor;

	private GenericActuator myAirOutActuator;

	private GenericActuator myNitrogenInActuator;

	private GenericActuator myCO2InActuator;

	private GenericActuator myO2OutActuator;

	private Random randomNumberGenerator = new MersenneTwister();
	
	private float myCropArea = 200 * randomNumberGenerator.nextFloat();
	
	private PlantType plantType = PlantType.from_int(randomNumberGenerator.nextInt(10));
	
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
	
	public void collectReferences(){
		super.collectReferences();
		//this changes the crop area and the crop type, before the first tick
		myBioHolder.theBiomassPSModules.get(0).getShelf(0).replant(plantType, myCropArea);
	}
	
	
}

