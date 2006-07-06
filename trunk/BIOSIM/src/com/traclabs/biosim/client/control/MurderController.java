package com.traclabs.biosim.client.control;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.util.OrbUtils;

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

	private GenericSensor myO2ConcentrationSensor;

	private GenericSensor myCO2ConcentrationSensor;

	private GenericActuator myO2InjectorAcutator;

	private GenericActuator myCO2InjectorActuator;

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
		
		boolean noInjectors = myBioHolder.theInjectors.isEmpty();
		if (noInjectors){
			myLogger.info("The Injector List is Empty.");
		}
		if (!noInjectors){
			myLogger.info("The Injector List is not Empty!");
			int size = myBioHolder.theInjectors.size();
			myLogger.info("The list has " + size + " elements.");
		}

		Injector O2Injector = myBioHolder.theInjectors.get(0);
		String name = O2Injector.getModuleName();
		myLogger.info("The name of the first module is "+ name +".");
		
		Injector CarbonDioxideInjector = myBioHolder.theInjectors.get(1);
		String cname = CarbonDioxideInjector.getModuleName();
		myLogger.info("The name of the second module is "+cname+".");

		myO2InjectorAcutator = (myBioHolder.getActuatorAttachedTo(
				myBioHolder.theO2InFlowRateActuators, O2Injector));
		
		myCO2InjectorActuator = (myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2InFlowRateActuators, CarbonDioxideInjector));

		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);

		myO2ConcentrationSensor = (myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasConcentrationSensors, crewEnvironment
						.getO2Store()));
		myCO2ConcentrationSensor = (myBioHolder.getSensorAttachedTo(myBioHolder.theGasConcentrationSensors, crewEnvironment.getCO2Store()));
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
		myBioDriver.endSimulation();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
	}
	
	/**
	 * If the oxygen in the cabin drifts below 10%, stop the sim.
	 */
	private boolean endConditionMet() {
		float oxygenPercentage = myO2ConcentrationSensor.getValue();
		float CO2Percentage = myCO2ConcentrationSensor.getValue();
		if ((oxygenPercentage < 0.10) || (oxygenPercentage > 0.30) || (CO2Percentage > .06))	{	
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
		//check sensor
		float O2sensorValue = myO2ConcentrationSensor.getValue();
		float CO2sensorValue = myCO2ConcentrationSensor.getValue();
		myLogger.info("O2 Sensor value is " + O2sensorValue +" and CO2 sensor value is "+ CO2sensorValue +".");
		//set actuator
		myLogger.info("incrementing actuator by 1");
		float O2actuatorValue = myO2InjectorAcutator.getValue();
		float CO2actuatorValue =myCO2InjectorActuator.getValue();
		myLogger.info("O2 actuator set at " + O2actuatorValue + " and CO2 actuator set at "+ CO2actuatorValue+".");
		myO2InjectorAcutator.setValue(O2actuatorValue + 1);
		myCO2InjectorActuator.setValue(CO2actuatorValue +1);
		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
	}

}
