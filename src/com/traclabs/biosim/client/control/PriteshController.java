package com.traclabs.biosim.client.control;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.idl.simulation.water.WaterRS;
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

public class PriteshController implements BiosimController{
	private static String CONFIGURATION_FILE = "test/pritesh.biosim";

	private BioDriver myBioDriver;

	private BioHolder myBioHolder;

	private Logger myLogger;

	private GenericSensor myO2ConcentrationSensor;
	
	private GenericSensor myDeathSensor;

	private GenericActuator myO2InjectorAcutator;

	public PriteshController() {
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
	}

	public static void main(String[] args) {
		PriteshController myController = new PriteshController();
		myController.collectReferences();
		myController.runSim();
	}

	/**
	 * Collects references to BioModules we'll need
	 * to run/observer/poke the sim.  The BioHolder is 
	 * a utility for clients to easily access different parts 
	 * of BioSim.
	 *
	 */
	public void collectReferences() {
		BioHolderInitializer.setFile(CONFIGURATION_FILE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;

		Injector O2Injector = myBioHolder.theInjectors.get(0);

		myO2InjectorAcutator = (myBioHolder.getActuatorAttachedTo(
				myBioHolder.theO2InFlowRateActuators, O2Injector));

		SimEnvironment crewEnvironment = myBioHolder.theSimEnvironments.get(0);

		myO2ConcentrationSensor = (myBioHolder.getSensorAttachedTo(
				myBioHolder.theGasConcentrationSensors, crewEnvironment
						.getO2Store()));
		myDeathSensor = myBioHolder.getSensorAttachedTo(myBioHolder.theCrewGroupAnyDeadSensors, myBioHolder.theCrewGroups.get(0));
		
		
		
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
		int waterSystemsFailed=0;
		for (WaterRS waterSystem : myBioHolder.theWaterRSModules) 
		{
			
			if(waterSystem.isMalfunctioning())
				waterSystemsFailed++;
			
			
		}
		float percentageFailed = waterSystemsFailed/myBioHolder.theWaterRSModules.size();
		boolean tooManyWaterSystemsFailed = percentageFailed>0.75;
		boolean crewDead = myDeathSensor.getValue()==1;
		
		return (crewDead || tooManyWaterSystemsFailed);
	}

	/**
	 * Executed every tick.  Looks at a sensor, looks at an actuator,
	 * then increments the actuator.
	 */
	public void stepSim() {
		//check sensor
		float sensorValue = myO2ConcentrationSensor.getValue();
		
		myLogger.info("sensor reading is " + sensorValue);
		//set actuator
		
		//float actuatorValue = myO2InjectorAcutator.getValue();
		myLogger.info("actuator set at " + myO2InjectorAcutator.getValue());
		
		if(sensorValue<0.3)
		{
			myLogger.info("Turning On Actuator");
			myO2InjectorAcutator.setValue(10);
		}
		
		else
		{
			myLogger.info("Turning Off Injector");
			myO2InjectorAcutator.setValue(0);
		}
		
		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
	}

}
