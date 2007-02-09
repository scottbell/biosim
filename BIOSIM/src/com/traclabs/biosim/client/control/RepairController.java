package com.traclabs.biosim.client.control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.crew.CrewPerson;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Accumulator;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.util.CommandLineUtils;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Haibei Jiang
 * A controller for stochastic performance and random failure modeling
 */

/*
To compile:
1) build biosim (type "ant" in BIOSIM_HOME directory)

To run: (assuming BIOSIM_HOME/bin is in your path)
1)type "run-nameserver"
2)type "run-server -xml=test/CEVConfig.xml"
3)type "java -classpath $BIOSIM_HOME/lib/xerces/xercesImpl.jar:$BIOSIM_HOME/lib/log4j/log4j.jar:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb/logkit.jar:$BIOSIM_HOME/lib/jacorb/avalon-framework.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME/build:$BIOSIM_HOME/resources -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton -DORBInitRef.NameService=file:$BIOSIM_HOME/tmp/ns/ior.txt com.traclabs.biosim.client.control.RepairController"

*/


public class RepairController implements BiosimController {
	
	private static String CONFIGURATION_FILE = "/BIOSIM/resources/com/traclabs/biosim/server/framework/configuration/reliability/CEVconfig.xml";

	private static final String LOG_FILE = "/home/RepairController/RepairControllerResults.txt";
	
	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;
	
	private CrewPerson myCrewPerson;
	
	private SimEnvironment crewEnvironment;

	private GenericSensor myO2PressureSensor;

	private GenericSensor myO2ConcentrationSensor;
	
	private GenericSensor myCO2PressureSensor;

	private GenericSensor myNitrogenPressureSensor;

	private GenericSensor myVaporPressureSensor;

	private GenericSensor myTimeTillCanopyClosureSensor;
	
	private boolean logToFile = false;
	FileOutputStream out;
	private PrintStream myOutput;
	
	
	public RepairController(boolean log) {
		logToFile = log;
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
		collectReferences();
		try{
			out = new FileOutputStream("Configuration.txt", true);		
		}catch (Exception e){
			System.out.println("Can't open Configuration.txt.");
		}

		if (logToFile) {
			try {
				myOutput = new PrintStream(new FileOutputStream(LOG_FILE, true));
			} 
			catch (FileNotFoundException e) {
						e.printStackTrace();
			}
		} else
			myOutput = System.out;
	}

	public static void main(String[] args) {
		boolean logToFile = Boolean.parseBoolean(CommandLineUtils.getOptionValueFromArgs(args, "log"));
		RepairController myController = new RepairController(logToFile);
		myController.runSim();
	}
	
	/**
	 * Collects references to BioModules we'll need to run/observer/poke the
	 * sim. The BioHolder is a utility for clients to easily access different
	 * parts of BioSim.
	 * 
	 */
	
	public void collectReferences() {
		BioHolderInitializer.setFile(CONFIGURATION_FILE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
		crewEnvironment = myBioHolder.theSimEnvironments.get(0);
		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);
		
		//Crew Failure Enabled
		myCrewPerson = myBioHolder.theCrewGroups.get(0).getCrewPerson("Bob Roberts");
		myBioHolder.theCrewGroups.get(0).isFailureEnabled();
		
		//Air Modules Failure Enabled
		myBioHolder.theCO2Stores.get(0).isFailureEnabled();
		myBioHolder.theVCCRModules.get(0).isFailureEnabled();
		myBioHolder.theO2Stores.get(0).isFailureEnabled();
		myBioHolder.theH2Stores.get(0).isFailureEnabled();
		myBioHolder.theOGSModules.get(0).isFailureEnabled();
		
		//Food Store Failure
		myBioHolder.theFoodStores.get(0).isFailureEnabled();
		
		
		
		myO2ConcentrationSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasConcentrationSensors, crewEnvironment.getO2Store());
		
		myO2PressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors,crewEnvironment.getO2Store());
		
		myCO2PressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, crewEnvironment.getCO2Store());
		
		myNitrogenPressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, crewEnvironment.getNitrogenStore());
		
		myVaporPressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, crewEnvironment.getVaporStore());
		
		
	}
	
	/**
	 * Main loop of controller. Pauses the simulation, then ticks it one tick at
	 * a time until end condition is met.
	 */
	public void runSim() {
		
		/**		
		 *prints the "name" of the simulation (# of Designed Configuration)
		 *output.println();
		 *output.println();
		 *output.print("Utility = "	+ myBioHolder.theBiomassPSModules.get(0).getShelf(0).getCropAreaUsed());
		 *
		 *output.println();
		 *output.println("Ticks TotalPressure O2PP CO2PP NitrogenPP VaporPP Activity");
		 *output.flush();
		*/
		
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		myLogger.info("Controller starting run");
		while (!RepairConditionMet())
			
			stepSim();
		
		//if we get here, the end condition has been met
		myBioDriver.endSimulation();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
	}
	
	/**
	 * If one component has the status "failed", pause the sim and initial a repair.
	 */
	private boolean RepairConditionMet() {
		float oxygenPercentage = myO2ConcentrationSensor.getValue();
		return (oxygenPercentage < 0.10);
	}
	
	/**
	 * Executed every tick.  Looks at a sensor, looks at an actuator,
	 * then increments the actuator.
	 */
	
	public void stepSim() {
		
	}

	
}


