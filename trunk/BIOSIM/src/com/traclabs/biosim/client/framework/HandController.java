
package biosim.client.framework;
import java.util.*;
import java.io.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import biosim.idl.framework.BioDriver;
import biosim.idl.framework.BioDriverHelper;
import biosim.idl.framework.BioModule;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.water.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.framework.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.water.*;
import biosim.client.util.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.framework.*;

/**
 * @author    Theresa Klein
 To compile:
 1) run make-client.sh
 2) javac -classpath .:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/generated/client/classes HandController.java
 
 javac - the compiler
 jacorb.jar - the library that has the ORB and various CORBA utilities
 generated/client/classes - the generated client stubs
 HandController - this file

 
 To run:
 1)run run-nameserver.sh
 2)run run-server.sh
 3)java -classpath .:$BIOSIM_HOME/resources:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME/generated/client/classes:$BIOSIM_HOME/lib/xerces/xmlParserAPIs.jar:$BIOSIM_HOME/lib/xerces/xml-apis.jar:$BIOSIM_HOME/lib/xerces/xercesImpl.jar -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton -DORBInitRef.NameService=file:$BIOSIM_HOME/generated/ns/ior.txt HandController
 (all the above on one line)
 
 -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB - overriding Sun's default ORB (using Jacorb instead)
 -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton - overriding Sun's default ORB (using Jacorb instead)
 -DORBInitRef.NameService=file:$BIOSIM_HOME/generated/ns/ior.txt - telling the client where to look for the ior (serialized nameservice object, produced by run-nameserver.sh)
 */

public class HandController{
	double DirtyWaterLowLevel = 100f;
	double DirtyWaterHighLevel = 400f;
	double GreyWaterLowLevel = 100f;
	double GreyWaterHighLevel = 400f;
	double PotableWaterLowLevel = 100f;
	double PotableWaterHighLevel = 400f;
	double BiomassLowLevel = 100f;
	double BiomassHighLevel = 400f;
	double FoodLowLevel = 100f;
	double FoodHighLevel = 400f;
	double PowerLowLevel = 2000f;
	double PowerHighLevel = 4500f;
	double O2StoreLowLevel = 300f;
	double O2StoreHighLevel = 800f;
	double CO2StoreLowLevel = 300f;
	double CO2StoreHighLevel = 800f;
	double H2StoreLowLevel = 1000f;
	double H2StoreHighLevel = 4000f;

	double CrewO2Level = .20;
	double CrewCO2Level = 0.02;
	double PlantO2Level =  .2;
	double PlantCO2Level = .02;
	double crewO2integral, crewCO2integral, plantO2integral, plantCO2integral;

	float co2_overflow; 

	File OutFile = new File("output.txt");
	FileWriter fw;
	PrintWriter pw;


	TreeMap SimState = new TreeMap();

	BioDriver myBioDriver;
	BioHolder myBioHolder;

	public static void main(String[] args){

		HandController myController = new HandController();
	
		myController.runSim();
	}

	public void runSim(){
		GenericActuator currentActuator;
		int i;

		try { 	fw = new FileWriter(OutFile) ; }
		catch (IOException e) {}
		pw = new PrintWriter(fw, true);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioDriver = myBioHolder.theBioDriver;
		
		//myBioDriver.setFullLogging(true);
		myBioDriver.startSimulation();
		BiomassRS myBiomassRS = (BiomassRS)(myBioHolder.theBiomassRSModules.get(0));  
		myBiomassRS.setAutoHarvestAndReplantEnabled(false); 
		CrewGroup myCrew = (CrewGroup)myBioHolder.theCrewGroups.get(0);
		WaterRS myWaterRS = (WaterRS)myBioHolder.theWaterRSModules.get(0);
		AirRS myAirRS = (AirRS)myBioHolder.theAirRSModules.get(0);
		//supply power
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators, myWaterRS));
		currentActuator.setValue(500);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators, myAirRS));
		currentActuator.setValue(300);
		//turn vccr off 
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theAirInFlowRateActuators, myAirRS));
		currentActuator.setValue(0);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2OutFlowRateActuators, myAirRS));
		currentActuator.setValue(0);
		//set values for other inputs and outputs
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theH2InFlowRateActuators, myAirRS));
		currentActuator.setValue(40);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePotableWaterOutFlowRateActuators, myAirRS));
		currentActuator.setValue(20);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePotableWaterOutFlowRateActuators, myWaterRS));
		currentActuator.setValue(10);
		// initialize everything to off
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2InFlowRateActuators, myAirRS));
		currentActuator.setValue(0);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePotableWaterInFlowRateActuators, myAirRS));
		currentActuator.setValue(0);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theDirtyWaterInFlowRateActuators, myWaterRS));
		currentActuator.setValue(0);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theGreyWaterInFlowRateActuators, myWaterRS));
		currentActuator.setValue(0);

		System.out.println("starting run...");
		crewO2integral = 0;
		crewCO2integral = 0;
		plantO2integral = 0;
		plantCO2integral = 0;
		//		CrewO2Level = ((GenericSensor)(BioHolder.getBioModule(BioHolder.myCrewEnvironmentO2AirConcentrationSensorName))).getValue();
		//		CrewCO2Level = ((GenericSensor)(BioHolder.getBioModule(BioHolder.myCrewEnvironmentCO2AirConcentrationSensorName))).getValue();
		//		PlantO2Level = ((GenericSensor)(BioHolder.getBioModule(BioHolder.myPlantEnvironmentO2AirConcentrationSensorName))).getValue();
		//		PlantCO2Level = ((GenericSensor)(BioHolder.getBioModule(BioHolder.myPlantEnvironmentCO2AirConcentrationSensorName))).getValue(); ;
		while (!myCrew.isDead()) {
			// advance 10 ticks

			for (i=0;i<10; i++) 
				myBioDriver.advanceOneTick();

			
			// collect data on the states, using the sensors
			System.out.println("Checking Sensors...");
			setSimState();
			// change actuator settings in reponse
			setActuators();

		}
		System.out.println("crew dead at "+myBioDriver.getTicks()+" ticks");
		try {fw.close();} catch (IOException e) {}
	}


	public void setSimState() {
		String fileoutput;
		fileoutput = myBioDriver.getTicks()+"   ";
		DirtyWaterStore myDirtyWaterStore = (DirtyWaterStore)myBioHolder.theDirtyWaterStores.get(0);
		GenericSensor currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theDirtyWaterStoreLevelSensors, myDirtyWaterStore));
		if (currentSensor.getValue() < DirtyWaterLowLevel)
			SimState.put("dirtywater", "low");
		else if (currentSensor.getValue() > DirtyWaterHighLevel)
			SimState.put("dirtywater", "high");
		else SimState.put("dirtywater", "normal");
		System.out.println("Dirty water..."+currentSensor.getValue()+"..."+SimState.get("dirtywater"));
		fileoutput += currentSensor.getValue()+"   ";

		GreyWaterStore myGreyWaterStore = (GreyWaterStore)myBioHolder.theGreyWaterStores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theGreyWaterStoreLevelSensors, myGreyWaterStore));
		if (currentSensor.getValue() < GreyWaterLowLevel)
			SimState.put("greywater", "low");
		else if (currentSensor.getValue() > GreyWaterHighLevel)
			SimState.put("greywater", "high");
		else SimState.put("greywater", "normal");
		System.out.println("Grey water..."+currentSensor.getValue()+"..."+SimState.get("greywater"));
		fileoutput += currentSensor.getValue()+"   ";
		
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)myBioHolder.thePotableWaterStores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.thePotableWaterStoreLevelSensors, myPotableWaterStore));
		if (currentSensor.getValue() < PotableWaterLowLevel)
			SimState.put("potablewater", "low");
		else if (currentSensor.getValue() > PotableWaterHighLevel)
			SimState.put("potablewater", "high");
		else SimState.put("potablewater", "normal");

		System.out.println("Potable water..."+currentSensor.getValue()+"..."+SimState.get("potablewater"));
		fileoutput += currentSensor.getValue()+"   ";
		
		BiomassStore myBiomassStore = (BiomassStore)myBioHolder.theBiomassStores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theBiomassStoreLevelSensors, myBiomassStore));
		if (currentSensor.getValue() < BiomassLowLevel)
			SimState.put("biomass", "low");
		else if (currentSensor.getValue() > BiomassHighLevel)
			SimState.put("biomass", "high");
		else SimState.put("biomass", "normal");
		System.out.println("Biomass..."+currentSensor.getValue()+"..."+SimState.get("biomass"));
		fileoutput += currentSensor.getValue()+"   ";

		FoodStore myFoodStore = (FoodStore)myBioHolder.theFoodStores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theFoodStoreLevelSensors, myFoodStore));
		if (currentSensor.getValue() < FoodLowLevel)
			SimState.put("food", "low");
		else if (currentSensor.getValue() > FoodHighLevel)
			SimState.put("food", "high");
		else SimState.put("food", "normal");
		System.out.println("Food ..."+currentSensor.getValue()+"..."+SimState.get("food"));
		fileoutput += currentSensor.getValue()+"   ";

		PowerStore myPowerStore = (PowerStore)myBioHolder.thePowerStores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.thePowerStoreLevelSensors, myPowerStore));
		if (currentSensor.getValue() < PowerLowLevel)
			SimState.put("power", "low");
		else if (currentSensor.getValue() > PowerHighLevel)
			SimState.put("power", "high");
		else SimState.put("power", "normal");
		System.out.println("Power..."+currentSensor.getValue()+"..."+SimState.get("power"));
		fileoutput += currentSensor.getValue()+"   ";

		O2Store myO2Store = (O2Store)myBioHolder.theO2Stores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theO2StoreLevelSensors, myO2Store));
		if (currentSensor.getValue() < O2StoreLowLevel)
			SimState.put("oxygen", "low");
		else if (currentSensor.getValue() > O2StoreHighLevel)
			SimState.put("oxygen", "high");
		else SimState.put("oxygen", "normal");
		System.out.println("Oxygen..."+currentSensor.getValue()+"..."+SimState.get("oxygen"));

		fileoutput += currentSensor.getValue()+"   ";

		
		CO2Store myCO2Store = (CO2Store)myBioHolder.theCO2Stores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theCO2StoreLevelSensors, myCO2Store));
		if (currentSensor.getValue() < CO2StoreLowLevel)
			SimState.put("carbondioxyide", "low");
		else if (currentSensor.getValue() > CO2StoreHighLevel)
			SimState.put("carbondioxide", "high");
		else SimState.put("carbondioxide", "normal");
		System.out.println("Carbon Dioxide..."+currentSensor.getValue()+"..."+SimState.get("carbondioxide"));
		fileoutput += currentSensor.getValue()+"   ";

		
		H2Store myH2Store = (H2Store)myBioHolder.theH2Stores.get(0);
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theH2StoreLevelSensors, myH2Store));
		if (currentSensor.getValue() < H2StoreLowLevel)
			SimState.put("hydrogen", "low");
		else if (currentSensor.getValue() > H2StoreHighLevel)
			SimState.put("hydrogen", "high");
		else SimState.put("hydrogen", "normal");
		System.out.println("Hydrogen..."+currentSensor.getValue()+"..."+SimState.get("hydrogen"));

		fileoutput += currentSensor.getValue()+"   ";


		SimEnvironment myCrewEnvironment = (SimEnvironment)myBioHolder.theSimEnvironments.get(0);
		SimEnvironment myPlantEnvironment = (SimEnvironment)myBioHolder.theSimEnvironments.get(1);
		
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theO2AirConcentrationSensors, myCrewEnvironment));
		System.out.println("Crew O2..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theCO2AirConcentrationSensors, myCrewEnvironment));
		System.out.println("Crew CO2..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";
		
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theWaterAirConcentrationSensors, myCrewEnvironment));
		System.out.println("Crew Water Vapor..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";


		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theO2AirConcentrationSensors, myPlantEnvironment));
		System.out.println("Plant O2..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";


		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theCO2AirConcentrationSensors, myPlantEnvironment));
		System.out.println("Plant CO2..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";
		
		
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theWaterAirConcentrationSensors, myPlantEnvironment));
		System.out.println("Plant Water Vapor..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";
		
		currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theWaterAirPressureSensors, myPlantEnvironment));
		System.out.println("Plant Water Vapor Pressure..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";


		/*		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myCrewGroupPotableWaterInFlowRateSensorName));
				System.out.println("Crew Water In FlowRate..."+currentSensor.getValue());
				fileoutput += currentSensor.getValue()+"   ";

				currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myWaterRSDirtyWaterInFlowRateSensorName));
				System.out.println("Dirty WaterRS In FlowRate..."+currentSensor.getValue());
				fileoutput += currentSensor.getValue()+"   ";

				currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myWaterRSPotableWaterOutFlowRateSensorName));
				System.out.println("Potable WaterRS Out FlowRate..."+currentSensor.getValue());
				fileoutput += currentSensor.getValue()+"   ";

				currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myAccumulatorO2AirStoreOutFlowRateSensorName));
				System.out.println("Oxygen Accumulator Out FlowRate..."+currentSensor.getValue());
				fileoutput += currentSensor.getValue()+"   ";

				currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myInjectorO2AirEnvironmentOutFlowRateSensorName));
				System.out.println("Crew Oxygen In FlowRate..."+currentSensor.getValue());
				fileoutput += currentSensor.getValue()+"   ";

				currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myAirRSPotableWaterInFlowRateSensorName));
				System.out.println("Air RS Water In FlowRate..."+currentSensor.getValue());
				fileoutput += currentSensor.getValue()+"   ";

				currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myBiomassRSPotableWaterInFlowRateSensorName));
				System.out.println("Plants Potable Water In FlowRate..."+currentSensor.getValue());
				fileoutput += currentSensor.getValue()+"   ";*/

		System.out.println(fileoutput);
		pw.println(fileoutput);
	}

	public void setActuators() {

		GenericActuator currentActuator;

		BioModule myModule;

		WaterRS myWaterRS = (WaterRS)myBioHolder.theWaterRSModules.get(0);
		AirRS myAirRS = (AirRS)myBioHolder.theAirRSModules.get(0);
		BiomassRS myBiomassRS = (BiomassRS)myBioHolder.theBiomassRSModules.get(0);
		FoodProcessor myFoodProcessor = (FoodProcessor)myBioHolder.theFoodProcessors.get(0);
		if (SimState.get("dirtywater") == "low" || SimState.get("potablewater") == "high" ) {
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theDirtyWaterInFlowRateActuators, myWaterRS));
			currentActuator.setValue(0);
		}
		if (SimState.get("greywater") == "low" || SimState.get("potablewater") == "high" ) {
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theGreyWaterInFlowRateActuators, myWaterRS));
			currentActuator.setValue(0);
		}
		if (SimState.get("potablewater") == "low" || SimState.get("dirtywater") == "high") {
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theDirtyWaterInFlowRateActuators, myWaterRS));
			currentActuator.setValue(10);
		}
		if (SimState.get("potablewater") == "low" || SimState.get("greywater") == "high") {
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theGreyWaterInFlowRateActuators, myWaterRS));
			currentActuator.setValue(10);
		}

		if (SimState.get("biomass") != "high") {
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators, myBiomassRS));
			currentActuator.setValue(400);
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theGreyWaterInFlowRateActuators, myBiomassRS));
			currentActuator.setValue(5);
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePotableWaterInFlowRateActuators, myBiomassRS));
			currentActuator.setValue(10);

			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators, myFoodProcessor));
			currentActuator.setValue(0);

		}
		if (SimState.get("food") == "low" ) {
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators, myFoodProcessor));
			currentActuator.setValue(100);
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theBiomassInFlowRateActuators, myFoodProcessor));
			currentActuator.setValue(10);
		}
		if (SimState.get("food") == "high" && SimState.get("biomass") == "low") {
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators, myFoodProcessor));
			currentActuator.setValue(0);
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theBiomassInFlowRateActuators, myFoodProcessor));
			currentActuator.setValue(0);
		}

		if (SimState.get("oxygen") == "low") {
			// OGS on
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePotableWaterInFlowRateActuators, myAirRS));
			currentActuator.setValue(10f);
			System.out.println("Turning OGS On.");
		}
		if (SimState.get("oxygen") == "high" ) {
			// OGS off
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePotableWaterInFlowRateActuators, myAirRS));
			currentActuator.setValue(0f);
			System.out.println("Turning OGS Off.");

		}

		if (SimState.get("carbondioxide") == "high") {
			//turn on CRS
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2InFlowRateActuators, myAirRS));
			currentActuator.setValue(10f);
			System.out.println("Turning CRS On.");
		}
		if (SimState.get("carbondioxide") == "low") {
			//turn off CRS
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2InFlowRateActuators, myAirRS));
			currentActuator.setValue(0f);

		}
		if (SimState.get("hydrogen") == "low" & SimState.get("carbondioxide") != "high") {
			//turn off CRS
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2InFlowRateActuators, myAirRS));
			currentActuator.setValue(0f);

		}
	
		if (SimState.get("hydrogen") == "high") {
			// OGS off
			currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.thePotableWaterInFlowRateActuators, myAirRS));
			currentActuator.setValue(0f);

		}

		doAccumulatorsInjectors();
		doPlants(); 
	}

	private void doAccumulatorsInjectors() {
		// a crude feedback controller for the accumulators and injectors
		GenericSensor levelSensor, rateSensor;
		GenericActuator currentActuator;

		double delta, signal;
		double crewO2p, crewCO2p, plantO2p, plantCO2p;
		double crewO2i, crewCO2i, plantO2i, plantCO2i;
		double crewO2, crewCO2, plantO2, plantCO2;
		
		SimEnvironment myCrewEnvironment = (SimEnvironment)myBioHolder.theSimEnvironments.get(0);
		SimEnvironment myPlantEnvironment = (SimEnvironment)myBioHolder.theSimEnvironments.get(1);
		Injector myInjector = (Injector)myBioHolder.theInjectors.get(0);
		Accumulator myAccumulator = (Accumulator)myBioHolder.theAccumulators.get(0);
		
		//crew O2 feedback control
		crewO2p = 200;
		crewO2i = 0.5f;
		levelSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theO2AirConcentrationSensors, myCrewEnvironment));
		crewO2 = levelSensor.getValue();
		delta = (double)(CrewO2Level - crewO2);
		crewO2integral += delta;
		signal = delta*crewO2p + crewO2i*crewO2integral;
		System.out.println("O2 flow from tank to Crew environment: "+signal);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theO2AirEnvironmentOutFlowRateActuators, myInjector));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theO2AirStoreInFlowRateActuators, myInjector));
		currentActuator.setValue((float)(signal));

		//crew CO2 feedback control
		crewCO2p = -200;
		crewCO2i = -0.5f;
		levelSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theCO2AirConcentrationSensors, myCrewEnvironment));
		crewCO2 = levelSensor.getValue();
		delta = (double)(CrewCO2Level - crewCO2);
		crewCO2integral += delta;
		signal = delta*crewCO2p + crewCO2i*crewCO2integral;
		System.out.println("CO2 flow from Crew environment to tank: "+signal);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2AirEnvironmentInFlowRateActuators, myAccumulator));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2AirStoreOutFlowRateActuators, myAccumulator));
		currentActuator.setValue((float)(signal));

		//plant O2 feedback control
		plantO2p = -100;
		plantO2i = -0.05;
		levelSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theO2AirConcentrationSensors, myPlantEnvironment));
		plantO2 = levelSensor.getValue();
		delta = (double)(PlantO2Level - plantO2);
		plantO2integral += delta;
		signal = delta*plantO2p + plantO2i*plantO2integral;
		System.out.println("O2 flow from Plant environment to tank: "+signal);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theO2AirEnvironmentInFlowRateActuators, myAccumulator));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theO2AirStoreOutFlowRateActuators, myAccumulator));
		currentActuator.setValue((float)(signal));

		//plant CO2 feedback control
		plantCO2p = 50;
		plantCO2i = 0.01;
		levelSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theCO2AirConcentrationSensors, myPlantEnvironment));
		plantCO2 = levelSensor.getValue();
		delta = (double)(PlantCO2Level - plantCO2);
		plantCO2integral += delta;
		signal = delta*plantCO2p + plantCO2i*plantCO2integral;
		System.out.println("CO2 flow from tank to Plant environment: "+signal);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2AirEnvironmentOutFlowRateActuators, myInjector));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theCO2AirStoreInFlowRateActuators, myInjector));
		currentActuator.setValue((float)(signal));
		
		
		currentActuator = (GenericActuator)myBioHolder.theWaterAirEnvironmentInFlowRateActuators.get(0); 
		currentActuator.setValue(0f);
		currentActuator = (GenericActuator)myBioHolder.theWaterAirEnvironmentInFlowRateActuators.get(1); 
		currentActuator.setValue(5f);
		currentActuator = (GenericActuator)(myBioHolder.getActuatorAttachedTo(myBioHolder.theWaterAirStoreOutFlowRateActuators, myAccumulator));
		currentActuator.setValue(5f);
	}
	
	private void doPlants() { 
		float overflow, new_overflow;
		float cropacres; 
		GenericSensor harvestSensor; 
		GenericSensor currentSensor; 
		GenericActuator currentActuator;
		
		CO2Store myCO2Store = (CO2Store)myBioHolder.theCO2Stores.get(0);
		BiomassRS myBiomassRS = (BiomassRS)(myBioHolder.theBiomassRSModules.get(0));  
		currentActuator = PlantingActuatorHelper.narrow((myBioHolder.getActuatorAttachedTo(myBioHolder.thePlantingActuators, myBiomassRS)));
		HarvestingActuator harvestActuator = HarvestingActuatorHelper.narrow((myBioHolder.getActuatorAttachedTo(myBioHolder.theHarvestingActuators, myBiomassRS)));
		
		int i; 
		int num = myBioHolder.theHarvestSensors.size(); 
		
		for (i=0;i<num;i++) { 
			currentSensor = (GenericSensor)myBioHolder.theHarvestSensors.get(i); 
			System.out.println(" Harvest Sensor "+currentSensor.getValue());

			currentActuator = (GenericActuator)myBioHolder.thePlantingActuators.get(i); 
			if (currentSensor.getValue() == 1f) { 
				myBiomassRS.getShelf(i).harvest(); 
				cropacres = currentActuator.getValue();
				currentSensor = (GenericSensor)(myBioHolder.getSensorAttachedTo(myBioHolder.theStoreOverflowSensors, myCO2Store));
//				overflow = currentSensor.getValue();
//				new_overflow = overflow - co2_overflow; 
//				if (new_overflow > 0) cropacres += 20; 
//				else cropacres -= 20; 
//				co2_overflow= overflow;
				System.out.println("Planting "+cropacres+" m^2 of wheat."); 
				currentActuator.setValue(cropacres);
				myBiomassRS.getShelf(i).replant(myBiomassRS.getShelf(i).getCropType(), cropacres);  
	
			}
		}
	}
}

