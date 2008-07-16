package com.traclabs.biosim.client.control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;

import com.traclabs.biosim.idl.simulation.crew.CrewPerson;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.CommandLineUtils;
import com.traclabs.biosim.util.OrbUtils;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.simulation.framework.Injector;

/**
 * @author Haibei Jiang A controller for stochastic performance and random
 *         failure modeling
 */

/*
 * To run the reliability controller: (assuming BIOSIM_HOME/bin is in your path)
 * 1)type run-nameserver 2)type run-server
 * -xml=/home/haibei/workspace/BIOSIM/resources
 * /com/traclabs/biosim/server/framework/configuration/reliability/CEVconfig.xml
 * 3)type java
 * -classpath$BIOSIM_HOME/lib/xerces/xercesImpl.jar:$BIOSIM_HOME/lib/
 * log4j/log4j.
 * jar:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb/logkit
 * .jar:$BIOSIM_HOME
 * /lib/jacorb/avalon-framework.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME
 * /build:$BIOSIM_HOME/resources -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
 * -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
 * -DORBInitRef.NameService=file:$BIOSIM_HOME/tmp/ns/ior.txt
 * com.traclabs.biosim.client.control.SeriesController
 */

public class SeriesController implements BiosimController {

	// remember to change path for xml file
	private static String CONFIGURATION_FILE = "/AIAA/series.xml";
	private static final String SENSOR_LOG_FILE = "SystemSensors.log";
	private static final String REPAIR_LOG_FILE = "FailureEvent.log";

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;

	private CrewPerson myCrewPerson;

	private SimEnvironment crewEnvironment;

	private GenericSensor myO2PressureSensor;

	private GenericSensor myCO2PressureSensor;

	private GenericSensor myNitrogenPressureSensor;

	private GenericSensor myVaporPressureSensor;

	private GenericSensor myOGS_O2OutFlowRateSensor;

	private GenericSensor myOGS_H2OutFlowRateSensor;

	private GenericSensor myOGS_PotableWaterInFlowRateSensor;

	private GenericSensor myOGS_PowerConsumerRateSensor;

	private GenericSensor myVCCR_PowerConsumerRateSensor;

	private GenericSensor myVCCR_CO2ProducerFlowRateSensor;

	private GenericSensor myInjector_O2ConsumerRateSensor;

	private GenericSensor myInjector_O2ProducerRateSensor;

	private GenericSensor myWaterRS_DirtyWaterConsumerRateSensor;

	private GenericSensor myWaterRS_GreyWaterConsumerRateSensor;

	private GenericSensor myWaterRS_PotableWaterProducerRateSensor;

	private GenericSensor myWaterRS_PowerConsumerRateSensor;

	private GenericSensor myFoodStoreSensor;

	private GenericSensor myPowerStoreSensor;

	private GenericSensor myO2StoreSensor;

	private GenericSensor myPotableWaterStoreSensor;

	private GenericActuator myTrial;
	private GenericActuator myO2InjectorActuator;

	private int myRepairDelay = 1;

	private boolean logToFile = true;

	private PrintStream mySensorOutput;

	private PrintStream myRepairOutput;

	public SeriesController(boolean log) {
		logToFile = log;
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
		if (logToFile) {
			try {
				mySensorOutput = new PrintStream(new FileOutputStream(
						SENSOR_LOG_FILE, true));
				myRepairOutput = new PrintStream(new FileOutputStream(
						REPAIR_LOG_FILE, true));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			mySensorOutput = System.out;
			myRepairOutput = System.out;
		}
		mySensorOutput
				.println("Ticks H2ProducerOGS O2ProducerOGS PotableWaterConsumeOGS PowerConsumerOGS PowerConsumerVCCR CO2ProducerVCCR O2ConsumerInjector O2ProdurerInjector DirtyWaterConsumer GreyWaterConsumer PotableWaterProducer PowerConsumerWaterRS FoodStore PowerStore PotatbleWaterStore O2Store O2PP CO2PP N2PP VaporPP");
	}

	public static void main(String[] args) {
		boolean logToFile = Boolean.parseBoolean(CommandLineUtils
				.getOptionValueFromArgs(args, "log"));
		int max = 4;
		for (int i = 0; i < max; i++) {
			SeriesController myController = new SeriesController(logToFile);
			myController.collectReferences();
			myController.runSim();
		}
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
		Injector myInjector = myBioHolder.theInjectors.get(0);
		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);

		Injector O2Injector = myBioHolder.theInjectors.get(0);

		myO2InjectorActuator = (myBioHolder.getActuatorAttachedTo(
				myBioHolder.theO2InFlowRateActuators, O2Injector));

		// Air Sensors
		myOGS_O2OutFlowRateSensor = myBioHolder.theO2OutFlowRateSensors.get(0);
		myOGS_H2OutFlowRateSensor = myBioHolder.theH2OutFlowRateSensors.get(0);
		myVCCR_CO2ProducerFlowRateSensor = myBioHolder.theCO2OutFlowRateSensors
				.get(0);
		myInjector_O2ConsumerRateSensor = myBioHolder.theO2InFlowRateSensors
				.get(0);
		myInjector_O2ProducerRateSensor = myBioHolder.theO2OutFlowRateSensors
				.get(1);

		// Power Sensors
		myOGS_PowerConsumerRateSensor = myBioHolder.thePowerInFlowRateSensors
				.get(0);
		myVCCR_PowerConsumerRateSensor = myBioHolder.thePowerInFlowRateSensors
				.get(1);
		myWaterRS_PowerConsumerRateSensor = myBioHolder.thePowerInFlowRateSensors
				.get(2);

		// Water Sensors
		myOGS_PotableWaterInFlowRateSensor = myBioHolder.thePotableWaterInFlowRateSensors
				.get(0);
		myWaterRS_DirtyWaterConsumerRateSensor = myBioHolder.theDirtyWaterInFlowRateSensors
				.get(0);
		myWaterRS_GreyWaterConsumerRateSensor = myBioHolder.theGreyWaterInFlowRateSensors
				.get(0);
		myWaterRS_PotableWaterProducerRateSensor = myBioHolder.thePotableWaterOutFlowRateSensors
				.get(0);

		// Crew Survival Condition Sensors
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

		// Level Sensors
		myFoodStoreSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theStoreLevelSensors, myBioHolder.theFoodStores
						.get(0));

		myPowerStoreSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theStoreLevelSensors, myBioHolder.thePowerStores
						.get(0));

		myPotableWaterStoreSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theStoreLevelSensors,
				myBioHolder.thePotableWaterStores.get(0));

		myO2StoreSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theStoreLevelSensors, myBioHolder.theO2Stores
						.get(0));

	}

	/**
	 * Main loop of controller. Pauses the simulation, then ticks it one tick at
	 * a time until end condition is met.
	 */
	public void runSim() {
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		myLogger.info("Controller starting run");
		do {
			myBioDriver.advanceOneTick();
			if (crewShouldDie()) {
				myLogger.info("Killing crew");
				myBioHolder.theCrewGroups.get(0).killCrew();
			}
			stepSim();
			printResults();
			mySensorOutput.flush();
			myRepairOutput.flush();
			if (myBioDriver.isDone())
				myLogger.info("Biosim thinks the simulation is finished");
		} while (!myBioDriver.isDone());
		myLogger.info("Simluation ended on tick " + myBioDriver.getTicks());
		myBioDriver.endSimulation();
		myBioDriver.reset();
		mySensorOutput.println("Controller finished run");
		mySensorOutput.println();
		myRepairOutput.println("Controller finished run");
		myRepairOutput.println();
		mySensorOutput.flush();
		myRepairOutput.flush();
	}

	/**
	 * If the crew is dead, end the simulation.
	 */
	private boolean crewShouldDie() {
		if (myO2PressureSensor.getValue() < 10.13) {
			myLogger.info("killing crew for low oxygen: "
					+ myO2PressureSensor.getValue());
			return true;

		} else if (myO2PressureSensor.getValue() > 30.39) {
			myLogger.info("killing crew for high oxygen: "
					+ myO2PressureSensor.getValue());
			return true;

		} else if (myCO2PressureSensor.getValue() > 1) {
			myLogger.info("killing crew for high CO2: "
					+ myCO2PressureSensor.getValue());
			return true;
		} else
			return false;
	}

	public void printResults() {
		// tick number
		mySensorOutput.println();
		mySensorOutput.print(myBioDriver.getTicks());// Ticks
		mySensorOutput.print("\t");
		// OGS info
		mySensorOutput.print(myOGS_H2OutFlowRateSensor.getValue());// H2ProducerOGS
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_O2OutFlowRateSensor.getValue());// O2ProducerOGS
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_PotableWaterInFlowRateSensor.getValue());// PotableWaterConsumeOGS
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_PowerConsumerRateSensor.getValue()); // PowerConsumerOGS
		mySensorOutput.print("\t");
		// VCCR info
		mySensorOutput.print(myVCCR_PowerConsumerRateSensor.getValue()); // PowerConsumerVCCR
		mySensorOutput.print("\t");
		mySensorOutput.print(myVCCR_CO2ProducerFlowRateSensor.getValue());// CO2ProducerVCCR
		mySensorOutput.print("\t");
		// Injector info
		mySensorOutput.print(myInjector_O2ConsumerRateSensor.getValue()); // O2ConsumerInjector
		mySensorOutput.print("\t");
		mySensorOutput.print(myInjector_O2ProducerRateSensor.getValue());// O2ProducerInjector
		mySensorOutput.print("\t");
		// WRS info
		mySensorOutput.print(myWaterRS_DirtyWaterConsumerRateSensor.getValue()); // DirtyWaterConsumer
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_GreyWaterConsumerRateSensor.getValue()); // GreyWaterConsumer
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_PotableWaterProducerRateSensor
				.getValue()); // PortableWaterProducer
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_PowerConsumerRateSensor.getValue()); // PowerConsumer
		mySensorOutput.print("\t");
		// Storage sensors
		mySensorOutput.print(myFoodStoreSensor.getValue()); // Food Store
		mySensorOutput.print("\t");
		mySensorOutput.print(myPowerStoreSensor.getValue()); // Power Store
		mySensorOutput.print("\t");
		mySensorOutput.print(myPotableWaterStoreSensor.getValue()); // Potable
																	// Water
																	// Store
		mySensorOutput.print("\t");
		mySensorOutput.print(myO2StoreSensor.getValue()); // O2 Store
		mySensorOutput.print("\t");
		// Environmental Condition Sensors
		mySensorOutput.print(myO2PressureSensor.getValue()); // O2 pressure
		mySensorOutput.print("\t");
		mySensorOutput.print(myCO2PressureSensor.getValue()); // CO2 pressure
		mySensorOutput.print("\t");
		mySensorOutput.print(myNitrogenPressureSensor.getValue()); // N2
																	// pressure
		mySensorOutput.print("\t");
		mySensorOutput.print(myVaporPressureSensor.getValue()); // Vapor
																// pressure
		mySensorOutput.print("\t");
		mySensorOutput.print("\t");

		if (myBioHolder.theCO2Stores.get(0).isMalfunctioning()) {
			myRepairOutput.println("CO2Store failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theVCCRModules.get(0).isMalfunctioning()) {
			myRepairOutput.println("VCCR failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theO2Stores.get(0).isMalfunctioning()) {
			myRepairOutput.println("O2Store failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theOGSModules.get(0).isMalfunctioning()) {
			myRepairOutput.println("OGS failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theH2Stores.get(0).isMalfunctioning()) {
			myRepairOutput.println("H2Store failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theCrewGroups.get(0).isMalfunctioning()) {
			myRepairOutput.println("Crew failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theFoodStores.get(0).isMalfunctioning()) {
			myRepairOutput.println("FoodStore failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theInjectors.get(0).isMalfunctioning()) {
			myRepairOutput.println("Injector failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.thePowerStores.get(0).isMalfunctioning()) {
			myRepairOutput.println("PowerStore failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theDryWasteStores.get(0).isMalfunctioning()) {
			myRepairOutput.println("DryWasteStore failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.thePotableWaterStores.get(0).isMalfunctioning()) {
			myRepairOutput.println("PortableWaterStore failure" + " "
					+ " at Tick " + myBioDriver.getTicks());
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myRepairOutput.println("DirtyWaterStore failure" + " "
					+ " at Tick " + myBioDriver.getTicks());
		}
		if (myBioHolder.theWaterRSModules.get(0).isMalfunctioning()) {
			myRepairOutput.println("WaterRS failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myRepairOutput.println("DirtyWaterStore failure" + " "
					+ " at Tick " + myBioDriver.getTicks());

		}
	}

	public boolean checkFailure() {
		if (myBioHolder.theCO2Stores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theVCCRModules.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theO2Stores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theOGSModules.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theH2Stores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theCrewGroups.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theFoodStores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theInjectors.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.thePowerStores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theDryWasteStores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.thePotableWaterStores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theWaterRSModules.get(0).isMalfunctioning()) {
			return true;
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			return true;
		} else
			return false;
	}

	/*public void componentRepair() {
		if (myBioHolder.theCO2Stores.get(0).isMalfunctioning()) {
			myBioHolder.theCO2Stores.get(0).reset();
			myRepairOutput.println("CO2Store is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theVCCRModules.get(0).isMalfunctioning()) {
			myBioHolder.theVCCRModules.get(0).reset();
			myRepairOutput.println("VCCR is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theO2Stores.get(0).isMalfunctioning()) {
			myBioHolder.theO2Stores.get(0).reset();
			myRepairOutput.println("O2Store is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theOGSModules.get(0).isMalfunctioning()) {
			myBioHolder.theOGSModules.get(0).reset();
			myRepairOutput.println("OGS is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theH2Stores.get(0).isMalfunctioning()) {
			myBioHolder.theH2Stores.get(0).reset();
			myRepairOutput.println("H2Store is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theCrewGroups.get(0).isMalfunctioning()) {
			myBioHolder.theCrewGroups.get(0).reset();
			myRepairOutput.println("Crew has recovered" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theFoodStores.get(0).isMalfunctioning()) {
			myBioHolder.theFoodStores.get(0).reset();
			myRepairOutput.println("FoodStore is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theInjectors.get(0).isMalfunctioning()) {
			myBioHolder.theInjectors.get(0).reset();
			myRepairOutput.println("Injector is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.thePowerStores.get(0).isMalfunctioning()) {
			myBioHolder.thePowerStores.get(0).reset();
			myRepairOutput.println("PowerStore is repaired " + " "
					+ " at Tick " + myBioDriver.getTicks());
		}
		if (myBioHolder.theDryWasteStores.get(0).isMalfunctioning()) {
			myBioHolder.theDryWasteStores.get(0).reset();
			myRepairOutput.println("DryWasteStore is repaired" + " "
					+ " at Tick " + myBioDriver.getTicks());
		}
		if (myBioHolder.thePotableWaterStores.get(0).isMalfunctioning()) {
			myBioHolder.thePotableWaterStores.get(0).reset();
			myRepairOutput.println("PortableWaterStore is repaired" + " "
					+ " at Tick " + myBioDriver.getTicks());
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myBioHolder.theDirtyWaterStores.get(0).reset();
			myRepairOutput.println("DirtyWaterStore is repaired" + " "
					+ " at Tick " + myBioDriver.getTicks());
		}
		if (myBioHolder.theWaterRSModules.get(0).isMalfunctioning()) {
			myBioHolder.theWaterRSModules.get(0).reset();
			myRepairOutput.println("WaterRS is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myBioHolder.theDirtyWaterStores.get(0).reset();
			myRepairOutput.println("DirtyWaterStore is repaired" + " "
					+ " at Tick " + myBioDriver.getTicks());
		}
	}
	*/
	
	/**
	 * Executed every tick. Looks at a sensor, looks at an actuator, then
	 * increments the actuator.
	 */
	public void stepSim() {
		// To get the Injector to change its parameters
		if (myO2PressureSensor.getValue() > 27) {

			myO2InjectorActuator.setValue(0);

		} else if (myO2PressureSensor.getValue() < 27) {

			myO2InjectorActuator.setValue(2);
		}

		// Check failure to monitor component malfunction using a Boolean
		// "CheckFailure"
		// Report Failure and fix the failed component using a function
		// "ComponentRepair"
		// if (checkFailure()) {
		// if (myRepairDelay >= 1) { // Repair Delay is the time needed for
		// repair activities
		// componentRepair();
		// myRepairDelay = 0;
		// }else {
		// myRepairDelay = 1;
		// }
		// }
	}
}
