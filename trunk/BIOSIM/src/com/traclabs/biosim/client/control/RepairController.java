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

/**
 * @author Haibei Jiang A controller for stochastic performance and random
 *         failure modeling
 */

/*
 * To compile: 1) build biosim (type "ant" in BIOSIM_HOME directory) To run:
 * (assuming BIOSIM_HOME/bin is in your path) 1)type "run-nameserver" 2)type
 * "run-server -xml=test/CEVConfig.xml" 3)type "java -classpath
 * $BIOSIM_HOME/lib/xerces/xercesImpl.jar:$BIOSIM_HOME/lib/log4j/log4j.jar:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb/logkit.jar:$BIOSIM_HOME/lib/jacorb/avalon-framework.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME/build:$BIOSIM_HOME/resources
 * -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
 * -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
 * -DORBInitRef.NameService=file:$BIOSIM_HOME/tmp/ns/ior.txt
 * com.traclabs.biosim.client.control.RepairController"
 */

public class RepairController implements BiosimController {

	// remember to change path for xml file
	private static String CONFIGURATION_FILE = "/reliability/CEVconfig.xml";

	private static final String LOG_FILE = "RepairControllerResults.log";

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

	private GenericSensor myOGS_PortableWaterInFlowRateSensor;

	private GenericSensor myOGS_PowerConsumerRateSensor;

	private GenericSensor myVCCR_PowerConsumerRateSensor;

	private GenericSensor myVCCR_CO2ProducerFlowRateSensor;

	private GenericSensor myInjector_O2ConsumerRateSensor;

	private GenericSensor myInjector_O2ProducerRateSensor;

	private GenericSensor myWaterRS_DirtyWaterConsumerRateSensor;

	private GenericSensor myWaterRS_GreyWaterConsumerRateSensor;

	private GenericSensor myWaterRS_PortableWaterProducerRateSensor;

	private GenericSensor myWaterRS_PowerConsumerRateSensor;

	private int myRepairDelay = 0;

	private boolean logToFile = false;

	private PrintStream myOutput;

	public RepairController(boolean log) {
		logToFile = log;
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
		if (logToFile) {
			try {
				myOutput = new PrintStream(new FileOutputStream(LOG_FILE, true));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else
			myOutput = System.out;
	}

	public static void main(String[] args) {
		boolean logToFile = Boolean.parseBoolean(CommandLineUtils
				.getOptionValueFromArgs(args, "log"));
		RepairController myController = new RepairController(logToFile);
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
		BioHolderInitializer.setFile(CONFIGURATION_FILE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
		crewEnvironment = myBioHolder.theSimEnvironments.get(0);
		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);

		// Air Sensors
		GenericSensor myOGS_O2OutFlowRateSensor = myBioHolder.theO2OutFlowRateSensors
				.get(0);
		GenericSensor myOGS_H2OutFlowRateSensor = myBioHolder.theH2OutFlowRateSensors
				.get(0);
		GenericSensor myVCCR_CO2ProducerFlowRateSensor = myBioHolder.theCO2OutFlowRateSensors
				.get(0);
		GenericSensor myInjector_O2ConsumerRateSensor = myBioHolder.theO2InFlowRateSensors
				.get(0);
		GenericSensor myInjector_O2ProducerRateSensor = myBioHolder.theO2OutFlowRateSensors
				.get(1);

		// Power Sensors
		GenericSensor myOGS_PowerConsumerRateSensor = myBioHolder.thePowerInFlowRateSensors
				.get(0);
		GenericSensor myVCCR_PowerConsumerRateSensor = myBioHolder.thePowerInFlowRateSensors
				.get(1);
		GenericSensor myWaterRS_PowerConsumerRateSensor = myBioHolder.thePowerInFlowRateSensors
				.get(2);

		// Water Sensors
		GenericSensor myOGS_PortableWaterInFlowRateSensor = myBioHolder.thePotableWaterInFlowRateSensors
				.get(0);
		GenericSensor myWaterRS_DirtyWaterConsumerRateSensor = myBioHolder.theDirtyWaterInFlowRateSensors
				.get(0);
		GenericSensor myWaterRS_GreyWaterConsumerRateSensor = myBioHolder.theGreyWaterInFlowRateSensors
				.get(0);
		GenericSensor myWaterRS_PortableWaterProducerRateSensor = myBioHolder.thePotableWaterOutFlowRateSensors
				.get(0);

		// Crew Suvival Condition Sensors
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
			if (crewShouldDie())
				myBioHolder.theCrewGroups.get(0).killCrew();
			stepSim();
		} while (!myBioDriver.isDone());
		printResults();
		myBioDriver.endSimulation();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
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
					+ myO2PressureSensor.getValue());
			return true;
		} else
			return false;
	}

	public void printResults() {
		myOutput.println();
		myOutput
				.println("Ticks H2ProducerOGS O2ProducerOGS PotableWaterConsumeOGS PowerConsumerOGS PowerConsumerVCCR CO2ProducerVCCR O2ConsumerInjector O2ProdurerInjector DirtyWaterConsumer GreyWaterConsumer PotableWaterProducer PowerConsumerWaterRS");
		myOutput.print(myBioDriver.getTicks() + "     ");// Ticks

		myOutput.print(myOGS_H2OutFlowRateSensor.getValue() + "     ");// H2ProducerOGS
		myOutput.print(myOGS_O2OutFlowRateSensor.getValue() + "     ");// O2ProducerOGS
		myOutput.print(myOGS_PortableWaterInFlowRateSensor.getValue() + "  ");// PotableWaterConsumeOGS
		myOutput.print(myOGS_PowerConsumerRateSensor.getValue() + "  "); // PowerConsumerOGS

		myOutput.print(myVCCR_PowerConsumerRateSensor.getValue() + "   "); // PowerConsumerVCCR
		myOutput.print(myVCCR_CO2ProducerFlowRateSensor.getValue() + "   ");// CO2ProducerVCCR

		myOutput.print(myInjector_O2ConsumerRateSensor.getValue() + "   "); // O2ConsumerInjector
		myOutput.print(myInjector_O2ProducerRateSensor.getValue() + "   ");// O2ProducerINjector

		myOutput.print(myWaterRS_DirtyWaterConsumerRateSensor.getValue()
				+ "   "); // DirtyWaterConsumer
		myOutput
				.print(myWaterRS_GreyWaterConsumerRateSensor.getValue() + "   "); // GreyWaterConsumer
		myOutput.print(myWaterRS_PortableWaterProducerRateSensor.getValue()
				+ "   "); // PortableWaterProducer
		myOutput.print(myWaterRS_PowerConsumerRateSensor.getValue() + "   "); // PowerConsumer
		myOutput.flush();
	}

	public boolean checkFailure() {
		if (myBioHolder.theCO2Stores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by CO2Store " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theVCCRModules.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by VCCR " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theO2Stores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by O2Store " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theOGSModules.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by OGS " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theH2Stores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by H2Store " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theCrewGroups.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by Crew " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theFoodStores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by FoodStore " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theInjectors.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by Injector " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.thePowerStores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by PowerStore " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theDryWasteStores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by DryWasteStore " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.thePotableWaterStores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by PortableWaterStore "
					+ " " + " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by DirtyWaterStore " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theWaterRSModules.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by WaterRS " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myLogger.info("Component failure caused by DirtyWaterStore " + " "
					+ " at Tick " + myBioDriver.getTicks());
			return true;
		} else
			return false;
	}

	public void componentRepair() {
		if (myBioHolder.theCO2Stores.get(0).isMalfunctioning()) {
			myBioHolder.theCO2Stores.get(0).reset();
			myLogger.info("CO2Store is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theVCCRModules.get(0).isMalfunctioning()) {
			myBioHolder.theVCCRModules.get(0).reset();
			myLogger.info("VCCR is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theO2Stores.get(0).isMalfunctioning()) {
			myBioHolder.theO2Stores.get(0).reset();
			myLogger.info("O2Store is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theOGSModules.get(0).isMalfunctioning()) {
			myBioHolder.theOGSModules.get(0).reset();
			myLogger.info("OGS is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theH2Stores.get(0).isMalfunctioning()) {
			myBioHolder.theH2Stores.get(0).reset();
			myLogger.info("H2Store is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theCrewGroups.get(0).isMalfunctioning()) {
			myBioHolder.theCrewGroups.get(0).reset();
			myLogger.info("Crew has recovered" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theFoodStores.get(0).isMalfunctioning()) {
			myBioHolder.theFoodStores.get(0).reset();
			myLogger.info("FoodStore is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theInjectors.get(0).isMalfunctioning()) {
			myBioHolder.theInjectors.get(0).reset();
			myLogger.info("Injector is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.thePowerStores.get(0).isMalfunctioning()) {
			myBioHolder.thePowerStores.get(0).reset();
			myLogger.info("PowerStore is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theDryWasteStores.get(0).isMalfunctioning()) {
			myBioHolder.theDryWasteStores.get(0).reset();
			myLogger.info("DryWasteStore is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.thePotableWaterStores.get(0).isMalfunctioning()) {
			myBioHolder.thePotableWaterStores.get(0).reset();
			myLogger.info("PortableWaterStore is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myBioHolder.theDirtyWaterStores.get(0).reset();
			myLogger.info("DirtyWaterStore is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theWaterRSModules.get(0).isMalfunctioning()) {
			myBioHolder.theWaterRSModules.get(0).reset();
			myLogger.info("WaterRS is repaired " + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			myBioHolder.theDirtyWaterStores.get(0).reset();
			myLogger.info("DirtyWaterStore is repaired" + " " + " at Tick "
					+ myBioDriver.getTicks());
		}
	}

	/**
	 * Executed every tick. Looks at a sensor, looks at an actuator, then
	 * increments the actuator.
	 */
	public void stepSim() {
		// Check failure to monitor component malfunction using a Boolean
		// "CheckFailure"
		// Report Failure and fix the failed component using a function
		// "ComponentRepair"
		if (checkFailure()) {
			if (myRepairDelay >= 1) { // Repair Delay is the time needed for
									// repair activities
				componentRepair();
				myRepairDelay = 0;
			} else {
				myRepairDelay = 1;
			}
		}
	}
}
