package com.traclabs.biosim.client.control;

import org.apache.log4j.Logger;

import java.io.*;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.OrbUtils;
import com.traclabs.biosim.idl.simulation.crew.*;

/**
 * @author Scott Bell
 * A simple, yet ineffective controller
 */

/*
To compile:
1) build biosim (type "ant" in BIOSIM_HOME directory)

To run: (assuming BIOSIM_HOME/bin is in your path)
1)type "run-nameserver"
2)type "run-server -xml=test/SimpleControllerInit.xml"
3)type "java -classpath $BIOSIM_HOME/lib/xerces/xercesImpl.jar:$BIOSIM_HOME/lib/log4j/log4j.jar:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb/logkit.jar:$BIOSIM_HOME/lib/jacorb/avalon-framework.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME/build:$BIOSIM_HOME/resources -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton -DORBInitRef.NameService=file:$BIOSIM_HOME/tmp/ns/ior.txt com.traclabs.biosim.client.control.SimpleController"

Good Luck!  If you have any questions, email me at:
scott@traclabs.com

*/

public class MurderController {
	private static String CONFIGURATION_FILE = "test/MurderControllerInit.xml";

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;
	
	private CrewPerson myCrewPerson;

	float O2Concentration = 0f;
	
	float CO2Concentration = 0f;

	public MurderController() {
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
		collectReferences();
	}

	public static void main(String[] args) {
		MurderController myController = new MurderController();
		myController.runSim();
	}

	/**
	 * Collects references to BioModules we'll need
	 * to run/observer/poke the sim.  The BioHolder is 
	 * a utility for clients to easily access different parts 
	 * of BioSim.
	 *
	 */
	private void collectReferences() {
		BioHolderInitializer.setFile(CONFIGURATION_FILE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);
		myCrewPerson = myBioHolder.theCrewGroups.get(0).getCrewPerson("Nigil");
	}
	/**
	 * Main loop of controller.  Pauses the simulation, then
	 * ticks it one tick at a time until end condition is met.
	 */
	public void runSim() {
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		myLogger.info("Controller starting run");
		while (!endConditionMet())
			stepSim();
		//if we get here, the end condition has been met
		myLogger.info("O2Concentration= "+O2Concentration+ " CO2Concentration= "+CO2Concentration);
		myBioDriver.endSimulation();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
	}
	
	/**
	 * If the oxygen in the cabin drifts below 10%, stop the sim.
	 */
	private boolean endConditionMet() {
		CO2Concentration = myCrewPerson.getCO2Ratio();
		O2Concentration = myCrewPerson.getO2Ratio();
		
		if ((O2Concentration < 0.10) || (O2Concentration > 0.30) || (CO2Concentration > .06))	{
			myBioHolder.theCrewGroups.get(0).killCrew();
			return true;
		}
		else	{
			return false;
		}
	}

	/**
	 * Executed every tick.  Looks at a sensor, looks at an actuator,
	 * then increments the actuator.
	 */
	public void stepSim() {
		printResults();
		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
	}
	public void printResults()	{
		FileOutputStream out;
		PrintStream p;

		try {
			out = new FileOutputStream("/home/kirsten/MurderControllerResults.txt", true);
			p = new PrintStream( out );
			p.print("Tick # "+ myBioDriver.getTicks()+ " O2Concentration - "+O2Concentration+" CO2Concentration - "+CO2Concentration);
			p.println();
			p.flush();
			out.close();
		}
		catch (Exception e){
			System.err.println("Error writing to file.");
		}
		return;
	}
}
