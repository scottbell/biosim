package com.traclabs.biosim.client.control;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Injector;

/**
 * @author Scott Bell
 * A simple, yet ineffective controller
 */

public class IllinoisController {

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;

	private GenericSensor myO2ConcentrationSensor;

	private GenericActuator myO2InjectorAcutator;

	public IllinoisController() {
		myLogger = Logger.getLogger(this.getClass());
		collectReferences();
	}

	public static void main(String[] args) {
		IllinoisController myController = new IllinoisController();
		myController.runSim();
	}

	private void collectReferences() {
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;

		Injector O2Injector = myBioHolder.theInjectors.get(1);

		myO2InjectorAcutator = (myBioHolder.getActuatorAttachedTo(
				myBioHolder.theO2InFlowRateActuators, O2Injector));

		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);

		myO2ConcentrationSensor = (myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasConcentrationSensors, crewEnvironment
						.getO2Store()));
	}

	public void runSim() {
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		myLogger.info("Controller starting run");
		while (!myBioDriver.isDone())
			stepSim();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
	}

	public void stepSim() {
		//check sensor
		float sensorValue = myO2ConcentrationSensor.getValue();
		myLogger.info("sensor reading is " + sensorValue);
		//set actuator
		myLogger.info("incrementing actuator by 1");
		float actuatorValue = myO2InjectorAcutator.getValue();
		myLogger.info("actuator set at " + myO2InjectorAcutator.getValue());
		myO2InjectorAcutator.setValue(actuatorValue + 1);
		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
	}

}
