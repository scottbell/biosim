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
 * @author Haibei Jiang and Francisco Capristan
 * A controller for modeling parallel system reliability
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

public class ParallelControllerOnlineRedundancy implements BiosimController {

	// remember to change path for xml file
	private static String CONFIGURATION_FILE = "/AIAA/ParallelOnlineRedundancy.xml";
	private static final String SENSOR_LOG_FILE = "ParallelOnlineRedundancySystemSensors.log";
	private static final String REPAIR_LOG_FILE = "ParallelOnlineRedundancyFailureEvent.log";

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;

	private CrewPerson myCrewPerson;

	private SimEnvironment crewEnvironment;

	private GenericSensor myO2PressureSensor;

	private GenericSensor myCO2PressureSensor;

	private GenericSensor myNitrogenPressureSensor;

	private GenericSensor myVaporPressureSensor;

	private GenericSensor myOGS_O2OutFlowRateSensor1;
	private GenericSensor myOGS_O2OutFlowRateSensor2;

	private GenericSensor myOGS_H2OutFlowRateSensor1;
	private GenericSensor myOGS_H2OutFlowRateSensor2;

	private GenericSensor myOGS_PotableWaterInFlowRateSensor1;
	private GenericSensor myOGS_PotableWaterInFlowRateSensor2;

	private GenericSensor myOGS_PowerConsumerRateSensor1;
	private GenericSensor myOGS_PowerConsumerRateSensor2;

	private GenericSensor myVCCR_PowerConsumerRateSensor1;
	private GenericSensor myVCCR_PowerConsumerRateSensor2;

	private GenericSensor myVCCR_CO2ProducerFlowRateSensor1;
	private GenericSensor myVCCR_CO2ProducerFlowRateSensor2;
	
	private GenericSensor myInjector_O2ConsumerRateSensor1;
	private GenericSensor myInjector_O2ConsumerRateSensor2;

	private GenericSensor myInjector_O2ProducerRateSensor1;
	private GenericSensor myInjector_O2ProducerRateSensor2;

	private GenericSensor myWaterRS_DirtyWaterConsumerRateSensor1;
	private GenericSensor myWaterRS_DirtyWaterConsumerRateSensor2;

	private GenericSensor myWaterRS_GreyWaterConsumerRateSensor1;
	private GenericSensor myWaterRS_GreyWaterConsumerRateSensor2;

	private GenericSensor myWaterRS_PotableWaterProducerRateSensor1;
	private GenericSensor myWaterRS_PotableWaterProducerRateSensor2;

	private GenericSensor myWaterRS_PowerConsumerRateSensor1;
	private GenericSensor myWaterRS_PowerConsumerRateSensor2;

	private GenericSensor myFoodStoreSensor;

	private GenericSensor myPowerStoreSensor;

	private GenericSensor myO2StoreSensor;

	private GenericSensor myPotableWaterStoreSensor;

	//private GenericActuator myTrial;
	
	private GenericActuator myO2InjectorActuator1;
	private GenericActuator myO2InjectorActuator2;

	private int myRepairDelay = 1;

	private boolean logToFile = true;
	private boolean OGS1active = true;
	private boolean OGS2active = true;
	private boolean VCCR1active = true;
	private boolean VCCR2active = true;
	private boolean Injector1active = true;
	private boolean Injector2active = true;
	private boolean WRS1active = true;
	private boolean WRS2active = true;
	private boolean FoodStoreactive = true;
	private boolean O2Storeactive1 = true;
	private boolean O2Storeactive2 = true;
	private boolean PowerStoreactive1 = true;
	private boolean PowerStoreactive2 = true;
	private boolean PotableWaterStoreactive1 = true;
	private boolean PotableWaterStoreactive2 = true;
	private boolean CO2Storeactive1 = true;
	private boolean CO2Storeactive2 = true;
	private boolean GreyWaterStoreactive1 = true;
	private boolean GreyWaterStoreactive2 = true;
	private boolean DirtyWaterStoreactive1 = true;
	private boolean DirtyWaterStoreactive2 = true;
	private boolean DryWasteStoreactive = true;
	private boolean once1=true;

	private boolean H2Storeactive1 = true;
	private boolean H2Storeactive2 = true;
	private boolean Crewactive = true;
	private boolean line1active = true;
	private boolean line2active = true;
	private float injectorValue;
	private float InjectorO2Consumer;
	private float InjectorO2Producer;
	private float InjectorCO2Consumer;
	private float InjectorCO2Producer;
	private float OGSPowerConsumer;
	private float OGSPotableWaterConsumer;
	private float OGSO2Producer;
	private float OGSH2Producer;
	private float VCCRPowerConsumer;
	private float VCCRAirConsumer;
	private float VCCRAirProducer;
	private float VCCRCO2Producer ;
	private float WaterRSPowerConsumer;
	private float WaterRSDirtyWaterConsumer ;
	private float WaterRSGreyWaterConsumer;
	private float WaterRSPotableWaterConsumer;
	private float DehumidifierAirConsumer;
	private float DehumidifierDirtyWaterProducer;
	private PrintStream mySensorOutput;

	private PrintStream myRepairOutput;

	public ParallelControllerOnlineRedundancy(boolean log) {
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
				.println("Ticks H2ProducerOGS1 O2ProducerOGS1 PotableWaterConsumeOGS1 PowerConsumerOGS1 H2ProducerOGS2 O2ProducerOGS2 PotableWaterConsumeOGS2 PowerConsumerOGS2 PowerConsumerVCCR1 CO2ProducerVCCR1 PowerConsumerVCCR2 CO2ProducerVCCR2	O2ConsumerInjector1 O2ProducerInjector1 O2ConsumerInjector2 O2ProducerInjector2 DirtyWaterConsumer1 GreyWaterConsumer1 PortableWaterProducer1 PowerConsumerWRS1 DirtyWaterConsumer2 GreyWaterConsumer2 PortableWaterProducer2 PowerConsumerWRS2 FoodStore PowerStore PotableWaterStore O2Store O2Pressure CO2Pressure N2Pressure VaporPressure");
	}

	public static void main(String[] args) {
		boolean logToFile = Boolean.parseBoolean(CommandLineUtils
				.getOptionValueFromArgs(args, "log"));
		int max = 3;
		for (int i = 0; i < max; i++) {
			ParallelControllerOnlineRedundancy myController = new ParallelControllerOnlineRedundancy(logToFile);
			myController.collectReferences();
			myController.desired_values();
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
		Injector myInjector1 = myBioHolder.theInjectors.get(0);
		Injector myInjector2 = myBioHolder.theInjectors.get(1);
		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);

		//Injectors and Actuators
		Injector O2Injector1 = myBioHolder.theInjectors.get(0);
		Injector O2Injector2 = myBioHolder.theInjectors.get(1);

		myO2InjectorActuator1 = (myBioHolder.getActuatorAttachedTo(
				myBioHolder.theO2InFlowRateActuators, O2Injector1));
		myO2InjectorActuator2 = (myBioHolder.getActuatorAttachedTo(
				myBioHolder.theO2InFlowRateActuators, O2Injector2));

		// Air Sensors
		myInjector_O2ConsumerRateSensor1 = myBioHolder.theO2InFlowRateSensors.get(0);
		myInjector_O2ConsumerRateSensor2 = myBioHolder.theO2InFlowRateSensors.get(1);
		myInjector_O2ProducerRateSensor1 = myBioHolder.theO2OutFlowRateSensors.get(0);
		myInjector_O2ProducerRateSensor2 = myBioHolder.theO2OutFlowRateSensors.get(1);
		myOGS_O2OutFlowRateSensor1 		= myBioHolder.theO2OutFlowRateSensors.get(2);
		myOGS_O2OutFlowRateSensor2		= myBioHolder.theO2OutFlowRateSensors.get(3);
		myOGS_H2OutFlowRateSensor1 		= myBioHolder.theH2OutFlowRateSensors.get(0);
		myOGS_H2OutFlowRateSensor2 		= myBioHolder.theH2OutFlowRateSensors.get(1);
		myVCCR_CO2ProducerFlowRateSensor1 = myBioHolder.theCO2OutFlowRateSensors.get(0);
		myVCCR_CO2ProducerFlowRateSensor2 = myBioHolder.theCO2OutFlowRateSensors.get(1);

		// Power Sensors
		myOGS_PowerConsumerRateSensor1 = myBioHolder.thePowerInFlowRateSensors.get(0);
		myOGS_PowerConsumerRateSensor2 = myBioHolder.thePowerInFlowRateSensors.get(1);
		myVCCR_PowerConsumerRateSensor1 = myBioHolder.thePowerInFlowRateSensors.get(2);
		myVCCR_PowerConsumerRateSensor2 = myBioHolder.thePowerInFlowRateSensors.get(3);
		myWaterRS_PowerConsumerRateSensor1 = myBioHolder.thePowerInFlowRateSensors.get(4);
		myWaterRS_PowerConsumerRateSensor2 = myBioHolder.thePowerInFlowRateSensors.get(5);

		// Water Sensors
		myOGS_PotableWaterInFlowRateSensor1 = myBioHolder.thePotableWaterInFlowRateSensors.get(0);
		myOGS_PotableWaterInFlowRateSensor2 = myBioHolder.thePotableWaterInFlowRateSensors.get(1);
		myWaterRS_DirtyWaterConsumerRateSensor1 = myBioHolder.theDirtyWaterInFlowRateSensors.get(0);
		myWaterRS_DirtyWaterConsumerRateSensor2 = myBioHolder.theDirtyWaterInFlowRateSensors.get(1);
		myWaterRS_GreyWaterConsumerRateSensor1 = myBioHolder.theGreyWaterInFlowRateSensors.get(0);
		myWaterRS_GreyWaterConsumerRateSensor2 = myBioHolder.theGreyWaterInFlowRateSensors.get(1);
		myWaterRS_PotableWaterProducerRateSensor1 = myBioHolder.thePotableWaterOutFlowRateSensors.get(0);
		myWaterRS_PotableWaterProducerRateSensor2 = myBioHolder.thePotableWaterOutFlowRateSensors.get(1);

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
			assign_values();
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
		// OGS1 info
		mySensorOutput.print(myOGS_H2OutFlowRateSensor1.getValue());// H2ProducerOGS1
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_O2OutFlowRateSensor1.getValue());// O2ProducerOGS1
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_PotableWaterInFlowRateSensor1.getValue());// PotableWaterConsumeOGS1
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_PowerConsumerRateSensor1.getValue()); // PowerConsumerOGS1
		mySensorOutput.print("\t");
		//OGS2 info
		mySensorOutput.print(myOGS_H2OutFlowRateSensor2.getValue());// H2ProducerOGS2
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_O2OutFlowRateSensor2.getValue());// O2ProducerOGS2
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_PotableWaterInFlowRateSensor2.getValue());// PotableWaterConsumeOGS2
		mySensorOutput.print("\t");
		mySensorOutput.print(myOGS_PowerConsumerRateSensor2.getValue()); // PowerConsumerOGS2
		mySensorOutput.print("\t");
		// VCCR1 info
		mySensorOutput.print(myVCCR_PowerConsumerRateSensor1.getValue()); // PowerConsumerVCCR1
		mySensorOutput.print("\t");
		mySensorOutput.print(myVCCR_CO2ProducerFlowRateSensor1.getValue());// CO2ProducerVCCR1
		mySensorOutput.print("\t");
		// VCCR2 info
		mySensorOutput.print(myVCCR_PowerConsumerRateSensor2.getValue()); // PowerConsumerVCCR2
		mySensorOutput.print("\t");
		mySensorOutput.print(myVCCR_CO2ProducerFlowRateSensor2.getValue());// CO2ProducerVCCR2
		mySensorOutput.print("\t");
		// Injector1 info
		mySensorOutput.print(myInjector_O2ConsumerRateSensor1.getValue()); // O2ConsumerInjector1
		mySensorOutput.print("\t");
		mySensorOutput.print(myInjector_O2ProducerRateSensor1.getValue());// O2ProducerInjector1
		mySensorOutput.print("\t");
		// Injector2 info
		mySensorOutput.print(myInjector_O2ConsumerRateSensor2.getValue()); // O2ConsumerInjector2
		mySensorOutput.print("\t");
		mySensorOutput.print(myInjector_O2ProducerRateSensor2.getValue());// O2ProducerInjector2
		mySensorOutput.print("\t");
		// WRS1 info
		mySensorOutput.print(myWaterRS_DirtyWaterConsumerRateSensor1.getValue()); // DirtyWaterConsumer1
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_GreyWaterConsumerRateSensor1.getValue()); // GreyWaterConsumer1
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_PotableWaterProducerRateSensor1.getValue()); // PotableWaterProducer1
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_PowerConsumerRateSensor1.getValue()); // PowerConsumerWRS1
		mySensorOutput.print("\t");
		// WRS2 info
		mySensorOutput.print(myWaterRS_DirtyWaterConsumerRateSensor2.getValue()); // DirtyWaterConsumer2
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_GreyWaterConsumerRateSensor2.getValue()); // GreyWaterConsumer2
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_PotableWaterProducerRateSensor2.getValue()); // PotableWaterProducer2
		mySensorOutput.print("\t");
		mySensorOutput.print(myWaterRS_PowerConsumerRateSensor2.getValue()); // PowerConsumerWRS2
		mySensorOutput.print("\t");
		// Storage sensors
		mySensorOutput.print(myFoodStoreSensor.getValue()); // Food Store
		mySensorOutput.print("\t");
		mySensorOutput.print(myPowerStoreSensor.getValue()); // Power Store
		mySensorOutput.print("\t");
		mySensorOutput.print(myPotableWaterStoreSensor.getValue()); // PotableWaterStore
		mySensorOutput.print("\t");
		mySensorOutput.print(myO2StoreSensor.getValue()); // O2 Store
		mySensorOutput.print("\t");
		
		
		
		
		// Environmental Condition Sensors
		mySensorOutput.print(myO2PressureSensor.getValue()); // O2Pressure
		mySensorOutput.print("\t");
		mySensorOutput.print(myCO2PressureSensor.getValue()); // CO2Pressure
		mySensorOutput.print("\t");
		mySensorOutput.print(myNitrogenPressureSensor.getValue()); // N2Pressure
		mySensorOutput.print("\t");
		mySensorOutput.print(myVaporPressureSensor.getValue()); // VaporPressure
		mySensorOutput.print("\t");
		mySensorOutput.print("\t");

		if (myBioHolder.theCO2Stores.get(0).isMalfunctioning()) {
			if (CO2Storeactive1){
			myRepairOutput.println("CO2Store1 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			CO2Storeactive1=false;
		}}

		if (myBioHolder.theVCCRModules.get(0).isMalfunctioning()) {
			if (VCCR1active){
			myRepairOutput.println("VCCR1 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			VCCR1active=false;
		}}
		if (myBioHolder.theO2Stores.get(0).isMalfunctioning()) {
			if (O2Storeactive1){
			myRepairOutput.println("O2Store1 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			O2Storeactive1 = false;
		}}
		if (myBioHolder.theOGSModules.get(0).isMalfunctioning()) {
			if (OGS1active){
			myRepairOutput.println("OGS1 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			OGS1active=false;
			}}
		if (myBioHolder.theH2Stores.get(0).isMalfunctioning()) {
			if (H2Storeactive1){
			myRepairOutput.println("H2Store1 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			H2Storeactive1=false;
		}}
		if (myBioHolder.theCrewGroups.get(0).isMalfunctioning()) {
			if(Crewactive){
			myRepairOutput.println("Crew failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			Crewactive=false;
		}}
		if (myBioHolder.theFoodStores.get(0).isMalfunctioning()) {
			if (FoodStoreactive){
			myRepairOutput.println("FoodStore failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			FoodStoreactive=false;
		}}
		if (myBioHolder.theInjectors.get(0).isMalfunctioning()) {
			if (Injector1active){
			myRepairOutput.println("Injector1 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			Injector1active=false;
		}}
		if (myBioHolder.thePowerStores.get(0).isMalfunctioning()) {
			if (PowerStoreactive1){
			myRepairOutput.println("PowerStore failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			PowerStoreactive1 = false;
		}}
		if (myBioHolder.theDryWasteStores.get(0).isMalfunctioning()) {
			if (DryWasteStoreactive){
			myRepairOutput.println("DryWasteStore failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			DryWasteStoreactive=false;
		}}
		if (myBioHolder.thePotableWaterStores.get(0).isMalfunctioning()) {
			if (PotableWaterStoreactive1){
			myRepairOutput.println("PortableWaterStore1 failure" + " "
					+ " at Tick " + myBioDriver.getTicks());
			PotableWaterStoreactive1=false;
		}}
		if (myBioHolder.theDirtyWaterStores.get(0).isMalfunctioning()) {
			if(DirtyWaterStoreactive1){
			myRepairOutput.println("DirtyWaterStore1 failure" + " "
					+ " at Tick " + myBioDriver.getTicks());
			DirtyWaterStoreactive1=false;
		}}
		if (myBioHolder.theWaterRSModules.get(0).isMalfunctioning()) {
			if (WRS1active){
			myRepairOutput.println("WaterRS1 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			WRS1active=false;
		}}
		
		//check regenerative component failure for second components
		if (myBioHolder.theVCCRModules.get(1).isMalfunctioning()) {
			if(VCCR2active){
			myRepairOutput.println("VCCR2 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			VCCR2active=false;
		}}


		if (myBioHolder.theOGSModules.get(1).isMalfunctioning()) {
			if (!OGS1active && OGS2active){
			myRepairOutput.println("OGS2 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			OGS2active = false;
		}}

		if (myBioHolder.theInjectors.get(1).isMalfunctioning()) {
			if (Injector2active){
			myRepairOutput.println("Injector2 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			Injector2active=false;
		}}
		if (myBioHolder.theWaterRSModules.get(1).isMalfunctioning()) {
			if(WRS2active){
			myRepairOutput.println("WaterRS2 failure" + " " + " at Tick "
					+ myBioDriver.getTicks());
			WRS2active=false;
		}}
			


		
	}

	public void stepSim() {
		// To get the Injector to change its parameters
		if (myO2PressureSensor.getValue() > 25) {

			myO2InjectorActuator1.setValue(0);
			myO2InjectorActuator2.setValue(0);

		} else if (myO2PressureSensor.getValue() < 15 && Injector1active && Injector2active) {
			
			myO2InjectorActuator1.setValue(InjectorO2Consumer);
			myO2InjectorActuator2.setValue(InjectorO2Consumer);
		}else if(myO2PressureSensor.getValue() < 15 && !Injector1active && Injector2active){
				myO2InjectorActuator2.setValue(2*InjectorO2Consumer);
			}
			else if(myO2PressureSensor.getValue() < 15 && Injector1active && !Injector2active){
				myO2InjectorActuator1.setValue(2*InjectorO2Consumer);
			}

	}
	private void desired_values(){
		InjectorO2Consumer= myBioHolder.theInjectors.get(0).getO2ConsumerDefinition().getDesiredFlowRate(0);
		InjectorO2Producer= myBioHolder.theInjectors.get(0).getO2ProducerDefinition().getDesiredFlowRate(0);
		InjectorCO2Consumer= myBioHolder.theInjectors.get(0).getCO2ConsumerDefinition().getDesiredFlowRate(0);
		InjectorCO2Producer= myBioHolder.theInjectors.get(0).getCO2ProducerDefinition().getDesiredFlowRate(0);
		OGSPowerConsumer=myBioHolder.theOGSModules.get(0).getPowerConsumerDefinition().getDesiredFlowRate(0);
		OGSPotableWaterConsumer=myBioHolder.theOGSModules.get(0).getPotableWaterConsumerDefinition().getDesiredFlowRate(0);
		OGSO2Producer=myBioHolder.theOGSModules.get(0).getO2ProducerDefinition().getDesiredFlowRate(0);
		OGSH2Producer=myBioHolder.theOGSModules.get(0).getH2ProducerDefinition().getDesiredFlowRate(0);
		VCCRPowerConsumer=myBioHolder.theVCCRModules.get(0).getPowerConsumerDefinition().getDesiredFlowRate(0);
		VCCRAirConsumer= myBioHolder.theVCCRModules.get(0).getAirConsumerDefinition().getDesiredFlowRate(0);
		VCCRAirProducer= myBioHolder.theVCCRModules.get(0).getAirProducerDefinition().getDesiredFlowRate(0);
		VCCRCO2Producer = myBioHolder.theVCCRModules.get(0).getCO2ProducerDefinition().getDesiredFlowRate(0);
		WaterRSPowerConsumer = myBioHolder.theWaterRSModules.get(0).getPowerConsumerDefinition().getDesiredFlowRate(0);
		WaterRSDirtyWaterConsumer = myBioHolder.theWaterRSModules.get(0).getDirtyWaterConsumerDefinition().getDesiredFlowRate(0);
		WaterRSGreyWaterConsumer = myBioHolder.theWaterRSModules.get(0).getGreyWaterConsumerDefinition().getDesiredFlowRate(0);
		WaterRSPotableWaterConsumer = myBioHolder.theWaterRSModules.get(0).getPotableWaterProducerDefinition().getDesiredFlowRate(0);
		
	}

	public void assign_values() {
		
			
			if(VCCR2active && !VCCR1active){
			myBioHolder.theVCCRModules.get(1).getPowerConsumerDefinition().setDesiredFlowRate(2*VCCRPowerConsumer, 0);
			myBioHolder.theVCCRModules.get(1).getAirConsumerDefinition().setDesiredFlowRate(2*VCCRAirConsumer, 0);
			myBioHolder.theVCCRModules.get(1).getAirProducerDefinition().setDesiredFlowRate(2*VCCRAirProducer, 0);
			myBioHolder.theVCCRModules.get(1).getCO2ProducerDefinition().setDesiredFlowRate(2*VCCRCO2Producer, 0);
		}
		if (OGS2active && !OGS1active){
			myBioHolder.theOGSModules.get(1).getPowerConsumerDefinition().setDesiredFlowRate(2*OGSPowerConsumer, 0);
			myBioHolder.theOGSModules.get(1).getPotableWaterConsumerDefinition().setDesiredFlowRate(2*OGSPotableWaterConsumer, 0);
			myBioHolder.theOGSModules.get(1).getO2ProducerDefinition().setDesiredFlowRate(2*OGSO2Producer, 0);
			myBioHolder.theOGSModules.get(1).getH2ProducerDefinition().setDesiredFlowRate(2*OGSH2Producer, 0);
		}
		if(Injector2active && !Injector1active && once1){
			myBioHolder.theInjectors.get(1).getO2ConsumerDefinition().setDesiredFlowRate(2*InjectorO2Consumer, 0);
			myBioHolder.theInjectors.get(1).getO2ProducerDefinition().setDesiredFlowRate(2*InjectorO2Producer, 0);
			myBioHolder.theInjectors.get(1).getCO2ConsumerDefinition().setDesiredFlowRate(2*InjectorCO2Consumer, 0);
			myBioHolder.theInjectors.get(1).getCO2ProducerDefinition().setDesiredFlowRate(2*InjectorCO2Producer, 0);
			once1=false;
	}
	if(WRS2active && !WRS1active){
			myBioHolder.theWaterRSModules.get(1).getPowerConsumerDefinition().setDesiredFlowRate(2*WaterRSPowerConsumer, 0);
			myBioHolder.theWaterRSModules.get(1).getPotableWaterProducerDefinition().setDesiredFlowRate(2*WaterRSPotableWaterConsumer , 0);
			myBioHolder.theWaterRSModules.get(1).getDirtyWaterConsumerDefinition().setDesiredFlowRate(2*WaterRSDirtyWaterConsumer, 0);
			myBioHolder.theWaterRSModules.get(1).getGreyWaterConsumerDefinition().setDesiredFlowRate(2*WaterRSGreyWaterConsumer, 0);
	}
	if(VCCR1active && !VCCR2active){
		myBioHolder.theVCCRModules.get(0).getPowerConsumerDefinition().setDesiredFlowRate(2*VCCRPowerConsumer, 0);
		myBioHolder.theVCCRModules.get(0).getAirConsumerDefinition().setDesiredFlowRate(2*VCCRAirConsumer, 0);
		myBioHolder.theVCCRModules.get(0).getAirProducerDefinition().setDesiredFlowRate(2*VCCRAirProducer, 0);
		myBioHolder.theVCCRModules.get(0).getCO2ProducerDefinition().setDesiredFlowRate(2*VCCRCO2Producer, 0);
	}
	if (OGS1active && !OGS2active){
		myBioHolder.theOGSModules.get(0).getPowerConsumerDefinition().setDesiredFlowRate(2*OGSPowerConsumer, 0);
		myBioHolder.theOGSModules.get(0).getPotableWaterConsumerDefinition().setDesiredFlowRate(2*OGSPotableWaterConsumer, 0);
		myBioHolder.theOGSModules.get(0).getO2ProducerDefinition().setDesiredFlowRate(2*OGSO2Producer, 0);
		myBioHolder.theOGSModules.get(0).getH2ProducerDefinition().setDesiredFlowRate(2*OGSH2Producer, 0);
	}
	if(Injector1active && !Injector2active && once1){
		myBioHolder.theInjectors.get(0).getO2ConsumerDefinition().setDesiredFlowRate(2*InjectorO2Consumer, 0);
		myBioHolder.theInjectors.get(0).getO2ProducerDefinition().setDesiredFlowRate(2*InjectorO2Producer, 0);
		myBioHolder.theInjectors.get(0).getCO2ConsumerDefinition().setDesiredFlowRate(2*InjectorCO2Consumer, 0);
		myBioHolder.theInjectors.get(0).getCO2ProducerDefinition().setDesiredFlowRate(2*InjectorCO2Producer, 0);
		once1=false;
	}
	if(WRS1active && !WRS2active){
		myBioHolder.theWaterRSModules.get(0).getPowerConsumerDefinition().setDesiredFlowRate(2*WaterRSPowerConsumer, 0);
		myBioHolder.theWaterRSModules.get(0).getPotableWaterProducerDefinition().setDesiredFlowRate(2*WaterRSPotableWaterConsumer , 0);
		myBioHolder.theWaterRSModules.get(0).getDirtyWaterConsumerDefinition().setDesiredFlowRate(2*WaterRSDirtyWaterConsumer, 0);
		myBioHolder.theWaterRSModules.get(0).getGreyWaterConsumerDefinition().setDesiredFlowRate(2*WaterRSGreyWaterConsumer, 0);
	}
	}
}
