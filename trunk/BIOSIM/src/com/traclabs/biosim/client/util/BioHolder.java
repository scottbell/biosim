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
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.water.*;
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
	public final static String myCO2StoreLevelSensorName = "CO2StoreLevelSensor";
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
	public final static String myBiomassRSPowerInFlowRateSensorName = "BiomassRSPowerInFlowRateSensor";
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
	//Actuator
	//Air
	//AirRs
	public final static String myAirRSPowerInFlowRateActuatorName = "AirRSPowerInFlowRateActuator";
	public final static String myAirRSAirInFlowRateActuatorName = "AirRSAirInFlowRateActuator";
	public final static String myAirRSAirOutFlowRateActuatorName = "AirRSAirOutFlowRateActuator";
	public final static String myAirRSO2OutFlowRateActuatorName = "AirRSO2OutFlowRateActuator";
	public final static String myAirRSCO2InFlowRateActuatorName = "AirRSCO2InFlowRateActuator";
	public final static String myAirRSCO2OutFlowRateActuatorName = "AirRSCO2OutFlowRateActuator";
	//Stores
	public final static String myO2StoreLevelActuatorName = "O2StoreLevelActuator";
	public final static String myCO2StoreLevelActuatorName = "CO2StoreLevelActuator";
	//Power
	//PowerPS
	public final static String myPowerPSPowerOutFlowRateActuatorName = "PowerPSPowerOutFlowRateActuator";
	//Stores
	public final static String myPowerStoreLevelActuatorName = "PowerStoreLevelActuator";
	//Environment
	//Crew
	public final static String myCrewEnvironmentOtherAirLevelActuatorName = "CrewEnvironmentOtherAirLevelActuator";
	public final static String myCrewEnvironmentCO2AirLevelActuatorName = "CrewEnvironmentCO2AirLevelActuator";
	public final static String myCrewEnvironmentO2AirLevelActuatorName = "CrewEnvironmentO2AirLevelActuator";
	//Plant
	public final static String myPlantEnvironmentOtherAirLevelActuatorName = "PlantEnvironmentOtherAirLevelActuator";
	public final static String myPlantEnvironmentCO2AirLevelActuatorName = "PlantEnvironmentCO2AirLevelActuator";
	public final static String myPlantEnvironmentO2AirLevelActuatorName = "PlantEnvironmentO2AirLevelActuator";
	//Water
	//WaterRS
	public final static String myWaterRSDirtyWaterInFlowRateActuatorName = "WaterRSDirtyWaterInFlowRateActuator";
	public final static String myWaterRSGreyWaterInFlowRateActuatorName = "WaterRSGreyWaterInFlowRateActuator";
	public final static String myWaterRSPowerInFlowRateActuatorName = "WaterRSPowerInFlowRateActuator";
	public final static String myWaterRSPotableWaterOutFlowRateActuatorName = "WaterRSPotableWaterOutFlowRateActuator";
	//Stores
	public final static String myPotableWaterStoreLevelActuatorName = "PotableWaterStoreLevelActuator";
	public final static String myGreyWaterStoreLevelActuatorName = "GreyWaterStoreLevelActuator";
	public final static String myDirtyWaterStoreLevelActuatorName = "DirtyWaterStoreLevelActuator";
	//Food
	//BiomassRS
	public final static String myBiomassRSAirInFlowRateActuatorName = "BiomassRSAirInFlowRateActuator";
	public final static String myBiomassRSAirOutFlowRateActuatorName = "BiomassRSAirOutFlowRateActuator";
	public final static String myBiomassRSPotableWaterInFlowRateActuatorName = "BiomassRSPotableWaterInFlowRateActuator";
	public final static String myBiomassRSGreyWaterInFlowRateActuatorName = "BiomassRSGreyWaterInFlowRateActuator";
	public final static String myBiomassRSBiomassOutFlowRateActuatorName = "BiomassRSBiomassOutFlowRateActuator";
	public final static String myBiomassRSPowerInFlowRateActuatorName = "BiomassRSPowerInFlowRateActuator";
	//Food Processor
	public final static String myFoodProcessorPowerInFlowRateActuatorName = "FoodProcessorPowerInFlowRateActuator";
	public final static String myFoodProcessorBiomassInFlowRateActuatorName = "FoodProcessorBiomassInFlowRateActuator";
	public final static String myFoodProcessorFoodOutFlowRateActuatorName = "FoodProcessorFoodOutFlowRateActuator";
	//Stores
	public final static String myBiomassStoreLevelActuatorName = "BiomassStoreLevelActuator";
	public final static String myFoodStoreLevelActuatorName = "FoodStoreLevelActuator";
	//Framework
	//Accumulator
	public final static String myAccumulatorCO2AirEnvironmentInFlowRateActuatorName = "AccumulatorCO2AirEnvironmentInFlowRateActuator";
	public final static String myAccumulatorO2AirEnvironmentInFlowRateActuatorName = "AccumulatorO2AirEnvironmentInFlowRateActuator";
	public final static String myAccumulatorCO2AirStoreOutFlowRateActuatorName = "AccumulatorCO2AirStoreOutFlowRateActuator";
	public final static String myAccumulatorO2AirStoreOutFlowRateActuatorName = "AccumulatorO2AirStoreOutFlowRateActuator";
	//Injector
	public final static String myInjectorCO2AirStoreInFlowRateActuatorName = "InjectorCO2AirStoreInFlowRateActuator";
	public final static String myInjectorO2AirStoreInFlowRateActuatorName = "InjectorO2AirStoreInFlowRateActuator";
	public final static String myInjectorCO2AirEnvironmentOutFlowRateActuatorName = "InjectorCO2AirEnvironmentOutFlowRateActuator";
	public final static String myInjectorO2AirEnvironmentOutFlowRateActuatorName = "InjectorO2AirEnvironmentOutFlowRateActuator";

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
			System.out.println("BioHolder: Collecting simulation references to modules...");
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

			System.out.println("BioHolder: Collecting sensor references to modules...");
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
				//Stores
				{
					O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myO2StoreLevelSensorName+myID));
					modules.put(myO2StoreLevelSensorName , myO2StoreLevelSensor);
					CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCO2StoreLevelSensorName+myID));
					modules.put(myCO2StoreLevelSensorName , myCO2StoreLevelSensor);
				}
			}
			//Power
			{
				//PowerPS
				{
					PowerOutFlowRateSensor myPowerPSPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPowerPSPowerOutFlowRateSensorName+myID));
					modules.put(myPowerPSPowerOutFlowRateSensorName , myPowerPSPowerOutFlowRateSensor);
				}
				//Stores
				{
					PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPowerStoreLevelSensorName+myID));
					modules.put(myPowerStoreLevelSensorName , myPowerStoreLevelSensor);
				}
			}
			//Environment
			{
				//Crew
				{
					OtherAirLevelSensor myCrewEnvironmentOtherAirLevelSensor = OtherAirLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentOtherAirLevelSensorName+myID));
					modules.put(myCrewEnvironmentOtherAirLevelSensorName , myCrewEnvironmentOtherAirLevelSensor);
					O2AirLevelSensor myCrewEnvironmentO2AirLevelSensor = O2AirLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentO2AirLevelSensorName+myID));
					modules.put(myCrewEnvironmentO2AirLevelSensorName , myCrewEnvironmentO2AirLevelSensor);
					CO2AirLevelSensor myCrewEnvironmentCO2AirLevelSensor = CO2AirLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentCO2AirLevelSensorName+myID));
					modules.put(myCrewEnvironmentCO2AirLevelSensorName , myCrewEnvironmentCO2AirLevelSensor);
				}
				//Plant
				{
					OtherAirLevelSensor myPlantEnvironmentOtherAirLevelSensor = OtherAirLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentOtherAirLevelSensorName+myID));
					modules.put(myPlantEnvironmentOtherAirLevelSensorName , myPlantEnvironmentOtherAirLevelSensor);
					O2AirLevelSensor myPlantEnvironmentO2AirLevelSensor = O2AirLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentO2AirLevelSensorName+myID));
					modules.put(myPlantEnvironmentO2AirLevelSensorName , myPlantEnvironmentO2AirLevelSensor);
					CO2AirLevelSensor myPlantEnvironmentCO2AirLevelSensor = CO2AirLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentCO2AirLevelSensorName+myID));
					modules.put(myPlantEnvironmentCO2AirLevelSensorName , myPlantEnvironmentCO2AirLevelSensor);
				}
			}
			//Water
			{
				//WaterRS
				{
					DirtyWaterInFlowRateSensor myWaterRSDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSDirtyWaterInFlowRateSensorName+myID));
					modules.put(myWaterRSDirtyWaterInFlowRateSensorName , myWaterRSDirtyWaterInFlowRateSensor);
					GreyWaterInFlowRateSensor myWaterRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSGreyWaterInFlowRateSensorName+myID));
					modules.put(myWaterRSGreyWaterInFlowRateSensorName , myWaterRSGreyWaterInFlowRateSensor);
					PowerInFlowRateSensor myWaterRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPowerInFlowRateSensorName+myID));
					modules.put(myWaterRSPowerInFlowRateSensorName , myWaterRSPowerInFlowRateSensor);
					PotableWaterOutFlowRateSensor myWaterRSPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPotableWaterOutFlowRateSensorName+myID));
					modules.put(myWaterRSPotableWaterOutFlowRateSensorName , myWaterRSPotableWaterOutFlowRateSensor);
				}
				//Stores
				{
					PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPotableWaterStoreLevelSensorName+myID));
					modules.put(myPotableWaterStoreLevelSensorName , myPotableWaterStoreLevelSensor);
					GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myGreyWaterStoreLevelSensorName+myID));
					modules.put(myGreyWaterStoreLevelSensorName , myGreyWaterStoreLevelSensor);
					DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myDirtyWaterStoreLevelSensorName+myID));
					modules.put(myDirtyWaterStoreLevelSensorName , myDirtyWaterStoreLevelSensor);
				}
			}
			//Food
			{
				//BiomassRS
				{
					AirInFlowRateSensor myBiomassRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirInFlowRateSensorName+myID));
					modules.put(myBiomassRSAirInFlowRateSensorName , myBiomassRSAirInFlowRateSensor);
					AirOutFlowRateSensor myBiomassRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirOutFlowRateSensorName+myID));
					modules.put(myBiomassRSAirOutFlowRateSensorName , myBiomassRSAirOutFlowRateSensor);
					PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPotableWaterInFlowRateSensorName+myID));
					modules.put(myBiomassRSPotableWaterInFlowRateSensorName , myBiomassRSPotableWaterInFlowRateSensor);
					GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSGreyWaterInFlowRateSensorName+myID));
					modules.put(myBiomassRSGreyWaterInFlowRateSensorName , myBiomassRSGreyWaterInFlowRateSensor);
					BiomassOutFlowRateSensor myBiomassRSBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSBiomassOutFlowRateSensorName+myID));
					modules.put(myBiomassRSBiomassOutFlowRateSensorName , myBiomassRSBiomassOutFlowRateSensor);
					PowerInFlowRateSensor myBiomassRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPowerInFlowRateSensorName+myID));
					modules.put(myBiomassRSPowerInFlowRateSensorName , myBiomassRSPowerInFlowRateSensor);
				}
				//Food Processor
				{
					PowerInFlowRateSensor myFoodProcessorPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorPowerInFlowRateSensorName+myID));
					modules.put(myFoodProcessorPowerInFlowRateSensorName , myFoodProcessorPowerInFlowRateSensor);
					BiomassInFlowRateSensor myFoodProcessorBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorBiomassInFlowRateSensorName+myID));
					modules.put(myFoodProcessorBiomassInFlowRateSensorName , myFoodProcessorBiomassInFlowRateSensor);
					FoodOutFlowRateSensor myFoodProcessorFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorFoodOutFlowRateSensorName+myID));
					modules.put(myFoodProcessorFoodOutFlowRateSensorName , myFoodProcessorFoodOutFlowRateSensor);
				}
				//Stores
				{
					BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassStoreLevelSensorName+myID));
					modules.put(myBiomassStoreLevelSensorName , myBiomassStoreLevelSensor);
					FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodStoreLevelSensorName+myID));
					modules.put(myFoodStoreLevelSensorName , myFoodStoreLevelSensor);
				}
			}
			//Framework
			{
				//Accumulator
				{
					CO2AirEnvironmentInFlowRateSensor myAccumulatorCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirEnvironmentInFlowRateSensorName+myID));
					modules.put(myAccumulatorCO2AirEnvironmentInFlowRateSensorName , myAccumulatorCO2AirEnvironmentInFlowRateSensor);
					O2AirEnvironmentInFlowRateSensor myAccumulatorO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirEnvironmentInFlowRateSensorName+myID));
					modules.put(myAccumulatorO2AirEnvironmentInFlowRateSensorName , myAccumulatorO2AirEnvironmentInFlowRateSensor);
					CO2AirStoreOutFlowRateSensor myAccumulatorCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirStoreOutFlowRateSensorName+myID));
					modules.put(myAccumulatorCO2AirStoreOutFlowRateSensorName , myAccumulatorCO2AirStoreOutFlowRateSensor);
					O2AirStoreOutFlowRateSensor myAccumulatorO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirStoreOutFlowRateSensorName+myID));
					modules.put(myAccumulatorO2AirStoreOutFlowRateSensorName , myAccumulatorO2AirStoreOutFlowRateSensor);
				}
				//Injector
				{
					CO2AirStoreInFlowRateSensor myInjectorCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirStoreInFlowRateSensorName+myID));
					modules.put(myInjectorCO2AirStoreInFlowRateSensorName , myInjectorCO2AirStoreInFlowRateSensor);
					O2AirStoreInFlowRateSensor myInjectorO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirStoreInFlowRateSensorName+myID));
					modules.put(myInjectorO2AirStoreInFlowRateSensorName , myInjectorO2AirStoreInFlowRateSensor);
					CO2AirEnvironmentOutFlowRateSensor myInjectorCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirEnvironmentOutFlowRateSensorName+myID));
					modules.put(myInjectorCO2AirEnvironmentOutFlowRateSensorName , myInjectorCO2AirEnvironmentOutFlowRateSensor);
					O2AirEnvironmentOutFlowRateSensor myInjectorO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirEnvironmentOutFlowRateSensorName+myID));
					modules.put(myInjectorO2AirEnvironmentOutFlowRateSensorName , myInjectorO2AirEnvironmentOutFlowRateSensor);
				}
			}
			System.out.println("BioHolder: Collecting actuator references to modules...");
			//Air
			{
				//AirRS
				{
					PowerInFlowRateActuator myAirRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSPowerInFlowRateActuatorName+myID));
					modules.put(myAirRSPowerInFlowRateActuatorName , myAirRSPowerInFlowRateActuator);
					AirInFlowRateActuator myAirRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirInFlowRateActuatorName+myID));
					modules.put(myAirRSAirInFlowRateActuatorName , myAirRSAirInFlowRateActuator);
					AirOutFlowRateActuator myAirRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirOutFlowRateActuatorName+myID));
					modules.put(myAirRSAirOutFlowRateActuatorName , myAirRSAirOutFlowRateActuator);
					O2OutFlowRateActuator myAirRSO2OutFlowRateActuator = O2OutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSO2OutFlowRateActuatorName+myID));
					modules.put(myAirRSO2OutFlowRateActuatorName , myAirRSO2OutFlowRateActuator);
					CO2InFlowRateActuator myAirRSCO2InFlowRateActuator = CO2InFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2InFlowRateActuatorName+myID));
					modules.put(myAirRSCO2InFlowRateActuatorName , myAirRSCO2InFlowRateActuator);
					CO2OutFlowRateActuator myAirRSCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2OutFlowRateActuatorName+myID));
					modules.put(myAirRSCO2OutFlowRateActuatorName , myAirRSCO2OutFlowRateActuator);
				}
				//Stores
				{
					O2StoreLevelActuator myO2StoreLevelActuator = O2StoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myO2StoreLevelActuatorName+myID));
					modules.put(myO2StoreLevelActuatorName , myO2StoreLevelActuator);
					CO2StoreLevelActuator myCO2StoreLevelActuator = CO2StoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCO2StoreLevelActuatorName+myID));
					modules.put(myCO2StoreLevelActuatorName , myCO2StoreLevelActuator);
				}
			}
			//Power
			{
				//PowerPS
				{
					PowerOutFlowRateActuator myPowerPSPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPowerPSPowerOutFlowRateActuatorName+myID));
					modules.put(myPowerPSPowerOutFlowRateActuatorName , myPowerPSPowerOutFlowRateActuator);
				}
				//Stores
				{
					PowerStoreLevelActuator myPowerStoreLevelActuator = PowerStoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPowerStoreLevelActuatorName+myID));
					modules.put(myPowerStoreLevelActuatorName , myPowerStoreLevelActuator);
				}
			}
			//Environment
			{
				//Crew
				{
					OtherAirLevelActuator myCrewEnvironmentOtherAirLevelActuator = OtherAirLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentOtherAirLevelActuatorName+myID));
					modules.put(myCrewEnvironmentOtherAirLevelActuatorName , myCrewEnvironmentOtherAirLevelActuator);
					O2AirLevelActuator myCrewEnvironmentO2AirLevelActuator = O2AirLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentO2AirLevelActuatorName+myID));
					modules.put(myCrewEnvironmentO2AirLevelActuatorName , myCrewEnvironmentO2AirLevelActuator);
					CO2AirLevelActuator myCrewEnvironmentCO2AirLevelActuator = CO2AirLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentCO2AirLevelActuatorName+myID));
					modules.put(myCrewEnvironmentCO2AirLevelActuatorName , myCrewEnvironmentCO2AirLevelActuator);
				}
				//Plant
				{
					OtherAirLevelActuator myPlantEnvironmentOtherAirLevelActuator = OtherAirLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentOtherAirLevelActuatorName+myID));
					modules.put(myPlantEnvironmentOtherAirLevelActuatorName , myPlantEnvironmentOtherAirLevelActuator);
					O2AirLevelActuator myPlantEnvironmentO2AirLevelActuator = O2AirLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentO2AirLevelActuatorName+myID));
					modules.put(myPlantEnvironmentO2AirLevelActuatorName , myPlantEnvironmentO2AirLevelActuator);
					CO2AirLevelActuator myPlantEnvironmentCO2AirLevelActuator = CO2AirLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentCO2AirLevelActuatorName+myID));
					modules.put(myPlantEnvironmentCO2AirLevelActuatorName , myPlantEnvironmentCO2AirLevelActuator);
				}
			}
			//Water
			{
				//WaterRS
				{
					DirtyWaterInFlowRateActuator myWaterRSDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSDirtyWaterInFlowRateActuatorName+myID));
					modules.put(myWaterRSDirtyWaterInFlowRateActuatorName , myWaterRSDirtyWaterInFlowRateActuator);
					GreyWaterInFlowRateActuator myWaterRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSGreyWaterInFlowRateActuatorName+myID));
					modules.put(myWaterRSGreyWaterInFlowRateActuatorName , myWaterRSGreyWaterInFlowRateActuator);
					PowerInFlowRateActuator myWaterRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPowerInFlowRateActuatorName+myID));
					modules.put(myWaterRSPowerInFlowRateActuatorName , myWaterRSPowerInFlowRateActuator);
					PotableWaterOutFlowRateActuator myWaterRSPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPotableWaterOutFlowRateActuatorName+myID));
					modules.put(myWaterRSPotableWaterOutFlowRateActuatorName , myWaterRSPotableWaterOutFlowRateActuator);
				}
				//Stores
				{
					PotableWaterStoreLevelActuator myPotableWaterStoreLevelActuator = PotableWaterStoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPotableWaterStoreLevelActuatorName+myID));
					modules.put(myPotableWaterStoreLevelActuatorName , myPotableWaterStoreLevelActuator);
					GreyWaterStoreLevelActuator myGreyWaterStoreLevelActuator = GreyWaterStoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myGreyWaterStoreLevelActuatorName+myID));
					modules.put(myGreyWaterStoreLevelActuatorName , myGreyWaterStoreLevelActuator);
					DirtyWaterStoreLevelActuator myDirtyWaterStoreLevelActuator = DirtyWaterStoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myDirtyWaterStoreLevelActuatorName+myID));
					modules.put(myDirtyWaterStoreLevelActuatorName , myDirtyWaterStoreLevelActuator);
				}
			}
			//Food
			{
				//BiomassRS
				{
					AirInFlowRateActuator myBiomassRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirInFlowRateActuatorName+myID));
					modules.put(myBiomassRSAirInFlowRateActuatorName , myBiomassRSAirInFlowRateActuator);
					AirOutFlowRateActuator myBiomassRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirOutFlowRateActuatorName+myID));
					modules.put(myBiomassRSAirOutFlowRateActuatorName , myBiomassRSAirOutFlowRateActuator);
					PotableWaterInFlowRateActuator myBiomassRSPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPotableWaterInFlowRateActuatorName+myID));
					modules.put(myBiomassRSPotableWaterInFlowRateActuatorName , myBiomassRSPotableWaterInFlowRateActuator);
					GreyWaterInFlowRateActuator myBiomassRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSGreyWaterInFlowRateActuatorName+myID));
					modules.put(myBiomassRSGreyWaterInFlowRateActuatorName , myBiomassRSGreyWaterInFlowRateActuator);
					BiomassOutFlowRateActuator myBiomassRSBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSBiomassOutFlowRateActuatorName+myID));
					modules.put(myBiomassRSBiomassOutFlowRateActuatorName , myBiomassRSBiomassOutFlowRateActuator);
					PowerInFlowRateActuator myBiomassRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPowerInFlowRateActuatorName+myID));
					modules.put(myBiomassRSPowerInFlowRateActuatorName , myBiomassRSPowerInFlowRateActuator);
				}
				//Food Processor
				{
					PowerInFlowRateActuator myFoodProcessorPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorPowerInFlowRateActuatorName+myID));
					modules.put(myFoodProcessorPowerInFlowRateActuatorName , myFoodProcessorPowerInFlowRateActuator);
					BiomassInFlowRateActuator myFoodProcessorBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorBiomassInFlowRateActuatorName+myID));
					modules.put(myFoodProcessorBiomassInFlowRateActuatorName , myFoodProcessorBiomassInFlowRateActuator);
					FoodOutFlowRateActuator myFoodProcessorFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorFoodOutFlowRateActuatorName+myID));
					modules.put(myFoodProcessorFoodOutFlowRateActuatorName , myFoodProcessorFoodOutFlowRateActuator);
				}
				//Stores
				{
					BiomassStoreLevelActuator myBiomassStoreLevelActuator = BiomassStoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassStoreLevelActuatorName+myID));
					modules.put(myBiomassStoreLevelActuatorName , myBiomassStoreLevelActuator);
					FoodStoreLevelActuator myFoodStoreLevelActuator = FoodStoreLevelActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodStoreLevelActuatorName+myID));
					modules.put(myFoodStoreLevelActuatorName , myFoodStoreLevelActuator);
				}
			}
			//Framework
			{
				//Accumulator
				{
					CO2AirEnvironmentInFlowRateActuator myAccumulatorCO2AirEnvironmentInFlowRateActuator = CO2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName+myID));
					modules.put(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName , myAccumulatorCO2AirEnvironmentInFlowRateActuator);
					O2AirEnvironmentInFlowRateActuator myAccumulatorO2AirEnvironmentInFlowRateActuator = O2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirEnvironmentInFlowRateActuatorName+myID));
					modules.put(myAccumulatorO2AirEnvironmentInFlowRateActuatorName , myAccumulatorO2AirEnvironmentInFlowRateActuator);
					CO2AirStoreOutFlowRateActuator myAccumulatorCO2AirStoreOutFlowRateActuator = CO2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirStoreOutFlowRateActuatorName+myID));
					modules.put(myAccumulatorCO2AirStoreOutFlowRateActuatorName , myAccumulatorCO2AirStoreOutFlowRateActuator);
					O2AirStoreOutFlowRateActuator myAccumulatorO2AirStoreOutFlowRateActuator = O2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirStoreOutFlowRateActuatorName+myID));
					modules.put(myAccumulatorO2AirStoreOutFlowRateActuatorName , myAccumulatorO2AirStoreOutFlowRateActuator);
				}
				//Injector
				{
					CO2AirStoreInFlowRateActuator myInjectorCO2AirStoreInFlowRateActuator = CO2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirStoreInFlowRateActuatorName+myID));
					modules.put(myInjectorCO2AirStoreInFlowRateActuatorName , myInjectorCO2AirStoreInFlowRateActuator);
					O2AirStoreInFlowRateActuator myInjectorO2AirStoreInFlowRateActuator = O2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirStoreInFlowRateActuatorName+myID));
					modules.put(myInjectorO2AirStoreInFlowRateActuatorName , myInjectorO2AirStoreInFlowRateActuator);
					CO2AirEnvironmentOutFlowRateActuator myInjectorCO2AirEnvironmentOutFlowRateActuator = CO2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirEnvironmentOutFlowRateActuatorName+myID));
					modules.put(myInjectorCO2AirEnvironmentOutFlowRateActuatorName , myInjectorCO2AirEnvironmentOutFlowRateActuator);
					O2AirEnvironmentOutFlowRateActuator myInjectorO2AirEnvironmentOutFlowRateActuator = O2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirEnvironmentOutFlowRateActuatorName+myID));
					modules.put(myInjectorO2AirEnvironmentOutFlowRateActuatorName , myInjectorO2AirEnvironmentOutFlowRateActuator);
				}
			}
			System.out.println("BioHolder: Collecting framework references to modules...");
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

