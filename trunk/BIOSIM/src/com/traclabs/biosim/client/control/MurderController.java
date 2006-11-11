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
 * A controller to end and change the simulation based on gas composition.
 * @author Kirsten Stark 
 */
public class MurderController implements BiosimController {
	public static final String CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";

	private static final String LOG_FILE = "~/MurderControllerResults.txt";

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

	private int myCO2Segment1Time = 0;

	private int myCO2Segment2Time = 300;

	private int myCO2Segment3Time = 400;

	private float myCO2Segment1SetPoint = 0.08f;

	private float myCO2Segment2SetPoint = 0.08f;

	private float myCO2Segment3SetPoint = 0.08f;

	private float myCO2Segment1LowRate = 0.8f;

	private float myCO2Segment1HighRate = 0;

	private float myCO2Segment2LowRate = 0.8f;

	private float myCO2Segment2HighRate = 0;

	private float myCO2Segment3LowRate = 0.8f;

	private float myCO2Segment3HighRate = 0;

	private float myO2SetPoint = 26;

	private float myO2LowRate = 0;

	private float myO2HighRate = 100;

	private float myTotalPressureSetPoint = 101;

	private float myTotalPressureLowRate = 1;

	private float myTotalPressureHighRate = 1;

	private PrintStream myOutput;
	
	private boolean logToFile = false;

	public MurderController(boolean log) {
		logToFile = log;
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
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
		boolean logToFile = Boolean.parseBoolean(CommandLineUtils
				.getOptionValueFromArgs(args, "log"));
		MurderController myController = new MurderController(logToFile);
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
	
	private void printHeader(){
		// prints the "name" of the simulation (how much area)
		myOutput.println();
		myOutput.println();
		myOutput.println("Crop area = "+ myBioHolder.theBiomassPSModules.get(0).getShelf(0).getCropAreaUsed());
		myOutput.println();
		myOutput.println("Ticks TotalPressure O2PP CO2PP NitrogenPP VaporPP Activity");
		myOutput.flush();
	}

	/**
	 * Main loop of controller. Pauses the simulation, then ticks it one tick at
	 * a time until end condition is met.
	 */
	public void runSim() {
		printHeader();
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		
		myLogger.info("Controller starting run");
		do {
			if(cropsShouldDie())
				myBioHolder.theBiomassPSModules.get(0).killPlants();
			manipulateSim();
			myBioDriver.advanceOneTick();
			printResults();
		} while (!crewShouldDie());
		
		myBioHolder.theCrewGroups.get(0).killCrew();
		myBioDriver.endSimulation();
		
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
		myOutput.flush();
		
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

	/**
	 * Executed every tick. Looks at a sensor, looks at an actuator, then
	 * increments the actuator.
	 */
	public void manipulateSim() {
		//do nothing before tick 2
		if (myBioDriver.getTicks() < 2) {
			myAirOutActuator.setValue(0);
			myNitrogenInActuator.setValue(0);
			myO2OutActuator.setValue(0);
			myCO2InActuator.setValue(0);
			return;
		}
		if (getTotalPressure() > myTotalPressureSetPoint){
			myLogger.info(myAirOutActuator.getModuleName()+" actuating for high ("+getTotalPressure()+" > "+myTotalPressureSetPoint+"), setting to "+myTotalPressureHighRate);
			myAirOutActuator.setValue(myTotalPressureHighRate);
			myNitrogenInActuator.setValue(0);
		}
		else {
			myLogger.info(myNitrogenInActuator.getModuleName()+" actuating for low ("+getTotalPressure()+" < "+myTotalPressureSetPoint+"), setting to "+myTotalPressureLowRate);
			myNitrogenInActuator.setValue(myTotalPressureLowRate);
			myAirOutActuator.setValue(0);
		}
		
		processSegment(myCO2PressureSensor, myCO2InActuator, myCO2Segment1Time,
				myCO2Segment1SetPoint, myCO2Segment1LowRate, myCO2Segment1HighRate);
		processSegment(myCO2PressureSensor, myCO2InActuator, myCO2Segment2Time,
				myCO2Segment2SetPoint, myCO2Segment2LowRate, myCO2Segment2HighRate);
		processSegment(myCO2PressureSensor, myCO2InActuator, myCO2Segment3Time,
				myCO2Segment3SetPoint, myCO2Segment3LowRate, myCO2Segment3HighRate);
		
		processSegment(myO2PressureSensor, myO2OutActuator, -1, myO2SetPoint, myO2LowRate, myO2HighRate);
		
	}

	private float getTotalPressure() {
		return myO2PressureSensor.getValue() + myCO2PressureSensor.getValue()
		+ myNitrogenPressureSensor.getValue() + myVaporPressureSensor
		.getValue();
	}

	private void processSegment(GenericSensor sensorToProcess,
			GenericActuator actuatorToProcess, int timeForSegment, float segmentBand,
			float lowRate, float highRate) {
		if (myBioDriver.getTicks() > timeForSegment) {
			if (sensorToProcess.getValue() < segmentBand){
				myLogger.info(actuatorToProcess.getModuleName()+" actuating for low ("+sensorToProcess.getValue()+" < "+segmentBand+"), setting to "+lowRate);
				actuatorToProcess.setValue(lowRate);
			}
			else{
				myLogger.info(actuatorToProcess.getModuleName()+" actuating for high ("+sensorToProcess.getValue()+" > "+segmentBand+"), setting to "+highRate);
				actuatorToProcess.setValue(highRate);
			}
		}
	}

	public void printResults() {
		FileOutputStream out; 
		PrintStream myOutput; 
		try {
			out = new FileOutputStream("Worker_result.txt", true);
			myOutput = new PrintStream(out);
			myOutput.println();
			myOutput.println("Crop area = "+ myBioHolder.theBiomassPSModules.get(0).getShelf(0).getCropAreaUsed());
			myOutput.println("Ticks TotalPressure O2PP CO2PP NitrogenPP VaporPP Activity");
			myOutput.print(myBioDriver.getTicks() + "     ");// Ticks
			myOutput.print(myO2PressureSensor.getValue()
					+ myCO2PressureSensor.getValue()
					+ myNitrogenPressureSensor.getValue()
					+ myVaporPressureSensor.getValue() + "     ");// Total
			// Pressure
			myOutput.print(myO2PressureSensor.getValue() + "  ");// PP of O2
			myOutput.print(myCO2PressureSensor.getValue() + "  "); // PP of CO2
			myOutput.print(myNitrogenPressureSensor.getValue() + "    "); // PP of
			// Nitrogen
			myOutput.print(myVaporPressureSensor.getValue() + "   "); // PP of Vapor
			myOutput.print(myCrewPerson.getCurrentActivity().getName() + "       ");
			myOutput.print(myBioHolder.theBiomassPSModules.get(0).getShelf(0)
					.getPlant().getMolesOfCO2Inhaled());
			if (myBioDriver.getTicks() > 480) {
				myOutput.print("      " + myCrewPerson.getCO2Produced());
			}
			myOutput.println();
			myOutput.flush();
		} 
		catch (FileNotFoundException e) {
					e.printStackTrace();
		}
	} 

	public void setCO2InActuator(GenericActuator myCO2InActuator) {
		this.myCO2InActuator = myCO2InActuator;
	}

	public void setCO2PressureSensor(GenericSensor myCO2PressureSensor) {
		this.myCO2PressureSensor = myCO2PressureSensor;
	}

	public void setCO2Segment1HighRate(float myCO2Segment1HighRate) {
		this.myCO2Segment1HighRate = myCO2Segment1HighRate;
	}

	public void setCO2Segment1LowRate(float myCO2Segment1LowRate) {
		this.myCO2Segment1LowRate = myCO2Segment1LowRate;
	}

	public void setCO2Segment1SetPoint(float myCO2Segment1SetPoint) {
		this.myCO2Segment1SetPoint = myCO2Segment1SetPoint;
	}

	public void setCO2Segment1Time(int myCO2Segment1Time) {
		this.myCO2Segment1Time = myCO2Segment1Time;
	}

	public void setCO2Segment2HighRate(float myCO2Segment2HighRate) {
		this.myCO2Segment2HighRate = myCO2Segment2HighRate;
	}

	public void setCO2Segment2LowRate(float myCO2Segment2LowRate) {
		this.myCO2Segment2LowRate = myCO2Segment2LowRate;
	}

	public void setCO2Segment2SetPoint(float myCO2Segment2SetPoint) {
		this.myCO2Segment2SetPoint = myCO2Segment2SetPoint;
	}

	public void setCO2Segment2Time(int myCO2Segment2Time) {
		this.myCO2Segment2Time = myCO2Segment2Time;
	}

	public void setCO2Segment3HighRate(float myCO2Segment3HighRate) {
		this.myCO2Segment3HighRate = myCO2Segment3HighRate;
	}

	public void setCO2Segment3LowRate(float myCO2Segment3LowRate) {
		this.myCO2Segment3LowRate = myCO2Segment3LowRate;
	}

	public void setCO2Segment3SetPoint(float myCO2Segment3SetPoint) {
		this.myCO2Segment3SetPoint = myCO2Segment3SetPoint;
	}

	public void setCO2Segment3Time(int myCO2Segment3Time) {
		this.myCO2Segment3Time = myCO2Segment3Time;
	}

	public void setO2HighRate(float myO2HighRate) {
		this.myO2HighRate = myO2HighRate;
	}

	public void setO2LowRate(float myO2LowRate) {
		this.myO2LowRate = myO2LowRate;
	}

	public void setO2SetPoint(float myO2SetPoint) {
		this.myO2SetPoint = myO2SetPoint;
	}

	public void setTotalPressureHighRate(float myTotalPressureHighRate) {
		this.myTotalPressureHighRate = myTotalPressureHighRate;
	}

	public void setTotalPressureLowRate(float myTotalPressureLowRate) {
		this.myTotalPressureLowRate = myTotalPressureLowRate;
	}

	public void setTotalPressureSetPoint(float myTotalPressureSetPoint) {
		this.myTotalPressureSetPoint = myTotalPressureSetPoint;
	}
}
