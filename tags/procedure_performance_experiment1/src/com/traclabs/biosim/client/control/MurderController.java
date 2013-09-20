package com.traclabs.biosim.client.control;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.OrbUtils;


/**
 * A controller to end and change the simulation based on gas composition.
 * For GA use ONLY
 * @author Kirsten Stark 
 */
public class MurderController implements BiosimController {
	private final static String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";
	private final static String DEFAULT_LOG_FILE = "Controller.log";
	
	public String myConfigurationFile;

	private String myLogFile;

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;

	private GenericSensor myO2PressureSensor;

	private GenericSensor myCO2PressureSensor;

	protected PrintStream myOutput;

	private SimEnvironment myCrewEnvironment;
	
	public MurderController(){
		this(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}
	
	public MurderController(String configurationFileName, String logFileName) {
		this.myConfigurationFile = configurationFileName;
		this.myLogFile = logFileName;
		OrbUtils.initializeLog();
		BioHolderInitializer.setFile(myConfigurationFile);
		myLogger = Logger.getLogger(this.getClass());
		if (logFileName != null) {
			try {
				myOutput = new PrintStream(new FileOutputStream(myLogFile, true));
			} 
			catch (FileNotFoundException e) {
						e.printStackTrace();
			}
		} else
			myOutput = System.out;
		
	}
	
	public String getConfigurationFile(){
		return myConfigurationFile;
	}

	public static void main(String[] args) {
		MurderController myController = new MurderController();
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
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
		myCrewEnvironment = myBioHolder.theSimEnvironments.get(0);
		myO2PressureSensor = myBioHolder
				.getSensorAttachedTo(myBioHolder.theGasPressureSensors,
						myCrewEnvironment.getO2Store());
		myCO2PressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, myCrewEnvironment
						.getCO2Store());
	}
	
	/**
	 * Main loop of controller. Pauses the simulation, then ticks it one tick CO2InInjectorat
	 * a time until end condition is met.
	 */
	public void runSim() {
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();		
		myLogger.info("Controller starting run");		
		do {
			myBioDriver.advanceOneTick();
			doTickContent();
		} while (!myBioDriver.isDone());
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
		myOutput.flush();
	}
	
	protected void doTickContent(){
		if(cropsShouldDie())
			myBioHolder.theBiomassPSModules.get(0).killPlants();
		if(crewShouldDie())
			myBioHolder.theCrewGroups.get(0).killCrew();
	}

	private boolean crewShouldDie() {
			if (myO2PressureSensor.getValue() < 10.13){
				myLogger.info("killing crew for low oxygen: "+myO2PressureSensor.getValue());
				return true;
			}
			else if(myO2PressureSensor.getValue() > 30.39){
				myLogger.info("killing crew for high oxygen: "+myO2PressureSensor.getValue());
				return true;
			}
			else if(myCO2PressureSensor.getValue() > 1) {
				myLogger.info("killing crew for high CO2: "+myO2PressureSensor.getValue());
				return true;
			}
			else
				return false;		
	}

	private boolean cropsShouldDie() {
		if (myBioDriver.getTicks() > 2){
			if (myCO2PressureSensor.getValue() < 0.033){
				myLogger.info("killing crops for low CO2: "+myCO2PressureSensor.getValue());
				return true;
			}
			else if(myCO2PressureSensor.getValue() > .2){
				myLogger.info("killing crops for high CO2: "+myCO2PressureSensor.getValue());
				return true;
			}
		}
		return false;
	}
}
