package biosim.client.util;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;
import java.util.*;
import biosim.client.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
/**
 * @author    Scott Bell
 */	

public class BioHolder
{
	//Module Names
	//Simulation
	public final static String crewName = "CrewGroup";
	public final static String powerPSName = "PowerPS";
	public final static String powerStoreName = "PowerStore";
	public final static String airRSName = "AirRS";
	public final static String CO2StoreName = "CO2Store";
	public final static String O2StoreName = "O2Store";
	public final static String biomassRSName = "BiomassRS";
	public final static String biomassStoreName = "BiomassStore";
	public final static String foodProcessorName = "FoodProcessor";
	public final static String foodStoreName = "FoodStore";
	public final static String waterRSName = "WaterRS";
	public final static String dirtyWaterStoreName = "DirtyWaterStore";
	public final static String potableWaterStoreName = "PotableWaterStore";
	public final static String greyWaterStoreName = "GreyWaterStore";
	public final static String simEnvironmentName = "CrewEnvironment";
	public final static String crewEnvironmentName = "CrewEnvironment";
	public final static String plantEnvironmentName = "PlantEnvironment";
	//Sensor
	//Air
	//AirRs
	public final static String myAirRSPowerInFlowRateSensorName = "AirRSPowerInFlowRateSensor";
	public final static String myAirRSAirInFlowRateSensorName = "AirRSAirInFlowRateSensor";
	public final static String myAirRSAirOutFlowRateSensorName = "AirRSAirOutFlowRateSensor";
	public final static String myAirRSO2OutFlowRateSensorName = "AirRSO2OutFlowRateSensor";
	public final static String myAirRSCO2InFlowRateSensorName = "AirRSCO2InFlowRateSensor";
	public final static String myAirRSCO2OutFlowRateSensorName = "AirRSCO2OutFlowRateSensor";
	//Stores
	public final static String myO2StoreLevelSensorName = "O2StoreLevelSensor";
	public final static String myCO2StoreLevelSensorName = "CO2StoreLevel";
	//Power
	//PowerPS
	public final static String myPowerPSPowerOutFlowRateSensorName = "PowerPSPowerOutFlowRateSensor";
	//Stores
	public final static String myPowerStoreLevelSensorName = "PowerStoreLevelSensor";
	//Environment
	//Crew
	public final static String myCrewEnvironmentOtherAirLevelSensorName = "CrewEnvironmentOtherAirLevelSensor";
	public final static String myCrewEnvironmentCO2AirLevelSensorName = "CrewEnvironmentCO2AirLevelSensor";
	public final static String myCrewEnvironmentO2AirLevelSensorName = "CrewEnvironmentO2AirLevelSensor";
	//Plant
	public final static String myPlantEnvironmentOtherAirLevelSensorName = "PlantEnvironmentOtherAirLevelSensor";
	public final static String myPlantEnvironmentCO2AirLevelSensorName = "PlantEnvironmentCO2AirLevelSensor";
	public final static String myPlantEnvironmentO2AirLevelSensorName = "PlantEnvironmentO2AirLevelSensor";
	//Water
	//WaterRS
	public final static String myWaterRSDirtyWaterInFlowRateSensorName = "WaterRSDirtyWaterInFlowRateSensor";
	public final static String myWaterRSGreyWaterInFlowRateSensorName = "WaterRSGreyWaterInFlowRateSensor";
	public final static String myWaterRSPowerInFlowRateSensorName = "WaterRSPowerInFlowRateSensor";
	public final static String myWaterRSPotableWaterOutFlowRateSensorName = "WaterRSPotableWaterOutFlowRateSensor";
	//Stores
	public final static String myPotableWaterStoreLevelSensorName = "PotableWaterStoreLevelSensor";
	public final static String myGreyWaterStoreLevelSensorName = "GreyWaterStoreLevelSensor";
	public final static String myDirtyWaterStoreLevelSensorName = "DirtyWaterStoreLevelSensor";
	//Food
	//BiomassRS
	public final static String myBiomassRSAirInFlowRateSensorName = "BiomassRSAirInFlowRateSensor";
	public final static String myBiomassRSAirOutFlowRateSensorName = "BiomassRSAirOutFlowRateSensor";
	public final static String myBiomassRSPotableWaterInFlowRateSensorName = "BiomassRSPotableWaterInFlowRateSensor";
	public final static String myBiomassRSGreyWaterInFlowRateSensorName = "BiomassRSGreyWaterInFlowRateSensor";
	public final static String myBiomassRSBiomassOutFlowRateSensorName = "BiomassRSBiomassOutFlowRateSensor";
	//Food Processor
	public final static String myFoodProcessorPowerInFlowRateSensorName = "FoodProcessorPowerInFlowRateSensor";
	public final static String myFoodProcessorBiomassInFlowRateSensorName = "FoodProcessorBiomassInFlowRateSensor";
	public final static String myFoodProcessorFoodOutFlowRateSensorName = "FoodProcessorFoodOutFlowRateSensor";
	//Stores
	public final static String myBiomassStoreLevelSensorName = "BiomassStoreLevelSensor";
	public final static String myFoodStoreLevelSensorName = "FoodStoreLevelSensor";
	//Framework
	//Accumulator
	public final static String myAccumulatorCO2AirEnvironmentInFlowRateSensorName = "AccumulatorCO2AirEnvironmentInFlowRateSensor";
	public final static String myAccumulatorO2AirEnvironmentInFlowRateSensorName = "AccumulatorO2AirEnvironmentInFlowRateSensor";
	public final static String myAccumulatorCO2AirStoreOutFlowRateSensorName = "AccumulatorCO2AirStoreOutFlowRateSensor";
	public final static String myAccumulatorO2AirStoreOutFlowRateSensorName = "AccumulatorO2AirStoreOutFlowRateSensor";
	//Injector
	public final static String myInjectorCO2AirStoreInFlowRateSensorName = "InjectorCO2AirStoreInFlowRateSensor";
	public final static String myInjectorO2AirStoreInFlowRateSensorName = "InjectorO2AirStoreInFlowRateSensor";
	public final static String myInjectorCO2AirEnvironmentOutFlowRateSensorName = "InjectorCO2AirEnvironmentOutFlowRateSensor";
	public final static String myInjectorO2AirEnvironmentOutFlowRateSensorName = "InjectorO2AirEnvironmentOutFlowRateSensor";

	//A hastable containing the server references
	private static Map modules;
	private static BioDriver myBioDriver;
	private static boolean hasCollectedReferences = false;
	private static int myID = 0;

	/**
	* Fetches a BioModule (e.g. AirRS, FoodProcessor, PotableWaterStore) that has been collected by the BioSimulator
	* @return the BioModule requested, null if not found
	*/
	public static BioModule getBioModule(String type){
		collectReferences();
		if (type == null){
			System.err.println("BioHolder: Passed null string....");
			return null;
		}
		BioModule returnModule = (BioModule)(modules.get(type));
		if (returnModule == null){
			System.err.println("BioHolder: Couldn't find module: "+type);
			return null;
		}
		return returnModule;
	}

	public static BioModule[] getBioModules(){
		collectReferences();
		BioModule[] arrayModules = new BioModule[modules.size()];
		return (BioModule[])(modules.values().toArray(arrayModules));
	}

	public static String[] getBioModuleNames(){
		collectReferences();
		String[] arrayModuleNames = new String[modules.size()];
		if (modules.size() == 0)
			return arrayModuleNames;
		return (String[])(modules.keySet().toArray(arrayModuleNames));
	}

	public static BioDriver getBioDriver(){
		collectReferences();
		return myBioDriver;
	}

	public static void setID(int pID){
		myID = pID;
	}

	/**
	* Tries to collect references to all the servers and adds them to a hashtable than can be accessed by outside classes.
	*/
	private static void collectReferences(){
		if (hasCollectedReferences)
			return;
		// resolve the Objects Reference in Naming
		try{
			if (modules == null)
				modules = new Hashtable();
			System.err.println("BioHolder: Collecting simulation references to modules...");
			CrewGroup myCrew = CrewGroupHelper.narrow(OrbUtils.getNCRef().resolve_str(crewName+myID));
			modules.put(crewName , myCrew);
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNCRef().resolve_str(powerPSName+myID));
			modules.put(powerPSName , myPowerPS);
			PowerStore myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(powerStoreName+myID));
			modules.put(powerStoreName , myPowerStore);
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNCRef().resolve_str(airRSName+myID));
			modules.put(airRSName , myAirRS);
			SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(crewEnvironmentName+myID));
			modules.put(crewEnvironmentName , myCrewEnvironment);
			SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(plantEnvironmentName+myID));
			modules.put(plantEnvironmentName , myPlantEnvironment);
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(greyWaterStoreName+myID));
			modules.put(greyWaterStoreName , myGreyWaterStore);
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(potableWaterStoreName+myID));
			modules.put(potableWaterStoreName , myPotableWaterStore);
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(dirtyWaterStoreName+myID));
			modules.put(dirtyWaterStoreName , myDirtyWaterStore);
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNCRef().resolve_str(foodProcessorName+myID));
			modules.put(foodProcessorName , myFoodProcessor);
			FoodStore myFoodStore= FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(foodStoreName+myID));
			modules.put(foodStoreName , myFoodStore);
			CO2Store myCO2Store = CO2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(CO2StoreName+myID));
			modules.put(CO2StoreName , myCO2Store);
			O2Store myO2Store = O2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(O2StoreName+myID));
			modules.put(O2StoreName , myO2Store);
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassRSName+myID));
			modules.put(biomassRSName , myBiomassRS);
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassStoreName+myID));
			modules.put(biomassStoreName, myBiomassStore);
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNCRef().resolve_str(waterRSName+myID));
			modules.put(waterRSName , myWaterRS);


			System.err.println("BioHolder: Collecting sensor references to modules...");
			//Air
			{
				//AirRS
				{
					PowerInFlowRateSensor myAirRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSPowerInFlowRateSensorName+myID));
					modules.put(myAirRSPowerInFlowRateSensorName , myAirRSPowerInFlowRateSensor);
					AirInFlowRateSensor myAirRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirInFlowRateSensorName+myID));
					modules.put(myAirRSAirInFlowRateSensorName , myAirRSAirInFlowRateSensor);
					AirOutFlowRateSensor myAirRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirOutFlowRateSensorName+myID));
					modules.put(myAirRSAirOutFlowRateSensorName , myAirRSAirOutFlowRateSensor);
					O2OutFlowRateSensor myAirRSO2OutFlowRateSensor = O2OutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSO2OutFlowRateSensorName+myID));
					modules.put(myAirRSO2OutFlowRateSensorName , myAirRSO2OutFlowRateSensor);
					CO2InFlowRateSensor myAirRSCO2InFlowRateSensor = CO2InFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2InFlowRateSensorName+myID));
					modules.put(myAirRSCO2InFlowRateSensorName , myAirRSCO2InFlowRateSensor);
					CO2OutFlowRateSensor myAirRSCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2OutFlowRateSensorName+myID));
					modules.put(myAirRSCO2OutFlowRateSensorName , myAirRSCO2OutFlowRateSensor);
				}
			}


			/*
			//Stores
			myO2StoreLevelSensorName = "O2StoreLevelSensor";
			myCO2StoreLevelSensorName = "CO2StoreLevel";
			//Power
			//PowerPS
			myPowerPSPowerOutFlowRateSensorName = "PowerPSPowerOutFlowRateSensor";
			//Stores
			myPowerStoreLevelSensorName = "PowerStoreLevelSensor";
			//Environment
			//Crew
			myCrewEnvironmentOtherAirLevelSensorName = "CrewEnvironmentOtherAirLevelSensor";
			myCrewEnvironmentCO2AirLevelSensorName = "CrewEnvironmentCO2AirLevelSensor";
			myCrewEnvironmentO2AirLevelSensorName = "CrewEnvironmentO2AirLevelSensor";
			//Plant
			myPlantEnvironmentOtherAirLevelSensorName = "PlantEnvironmentOtherAirLevelSensor";
			myPlantEnvironmentCO2AirLevelSensorName = "PlantEnvironmentCO2AirLevelSensor";
			myPlantEnvironmentO2AirLevelSensorName = "PlantEnvironmentO2AirLevelSensor";
			//Water
			//WaterRS
			myWaterRSDirtyWaterInFlowRateSensorName = "WaterRSDirtyWaterInFlowRateSensor";
			myWaterRSGreyWaterInFlowRateSensorName = "WaterRSGreyWaterInFlowRateSensor";
			myWaterRSPowerInFlowRateSensorName = "WaterRSPowerInFlowRateSensor";
			myWaterRSPotableWaterOutFlowRateSensorName = "WaterRSPotableWaterOutFlowRateSensor";
			//Stores
			myPotableWaterStoreLevelSensorName = "PotableWaterStoreLevelSensor";
			myGreyWaterStoreLevelSensorName = "GreyWaterStoreLevelSensor";
			myDirtyWaterStoreLevelSensorName = "DirtyWaterStoreLevelSensor";
			//Food
			//BiomassRS
			myBiomassRSAirInFlowRateSensorName = "BiomassRSAirInFlowRateSensor";
			myBiomassRSAirOutFlowRateSensorName = "BiomassRSAirOutFlowRateSensor";
			myBiomassRSPotableWaterInFlowRateSensorName = "BiomassRSPotableWaterInFlowRateSensor";
			myBiomassRSGreyWaterInFlowRateSensorName = "BiomassRSGreyWaterInFlowRateSensor";
			myBiomassRSBiomassOutFlowRateSensorName = "BiomassRSBiomassOutFlowRateSensor";
			//Food Processor
			myFoodProcessorPowerInFlowRateSensorName = "FoodProcessorPowerInFlowRateSensor";
			myFoodProcessorBiomassInFlowRateSensorName = "FoodProcessorBiomassInFlowRateSensor";
			myFoodProcessorFoodOutFlowRateSensorName = "FoodProcessorFoodOutFlowRateSensor";
			//Stores
			myBiomassStoreLevelSensorName = "BiomassStoreLevelSensor";
			myFoodStoreLevelSensorName = "FoodStoreLevelSensor";
			//Framework
			//Accumulator
			myAccumulatorCO2AirEnvironmentInFlowRateSensorName = "AccumulatorCO2AirEnvironmentInFlowRateSensor";
			myAccumulatorO2AirEnvironmentInFlowRateSensorName = "AccumulatorO2AirEnvironmentInFlowRateSensor";
			myAccumulatorCO2AirStoreOutFlowRateSensorName = "AccumulatorCO2AirStoreOutFlowRateSensor";
			myAccumulatorO2AirStoreOutFlowRateSensorName = "AccumulatorO2AirStoreOutFlowRateSensor";
			//Injector
			myInjectorCO2AirStoreInFlowRateSensorName = "InjectorCO2AirStoreInFlowRateSensor";
			myInjectorO2AirStoreInFlowRateSensorName = "InjectorO2AirStoreInFlowRateSensor";
			myInjectorCO2AirEnvironmentOutFlowRateSensorName = "InjectorCO2AirEnvironmentOutFlowRateSensor";
			myInjectorO2AirEnvironmentOutFlowRateSensorName = "InjectorO2AirEnvironmentOutFlowRateSensor";
			*/

			System.err.println("BioHolder: Collecting framework references to modules...");
			myBioDriver = BioDriverHelper.narrow(OrbUtils.getNCRef().resolve_str("BioDriver"+myID));
			hasCollectedReferences = true;
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.sleepAwhile();
			collectReferences();
		}
		catch (Exception e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.resetInit();
			OrbUtils.sleepAwhile();
			collectReferences();
		}
	}
}

