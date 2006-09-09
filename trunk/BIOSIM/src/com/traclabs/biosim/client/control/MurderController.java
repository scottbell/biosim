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
 * @author Kirsten Stark A controller to end change the simulation based on air
 *         make-up
 */

/*
 * To compile: 1) build biosim (type "ant" in BIOSIM_HOME directory)
 * 
 * To run: (assuming BIOSIM_HOME/bin is in your path) 1)type "run-nameserver"
 * 2)type "run-server -xml=test/SimpleControllerInit.xml" 3)type "java
 * -classpath
 * $BIOSIM_HOME/lib/xerces/xercesImpl.jar:$BIOSIM_HOME/lib/log4j/log4j.jar:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb/logkit.jar:$BIOSIM_HOME/lib/jacorb/avalon-framework.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME/build:$BIOSIM_HOME/resources
 * -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
 * -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
 * -DORBInitRef.NameService=file:$BIOSIM_HOME/tmp/ns/ior.txt
 * com.traclabs.biosim.client.control.SimpleController"
 * 
 * Good Luck! If you have any questions, email me at: scott@traclabs.com
 * 
 */

public class MurderController implements BiosimController {
	private static final String CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";

	private static final String LOG_FILE = "/home/kirsten/MurderControllerResults.txt";

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

	PrintStream output;

	public MurderController(boolean logToFile) {
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
		collectReferences();
		if (logToFile){
		try {
			output = new PrintStream(new FileOutputStream(LOG_FILE, true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		}
		else
			output = System.out;

	}

	public static void main(String[] args) {
		boolean logToFile = Boolean.parseBoolean(CommandLineUtils.getOptionValueFromArgs(args, "log"));
		MurderController myController = new MurderController(logToFile);
		myController.runSim();

	}

	/**
	 * Collects references to BioModules we'll need to run/observer/poke the
	 * sim. The BioHolder is a utility for clients to easily access different
	 * parts of BioSim.
	 * 
	 */
	private void collectReferences() {
		BioHolderInitializer.setFile(CONFIGURATION_FILE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		crewEnvironment = myBioHolder.theSimEnvironments.get(0);
		myBioDriver = myBioHolder.theBioDriver;
		myCrewPerson = myBioHolder.theCrewGroups.get(0).getCrewPerson("Nigil");

		Injector NitrogenInjector = myBioHolder.theInjectors.get(0);
		Injector CO2Injector = myBioHolder.theInjectors.get(1);
		Accumulator AirAccumulator = myBioHolder.theAccumulators.get(0);
		Accumulator O2Accumulator = myBioHolder.theAccumulators.get(1);

		myNitrogenInActuator = myBioHolder.getActuatorAttachedTo(
				myBioHolder.theNitrogenInFlowRateActuators, NitrogenInjector);
		myAirOutActuator = myBioHolder.getActuatorAttachedTo(
				myBioHolder.theAirInFlowRateActuators, AirAccumulator);
		myCO2InActuator = myBioHolder.getActuatorAttachedTo(
				myBioHolder.theCO2InFlowRateActuators, CO2Injector);
		myO2OutActuator = myBioHolder.getActuatorAttachedTo(
				myBioHolder.theO2InFlowRateActuators, O2Accumulator);

		myO2PressureSensor = myBioHolder
				.getSensorAttachedTo(myBioHolder.theGasPressureSensors,
						crewEnvironment.getO2Store());
		myCO2PressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, crewEnvironment
						.getCO2Store());
		myNitrogenPressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, crewEnvironment
						.getNitrogenStore());
		myVaporPressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, crewEnvironment
						.getVaporStore());
		myTimeTillCanopyClosureSensor = myBioHolder.getShelfSensorAttachedTo(
				myBioHolder.theTimeTillCanopyClosureSensors,
				myBioHolder.theBiomassPSModules.get(0), 0);
		
	
	}

	/**
	 * Main loop of controller. Pauses the simulation, then ticks it one tick at
	 * a time until end condition is met.
	 */
	public void runSim() {

		// prints the "name" of the simulation (how much area)
		output.println();
		output.println();
		output.print("Crop area = "
				+ myBioHolder.theBiomassPSModules.get(0).getShelf(0)
						.getCropAreaUsed());
		output.println();
		output
				.println("Ticks TotalPressure O2PP CO2PP NitrogenPP VaporPP Activity");
		output.flush();

		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		myLogger.info("Controller starting run");
		crewEnvironment.setCurrentVolumeAtSeaLevel(32000);
		myLogger.info("The value of O2 is " + myO2PressureSensor.getValue());
		myLogger.info("The time till canopy closure is "
				+ myTimeTillCanopyClosureSensor.getValue());
		// myCrewPerson.setArrivalTick(24*(int)myTimeTillCanopyClosureSensor.getValue());
		// printResults(); //prints the initial conditions
		do {
			stepSim(); 
			myLogger.info(myBioDriver.getTicks());
			if (myBioDriver.getTicks() > 490){
				myLogger.info("Over 490 ticks.");
			}
			//myLogger.info(crewEnvironment.getCO2Store().getCurrentLevel());
			
		}while (!endConditionMet());
		 
		//if we get here, the end condition has been met
		myLogger.info("Final O2PartialPressure= "+myO2PressureSensor.getValue()+ " Final CO2PartialPressure= "+ myCO2PressureSensor.getValue());
		myBioDriver.endSimulation();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
		output.close();
	}

	private boolean endConditionMet() {

		if (((myO2PressureSensor.getValue() < 10.13)
				|| (myO2PressureSensor.getValue() > 30.39) || (myCO2PressureSensor
				.getValue() >  1))) {
			myBioHolder.theCrewGroups.get(0).killCrew();
			return true;
		}

		
	return false;
	}

	/**
	 * Executed every tick. Looks at a sensor, looks at an actuator, then
	 * increments the actuator.
	 */
	public void stepSim() {

		// CO2Pressure controls
		/*
		if ((myCO2PressureSensor.getValue() < .1)
				&& (myCO2PressureSensor.getValue() > .05)
				&& (myBioDriver.getTicks() < (myCrewPerson.getArrivalTick() + 7))) {
			myCO2InActuator.setValue(1);
		}

		if ((myCO2PressureSensor.getValue() < .05)
				&& (myBioDriver.getTicks() < (myCrewPerson.getArrivalTick() + 7))) {
			myCO2InActuator.setValue(1.5f);
		}
		
		if ((myCO2PressureSensor.getValue() > .1)){
			myCO2InActuator.setValue(0);
		}
		*/
		if((myCO2PressureSensor.getValue() < .153809) && (myBioDriver.getTicks() < (myCrewPerson.getArrivalTick() + 7))){
			myCO2InActuator.setValue(2- crewEnvironment.getCO2Store().getCurrentLevel());
		}
		
		if ((!(myCO2PressureSensor.getValue() < .153809)) && (myBioDriver.getTicks() < 120)) {
			myCO2InActuator.setValue(0);
		}

		if (myBioDriver.getTicks() > (myCrewPerson.getArrivalTick() + 7)) {
			myCO2InActuator.setValue(0);
		}
		
		if((myBioDriver.getTicks() > 2)&&((myCO2PressureSensor.getValue() < 0.033) || (myCO2PressureSensor.getValue() > .2)))	{

			myBioHolder.theBiomassPSModules.get(0).killPlants();
			myLogger.info("The crops have died from "
					+ myCO2PressureSensor.getValue() + " CO2 on tick "
					+ myBioDriver.getTicks());
		}

		// O2Pressure controls

		if ((myO2PressureSensor.getValue() > 26)) {
			myO2OutActuator.setValue(100);
		}
		if ((myO2PressureSensor.getValue() < 26)) {
			myO2OutActuator.setValue(0);
		}
		if (myBioDriver.getTicks() > (myCrewPerson.getArrivalTick() +7)) {
			myO2OutActuator.setValue(0);
		}

		// TotalPressure controls

		if ((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) > 106) {
			myAirOutActuator.setValue(65);
		}

		if ((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) < 96) {
			myNitrogenInActuator.setValue(65);
		}

		if (((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) > 96) && ((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) < 106)) {
			myAirOutActuator.setValue(0);
			myNitrogenInActuator.setValue(0);

		}

		if (myBioDriver.getTicks() < 2) {
			myAirOutActuator.setValue(0);
			myNitrogenInActuator.setValue(0);
			myO2OutActuator.setValue(0);
			myCO2InActuator.setValue(0);
		}

		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
		printResults();
	}

	public void printResults() {
		// TotalPressure = myO2PressureSensor.getValue() +
		// myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue()
		// + myVaporPressureSensor.getValue();
		output.print(myBioDriver.getTicks() + "     ");// Ticks
		output.print(myO2PressureSensor.getValue()
				+ myCO2PressureSensor.getValue()
				+ myNitrogenPressureSensor.getValue()
				+ myVaporPressureSensor.getValue() + "     ");// Total
																// Pressure
		output.print(myO2PressureSensor.getValue() + "  ");// PP of O2
		output.print(myCO2PressureSensor.getValue() + "  "); // PP of CO2
		//output.print(myBioHolder.theBiomassPSModules.get(0).getShelf(0).
		output.print(myNitrogenPressureSensor.getValue() + "    "); // PP of
																	// Nitrogen
		output.print(myVaporPressureSensor.getValue() + "   "); // PP of Vapor
		output.print(myCrewPerson.getCurrentActivity().getName() + "       ");
		// output.print(myCrewPerson.getO2Consumed() + " " +
		// myCrewPerson.getCO2Produced() + " ");
		output.println();
		if (myBioDriver.getTicks() > 480){
			output.print(myCrewPerson.getCO2Produced());
		}
		output.println();
		output.flush();
	}
}
