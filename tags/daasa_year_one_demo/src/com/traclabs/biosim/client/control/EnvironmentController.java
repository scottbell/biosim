package com.traclabs.biosim.client.control;

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
import com.traclabs.biosim.util.MersenneTwister;

public class EnvironmentController extends MurderController implements
		BiosimController {
	private final static String DEFAULT_CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";
	private final static String DEFAULT_LOG_FILE = "EnvironmentController.log";

	private BioDriver myBioDriver;

	protected BioHolder myBioHolder;

	private Logger myLogger = Logger.getLogger(EnvironmentController.class);

	private CrewPerson myCrewPerson;

	private SimEnvironment myCrewEnvironment;

	private GenericSensor myO2PressureSensor;

	private GenericSensor myCO2PressureSensor;

	private GenericSensor myNitrogenPressureSensor;

	private GenericSensor myVaporPressureSensor;

	private GenericSensor myTimeTillCanopyClosureSensor;

	private GenericActuator myAirOutActuator;

	private GenericActuator myNitrogenInActuator;

	private GenericActuator myCO2InActuator;

	private GenericActuator myO2OutActuator;

	private static final long seed =  System.currentTimeMillis();
	
	private MersenneTwister randomNumberGenerator = new MersenneTwister();

	private int myCO2Segment1Time = (int) (100 * randomNumberGenerator
			.nextDouble());

	private int myCO2Segment2Time = (int) (100 + 200 * randomNumberGenerator
			.nextDouble());

	private int myCO2Segment3Time = (int) (300 + 100 * randomNumberGenerator
			.nextDouble());

	private int ArrivalTime = (int) (480 * randomNumberGenerator.nextDouble());

	private float myCO2Segment1SetPoint = (float) (0.01 + 0.09 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment2SetPoint = (float) (0.01 + 0.09 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment3SetPoint = (float) (0.01 + 0.09 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment1LowRate = (float) (2 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment1HighRate = (float) (1 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment2LowRate = (float) (2 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment2HighRate = (float) (1 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment3LowRate = (float) (2 * randomNumberGenerator
			.nextDouble());

	private float myCO2Segment3HighRate = (float) (1 * randomNumberGenerator
			.nextDouble());

	private float myO2SetPoint = (float) (20 + 10 * randomNumberGenerator
			.nextDouble());

	private float myO2LowRate = (float) (10 * randomNumberGenerator
			.nextDouble());

	private float myO2HighRate = (float) (90 + 10 * randomNumberGenerator
			.nextDouble());

	private float myTotalPressureSetPoint = 101;

	private float myTotalPressureLowRate = (float) (2 * randomNumberGenerator
			.nextDouble());

	private float myTotalPressureHighRate = (float) (2 * randomNumberGenerator
			.nextDouble());

	private float myCropArea = (float) (300 * randomNumberGenerator
			.nextDouble());
	
	public EnvironmentController(String configurationFileName, String logFileName) {
		super(configurationFileName, logFileName);
	}
	
	public EnvironmentController() {
		super(DEFAULT_CONFIGURATION_FILE, DEFAULT_LOG_FILE);
	}

	public static void main(String[] args) {
		EnvironmentController myController = new EnvironmentController();
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
		super.collectReferences();
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
		myCrewEnvironment = myBioHolder.theSimEnvironments.get(0);
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

		myO2PressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, myCrewEnvironment
						.getO2Store());
		myCO2PressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, myCrewEnvironment
						.getCO2Store());
		myNitrogenPressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, myCrewEnvironment
						.getNitrogenStore());
		myVaporPressureSensor = myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasPressureSensors, myCrewEnvironment
						.getVaporStore());
		myTimeTillCanopyClosureSensor = myBioHolder.getShelfSensorAttachedTo(
				myBioHolder.theTimeTillCanopyClosureSensors,
				myBioHolder.theBiomassPSModules.get(0), 0);

	}

	/**
	 * Executed every tick. Looks at a sensor, looks at an actuator, then
	 * increments the actuator.
	 */
	public void doTickContent() {
		super.doTickContent();
		// do nothing before tick 2
		if (myBioDriver.getTicks() < 2) {
			myAirOutActuator.setValue(0);
			myNitrogenInActuator.setValue(0);
			myO2OutActuator.setValue(0);
			myCO2InActuator.setValue(0);
			return;
		}
		if (getTotalPressure() > myTotalPressureSetPoint) {
			myLogger.debug(myAirOutActuator.getModuleName()
					+ " actuating for high (" + getTotalPressure() + " > "
					+ myTotalPressureSetPoint + "), setting to "
					+ myTotalPressureHighRate);
			myAirOutActuator.setValue(myTotalPressureHighRate);
			myNitrogenInActuator.setValue(0);
		} else {
			myLogger.debug(myNitrogenInActuator.getModuleName()
					+ " actuating for low (" + getTotalPressure() + " < "
					+ myTotalPressureSetPoint + "), setting to "
					+ myTotalPressureLowRate);
			myNitrogenInActuator.setValue(myTotalPressureLowRate);
			myAirOutActuator.setValue(0);
		}

		processSegment(myCO2PressureSensor, myCO2InActuator, myCO2Segment1Time,
				myCO2Segment1SetPoint, myCO2Segment1LowRate,
				myCO2Segment1HighRate);
		processSegment(myCO2PressureSensor, myCO2InActuator, myCO2Segment2Time,
				myCO2Segment2SetPoint, myCO2Segment2LowRate,
				myCO2Segment2HighRate);
		processSegment(myCO2PressureSensor, myCO2InActuator, myCO2Segment3Time,
				myCO2Segment3SetPoint, myCO2Segment3LowRate,
				myCO2Segment3HighRate);

		processSegment(myO2PressureSensor, myO2OutActuator, -1, myO2SetPoint,
				myO2LowRate, myO2HighRate);
		logResults();
	}

	public void logResults() {
		myOutput.println();
		myOutput
				.println("Ticks TotalPressure O2PP CO2PP NitrogenPP VaporPP Activity");
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
		myOutput.print(myVaporPressureSensor.getValue() + "   "); // PP of
																	// Vapor
		myOutput.print(myCrewPerson.getCurrentActivity().getName() + "       ");
		myOutput.print(myBioHolder.theBiomassPSModules.get(0).getShelf(0)
				.getPlant().getMolesOfCO2Inhaled());
		myOutput.println();
		myOutput.flush();
	}

	private float getTotalPressure() {
		return myO2PressureSensor.getValue() + myCO2PressureSensor.getValue()
				+ myNitrogenPressureSensor.getValue()
				+ myVaporPressureSensor.getValue();
	}

	private void processSegment(GenericSensor sensorToProcess,
			GenericActuator actuatorToProcess, int timeForSegment,
			float segmentBand, float lowRate, float highRate) {
		if (myBioDriver.getTicks() > timeForSegment) {
			if (sensorToProcess.getValue() < segmentBand) {
				myLogger.debug(actuatorToProcess.getModuleName()
						+ " actuating for low (" + sensorToProcess.getValue()
						+ " < " + segmentBand + "), setting to " + lowRate);
				actuatorToProcess.setValue(lowRate);
			} else {
				myLogger.debug(actuatorToProcess.getModuleName()
						+ " actuating for high (" + sensorToProcess.getValue()
						+ " > " + segmentBand + "), setting to " + highRate);
				actuatorToProcess.setValue(highRate);
			}
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
