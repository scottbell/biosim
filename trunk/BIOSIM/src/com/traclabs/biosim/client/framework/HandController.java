
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
import biosim.client.util.BioHolder;
import biosim.idl.simulation.crew.*;

/**
 * @author    Theresa Klein
 To compile:
 1) run make-client.sh
 2) javac -classpath .:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/generated/client/classes HandController.java
 
 javac - the compiler
 jacorb.jar - the library that has the ORB and various CORBA utilities
 generated/client/classes - the generated client stubs
 TestBiosim - this file

 
 To run:
 1)run run-nameserver.sh
 2)run run-server.sh
 3)java -classpath .:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME/generated/client/classes -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton -DORBInitRef.NameService=file:$BIOSIM_HOME/generated/ns/ior.txt HandController
 (all the above on one line)
 
 -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB - overriding Sun's default ORB (using Jacorb instead)
 -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton - overriding Sun's default ORB (using Jacorb instead)
 -DORBInitRef.NameService=file:$BIOSIM_HOME/generated/ns/ior.txt - telling the client where to look for the ior (serialized nameservice object, produced by run-nameserver.sh)
 
 Good Luck!  If you have any questions, email me at:
 scott@traclabs.com

 */

public class HandController{
	double DirtyWaterLowLevel = 100f;
	double DirtyWaterHighLevel = 400f;
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
	double CrewCO2Level = 0.005;
	double PlantO2Level =  .1;
	double PlantCO2Level = .3;
	double crewO2integral, crewCO2integral, plantO2integral, plantCO2integral;


	File OutFile = new File("output.txt");
	FileWriter fw;
	PrintWriter pw;


	TreeMap SimState = new TreeMap();

	BioDriver myBioDriver;

	public static void main(String[] args){

		HandController myController = new HandController();
		myController.runSim();
	}

	public void runSim(){
		int i;
		try { 	fw = new FileWriter(OutFile) ; }
		catch (IOException e) {}
		pw = new PrintWriter(fw, true) ;
		myBioDriver = BioHolder.getBioDriver();
		//myBioDriver.setFullLogging(true);
		myBioDriver.spawnSimulation();

		CrewGroup myCrew = (CrewGroup)BioHolder.getBioModule(BioHolder.crewName);

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
			//for (i=0;i<10; i++) 
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

		int i;

		String fileoutput;



		fileoutput = myBioDriver.getTicks()+"   ";


		GenericSensor currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myDirtyWaterStoreLevelSensorName));
		if (currentSensor.getValue() < DirtyWaterLowLevel)
			SimState.put("dirtywater", "low");
		else if (currentSensor.getValue() > DirtyWaterHighLevel)
			SimState.put("dirtywater", "high");
		else SimState.put("dirtywater", "normal");
		System.out.println("Dirty water..."+currentSensor.getValue()+"..."+SimState.get("dirtywater"));
		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myPotableWaterStoreLevelSensorName));
		if (currentSensor.getValue() < PotableWaterLowLevel)
			SimState.put("potablewater", "low");
		else if (currentSensor.getValue() > PotableWaterHighLevel)
			SimState.put("potablewater", "high");
		else SimState.put("potablewater", "normal");

		System.out.println("Potable water..."+currentSensor.getValue()+"..."+SimState.get("potablewater"));
		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myBiomassStoreLevelSensorName));
		if (currentSensor.getValue() < BiomassLowLevel)
			SimState.put("biomass", "low");
		else if (currentSensor.getValue() > BiomassHighLevel)
			SimState.put("biomass", "high");
		else SimState.put("biomass", "normal");
		System.out.println("Biomass..."+currentSensor.getValue()+"..."+SimState.get("biomass"));
		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myFoodStoreLevelSensorName));
		if (currentSensor.getValue() < FoodLowLevel)
			SimState.put("food", "low");
		else if (currentSensor.getValue() > FoodHighLevel)
			SimState.put("food", "high");
		else SimState.put("food", "normal");
		System.out.println("Food ..."+currentSensor.getValue()+"..."+SimState.get("food"));
		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myPowerStoreLevelSensorName));
		if (currentSensor.getValue() < PowerLowLevel)
			SimState.put("power", "low");
		else if (currentSensor.getValue() > PowerHighLevel)
			SimState.put("power", "high");
		else SimState.put("power", "normal");
		System.out.println("Power..."+currentSensor.getValue()+"..."+SimState.get("power"));
		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myO2StoreLevelSensorName));
		if (currentSensor.getValue() < O2StoreLowLevel)
			SimState.put("oxygen", "low");
		else if (currentSensor.getValue() > O2StoreHighLevel)
			SimState.put("oxygen", "high");
		else SimState.put("oxygen", "normal");
		System.out.println("Oxygen..."+currentSensor.getValue()+"..."+SimState.get("oxygen"));

		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myCO2StoreLevelSensorName));
		if (currentSensor.getValue() < CO2StoreLowLevel)
			SimState.put("carbondioxyide", "low");
		else if (currentSensor.getValue() > CO2StoreHighLevel)
			SimState.put("carbondioxide", "high");
		else SimState.put("carbondioxide", "normal");
		System.out.println("Carbon Dioxide..."+currentSensor.getValue()+"..."+SimState.get("carbondioxide"));

		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myH2StoreLevelSensorName));
		System.out.println("Hydrogen..."+currentSensor.getValue()+"..."+SimState.get("hydrogen"));

		if (currentSensor.getValue() < H2StoreLowLevel)
			SimState.put("hydrogen", "low");
		else if (currentSensor.getValue() > H2StoreHighLevel)
			SimState.put("hydrogen", "high");
		else SimState.put("hydrogen", "normal");
		System.out.println("Hydrogen..."+currentSensor.getValue()+"..."+SimState.get("hydrogen"));

		fileoutput += currentSensor.getValue()+"   ";



		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myCrewEnvironmentO2AirConcentrationSensorName));
		System.out.println("Crew O2..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";

		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myCrewEnvironmentCO2AirConcentrationSensorName));
		System.out.println("Crew CO2..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";


		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myPlantEnvironmentO2AirConcentrationSensorName));
		System.out.println("Plant O2..."+currentSensor.getValue());
		fileoutput += currentSensor.getValue()+"   ";


		currentSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myPlantEnvironmentCO2AirConcentrationSensorName));
		System.out.println("Plant CO2..."+currentSensor.getValue());
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

		if (SimState.get("dirtywater") == "low" || SimState.get("potablewater") == "high") {

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myWaterRSPowerInFlowRateActuatorName));

			currentActuator.setValue(0);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myWaterRSDirtyWaterInFlowRateActuatorName));

			currentActuator.setValue(0);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myWaterRSGreyWaterInFlowRateActuatorName));

			currentActuator.setValue(0);
		}
		if (SimState.get("potablewater") == "low" || SimState.get("dirtywater") == "high") {

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myWaterRSPowerInFlowRateActuatorName));

			currentActuator.setValue(468);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myWaterRSDirtyWaterInFlowRateActuatorName));

			currentActuator.setValue(100);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myWaterRSGreyWaterInFlowRateActuatorName));

			currentActuator.setValue(100);

		}

		if (SimState.get("biomass") != "high") {

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myBiomassRSPowerInFlowRateActuatorName));

			currentActuator.setValue(400);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myBiomassRSGreyWaterInFlowRateActuatorName));

			currentActuator.setValue(5);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myBiomassRSPotableWaterInFlowRateActuatorName));

			currentActuator.setValue(10);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myFoodProcessorPowerInFlowRateActuatorName));

			currentActuator.setValue(0);
		}
		if (SimState.get("food") == "low" ) {

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myFoodProcessorPowerInFlowRateActuatorName));
			currentActuator.setValue(100);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myFoodProcessorBiomassInFlowRateActuatorName));
			currentActuator.setValue(10);
		}
		if (SimState.get("food") == "high" && SimState.get("biomass") == "low") {

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myFoodProcessorPowerInFlowRateActuatorName));
			currentActuator.setValue(0);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myFoodProcessorBiomassInFlowRateActuatorName));
			currentActuator.setValue(0);
		}

		if (SimState.get("oxygen") == "low") {
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSPowerInFlowRateActuatorName));


			currentActuator.setValue(300);


			// OGS on

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSPotableWaterInFlowRateActuatorName));

			currentActuator.setValue(0.5f);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSO2OutFlowRateActuatorName));

			currentActuator.setValue(100f);
		}
		if (SimState.get("oxygen") == "high" ) {
			// OGS off

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSPotableWaterInFlowRateActuatorName));

			currentActuator.setValue(0f);

			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSO2OutFlowRateActuatorName));

			currentActuator.setValue(0f);

		}

		if (SimState.get("carbondioxide") == "high") {

			//turn off VCCR
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSAirInFlowRateActuatorName));
			currentActuator.setValue(0f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSCO2OutFlowRateActuatorName));
			currentActuator.setValue(0f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSAirOutFlowRateActuatorName));
			currentActuator.setValue(0f);
			//turn on CRS
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSCO2InFlowRateActuatorName));
			currentActuator.setValue(100f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSPotableWaterOutFlowRateActuatorName));
			currentActuator.setValue(100f);
		}
		if (SimState.get("carbondioxide") == "low") {
			//turn on VCCR
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSAirInFlowRateActuatorName));
			currentActuator.setValue(100f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSCO2OutFlowRateActuatorName));
			currentActuator.setValue(100f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSAirOutFlowRateActuatorName));
			currentActuator.setValue(100f);
			//turn off CRS
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSCO2InFlowRateActuatorName));
			currentActuator.setValue(0f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSPotableWaterOutFlowRateActuatorName));
			currentActuator.setValue(0f);
		}
		if (SimState.get("hydrogen") == "low") {
			//turn off CRS
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSCO2InFlowRateActuatorName));
			currentActuator.setValue(0f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSPotableWaterOutFlowRateActuatorName));
			currentActuator.setValue(0f);
		}
		if (SimState.get("hydrogen") == "high") {
			// OGS off
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSPotableWaterInFlowRateActuatorName));
			currentActuator.setValue(0f);
			currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAirRSO2OutFlowRateActuatorName));
			currentActuator.setValue(0f);
		}





		doAccumulatorsInjectors();
	}

	private void doAccumulatorsInjectors() {

		// a crude feedback controller for the accumulators and injectors
		GenericSensor levelSensor, rateSensor;
		GenericActuator currentActuator;

		double delta, signal;
		double crewO2p, crewCO2p, plantO2p, plantCO2p;
		double crewO2i, crewCO2i, plantO2i, plantCO2i;
		double crewO2, crewCO2, plantO2, plantCO2;



		//crew O2 feedback control
		crewO2p = 150;
		crewO2i = 10;
		levelSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myCrewEnvironmentO2AirConcentrationSensorName));
		crewO2 = levelSensor.getValue();
		delta = (double)(CrewO2Level - crewO2);
		crewO2integral += delta;
		signal = delta*crewO2p + crewO2i*crewO2integral;
		System.out.println("O2 flow from tank to Crew environment: "+signal);
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myInjectorO2AirEnvironmentOutFlowRateActuatorName));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myInjectorO2AirStoreInFlowRateActuatorName));
		currentActuator.setValue((float)(signal));

		//crew CO2 feedback control
		crewCO2p = -200;
		crewCO2i = -15;
		levelSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myCrewEnvironmentCO2AirConcentrationSensorName));
		crewCO2 = levelSensor.getValue();
		delta = (double)(CrewCO2Level - crewCO2);
		crewCO2integral += delta;
		signal = delta*crewCO2p + crewCO2i*crewCO2integral;
		System.out.println("CO2 flow from Crew environment to tank: "+signal);
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAccumulatorCO2AirEnvironmentInFlowRateActuatorName));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAccumulatorCO2AirStoreOutFlowRateActuatorName));
		currentActuator.setValue((float)(signal));

		//plant O2 feedback control
		plantO2p = -150;
		plantO2i = -10;
		levelSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myPlantEnvironmentO2AirConcentrationSensorName));
		plantO2 = levelSensor.getValue();
		delta = (double)(PlantO2Level - plantO2);
		plantO2integral += delta;
		signal = delta*plantO2p + plantO2i*plantO2integral;
		System.out.println("O2 flow from Plant environment to tank: "+signal);
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAccumulatorO2AirEnvironmentInFlowRateActuatorName));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myAccumulatorO2AirStoreOutFlowRateActuatorName));
		currentActuator.setValue((float)(signal));

		//plant CO2 feedback control
		plantCO2p = 200;
		plantCO2i = 10;
		levelSensor = (GenericSensor)(BioHolder.getBioModule(BioHolder.myPlantEnvironmentCO2AirConcentrationSensorName));
		plantCO2 = levelSensor.getValue();
		delta = (double)(PlantCO2Level - plantCO2);
		plantCO2integral += delta;
		signal = delta*plantCO2p + plantCO2i*plantCO2integral;
		System.out.println("CO2 flow from tank to Plant environment: "+signal);
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myInjectorCO2AirEnvironmentOutFlowRateActuatorName));
		currentActuator.setValue((float)(signal));
		currentActuator = (GenericActuator)(BioHolder.getBioModule(BioHolder.myInjectorCO2AirStoreInFlowRateActuatorName));
		currentActuator.setValue((float)(signal));


	}
}

