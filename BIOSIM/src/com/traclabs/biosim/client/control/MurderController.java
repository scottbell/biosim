package com.traclabs.biosim.client.control;

import org.apache.log4j.Logger;

import java.io.*;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.OrbUtils;
import com.traclabs.biosim.idl.simulation.crew.*;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.framework.*;

/**
 * @author Kirsten Stark
 * A controller to end change the simulation based on air make-up
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
 
public class MurderController implements BiosimController {
	private static String CONFIGURATION_FILE = "kirsten/MurderControllerInit.xml";

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
	
	FileOutputStream out;
	
	PrintStream p;

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
		crewEnvironment = myBioHolder.theSimEnvironments.get(0);
		myBioDriver = myBioHolder.theBioDriver;
		myCrewPerson = myBioHolder.theCrewGroups.get(0).getCrewPerson("Nigil");
		
		Injector NitrogenInjector = myBioHolder.theInjectors.get(0);
		Injector CO2Injector = myBioHolder.theInjectors.get(1);
		Accumulator AirAccumulator = myBioHolder.theAccumulators.get(0);
		
		myNitrogenInActuator = myBioHolder.getActuatorAttachedTo(myBioHolder.theNitrogenInFlowRateActuators, NitrogenInjector);
		myAirOutActuator = myBioHolder.getActuatorAttachedTo(myBioHolder.theAirInFlowRateActuators, AirAccumulator);
		myCO2InActuator = myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2InFlowRateActuators,CO2Injector);
		
		myO2PressureSensor = myBioHolder.getSensorAttachedTo(myBioHolder.theGasPressureSensors, crewEnvironment.getO2Store());
		myCO2PressureSensor = myBioHolder.getSensorAttachedTo(myBioHolder.theGasPressureSensors, crewEnvironment.getCO2Store());
		myNitrogenPressureSensor = myBioHolder.getSensorAttachedTo(myBioHolder.theGasPressureSensors, crewEnvironment.getNitrogenStore());
		myVaporPressureSensor = myBioHolder.getSensorAttachedTo(myBioHolder.theGasPressureSensors, crewEnvironment.getVaporStore());
		myTimeTillCanopyClosureSensor = myBioHolder.getShelfSensorAttachedTo(myBioHolder.theTimeTillCanopyClosureSensors, myBioHolder.theBiomassPSModules.get(0), 0);
		
	} 
	/**
	 * Main loop of controller.  Pauses the simulation, then
	 * ticks it one tick at a time until end condition is met.
	 */
	public void runSim() {
		
		//prints the "name" of the simulation (how much area)
		try {
			out = new FileOutputStream("/home/kirsten/MurderControllerResults.txt", true);
			p = new PrintStream( out );
			p.println();
			p.println();
			p.print("Crop area = " + myBioHolder.theBiomassPSModules.get(0).getShelf(0).getCropAreaUsed());
			p.println();
			p.println("Ticks TotalPressure O2PP CO2PP NitrogenPP VaporPP Activity");
			p.flush();
			out.close();
		}catch (Exception e){
			System.err.println("Error writing to file.");
		}
		
		myBioDriver.setPauseSimulation(true);
		myBioDriver.startSimulation();
		myLogger.info("Controller starting run");
		crewEnvironment.setCurrentVolumeAtSeaLevel(32000);
		myLogger.info("The value of O2 is " + myO2PressureSensor.getValue());
		myLogger.info("The time till canopy closure is " + myTimeTillCanopyClosureSensor.getValue());
		//myCrewPerson.setArrivalTick(24*(int)myTimeTillCanopyClosureSensor.getValue());
		//printResults(); //prints the initial conditions
		do {
			stepSim();  
		}while (!endConditionMet());
		
		//if we get here, the end condition has been met
		myLogger.info("Final O2PartialPressure= "+myO2PressureSensor.getValue()+ " Final CO2PartialPressure= "+ myCO2PressureSensor.getValue());
		myBioDriver.endSimulation();
		myLogger.info("Controller ended on tick " + myBioDriver.getTicks());
	} 
	
	private boolean endConditionMet() {
		
		if((myO2PressureSensor.getValue() < 10.13) || (myO2PressureSensor.getValue() > 30.39) || (myCO2PressureSensor.getValue() > 1))	{
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
	
		
		//CO2 controls		
		if((myCO2PressureSensor.getValue() < .1) && (myCO2PressureSensor.getValue() > .05) && (myBioDriver.getTicks() < myCrewPerson.getArrivalTick()))	{
			myCO2InActuator.setValue(20);
			}
		
		if((myCO2PressureSensor.getValue() < .05) && (myBioDriver.getTicks() < myCrewPerson.getArrivalTick()))	{
			myCO2InActuator.setValue(40);
		}
		
		if (!(myCO2PressureSensor.getValue() < .1) && (myBioDriver.getTicks() < myCrewPerson.getArrivalTick()))	{
			myCO2InActuator.setValue(0);
		}
		
		if (myBioDriver.getTicks() > myCrewPerson.getArrivalTick()){
			myCO2InActuator.setValue(0);
		}
		
		if((myCO2PressureSensor.getValue() < 0.033) || (myCO2PressureSensor.getValue() > .2))	{
			myBioHolder.theBiomassPSModules.get(0).killPlants();
			myLogger.info("The crops have died from " + myCO2PressureSensor.getValue() + " CO2 on tick " + myBioDriver.getTicks());
		}
		
		
		
		//TotalPressure controls 
		
		if ((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) > 106) {
			myAirOutActuator.setValue(60);
		}
		
		if ((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) < 96)	{
			myNitrogenInActuator.setValue(60);
		}
		
		if (((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) > 96) && ((myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue()) < 106))	{
			myAirOutActuator.setValue(0);
			myNitrogenInActuator.setValue(0);
		
		}  
		
		// advancing the sim 1 tick
		myBioDriver.advanceOneTick();
		printResults();
		return;
	
		
		
	}
	public void printResults()	{
		//TotalPressure = myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue();
	
		try {
			out = new FileOutputStream("/home/kirsten/MurderControllerResults.txt", true);
			p = new PrintStream( out );
			p.print(myBioDriver.getTicks() + "     ");//Ticks
			p.print(myO2PressureSensor.getValue() + myCO2PressureSensor.getValue() + myNitrogenPressureSensor.getValue() + myVaporPressureSensor.getValue() + "     ");//Total Pressure
			p.print(myO2PressureSensor.getValue() + "  ");//PP of O2
			p.print(myCO2PressureSensor.getValue() + "  "); //PP of CO2
			p.print(myNitrogenPressureSensor.getValue() + "    "); //PP of Nitrogen
			p.print(myVaporPressureSensor.getValue() + "   "); //PP or Vapor
			p.print(myCrewPerson.getCurrentActivity().getName() + "       ");
			//p.print(myCrewPerson.getO2Consumed() + " " + myCrewPerson.getCO2Produced() + "         ");
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