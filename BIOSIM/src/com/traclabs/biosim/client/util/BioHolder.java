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
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.water.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.water.*;
import biosim.idl.actuator.framework.*;
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
	//Crew
	public final static String myCrewGroupDeathSensorName = "CrewGroupDeathSensor";
	public final static String myCrewGroupPotableWaterInFlowRateSensorName = "CrewGroupPotableWaterInFlowRateSensor";
	public final static String myCrewGroupGreyWaterOutFlowRateSensorName = "CrewGroupGreyWaterOutFlowRateSensor";
	public final static String myCrewGroupDirtyWaterOutFlowRateSensorName = "CrewGroupDirtyWaterOutFlowRateSensor";
	//Power
	//PowerPS
	public final static String myPowerPSPowerOutFlowRateSensorName = "PowerPSPowerOutFlowRateSensor";
	//Stores
	public final static String myPowerStoreLevelSensorName = "PowerStoreLevelSensor";
	//Environment
	//Crew
	public final static String myCrewEnvironmentOtherAirMolesSensorName = "CrewEnvironmentOtherAirMolesSensor";
	public final static String myCrewEnvironmentCO2AirMolesSensorName = "CrewEnvironmentCO2AirMolesSensor";
	public final static String myCrewEnvironmentO2AirMolesSensorName = "CrewEnvironmentO2AirMolesSensor";
	//Plant
	public final static String myPlantEnvironmentOtherAirMolesSensorName = "PlantEnvironmentOtherAirMolesSensor";
	public final static String myPlantEnvironmentCO2AirMolesSensorName = "PlantEnvironmentCO2AirMolesSensor";
	public final static String myPlantEnvironmentO2AirMolesSensorName = "PlantEnvironmentO2AirMolesSensor";
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
	public final static String myBiomassRSDirtyWaterOutFlowRateSensorName = "BiomassRSDirtyWaterOutFlowRateSensor";
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
	//Power
	//PowerPS
	public final static String myPowerPSPowerOutFlowRateActuatorName = "PowerPSPowerOutFlowRateActuator";
	//Water
	//WaterRS
	public final static String myWaterRSDirtyWaterInFlowRateActuatorName = "WaterRSDirtyWaterInFlowRateActuator";
	public final static String myWaterRSGreyWaterInFlowRateActuatorName = "WaterRSGreyWaterInFlowRateActuator";
	public final static String myWaterRSPowerInFlowRateActuatorName = "WaterRSPowerInFlowRateActuator";
	public final static String myWaterRSPotableWaterOutFlowRateActuatorName = "WaterRSPotableWaterOutFlowRateActuator";
	//Crew
	public final static String myCrewGroupPotableWaterInFlowRateActuatorName = "CrewGroupPotableWaterInFlowRateActuator";
	public final static String myCrewGroupGreyWaterOutFlowRateActuatorName = "CrewGroupGreyWaterOutFlowRateActuator";
	public final static String myCrewGroupDirtyWaterOutFlowRateActuatorName = "CrewGroupDirtyWaterOutFlowRateActuator";
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
	private static Map sensors;
	private static Map actuators;
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

	public static GenericSensor[] getSensors(){
		collectReferences();
		GenericSensor[] arraySensors = new GenericSensor[sensors.size()];
		return (GenericSensor[])(sensors.values().toArray(arraySensors));
	}

	public static GenericActuator[] getActuators(){
		collectReferences();
		GenericActuator[] arrayActuators = new GenericActuator[actuators.size()];
		return (GenericActuator[])(actuators.values().toArray(arrayActuators));
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
			if (sensors == null)
				sensors = new Hashtable();
			if (actuators == null)
				actuators = new Hashtable();
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
					sensors.put(myAirRSPowerInFlowRateSensorName , myAirRSPowerInFlowRateSensor);
					AirInFlowRateSensor myAirRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirInFlowRateSensorName+myID));
					modules.put(myAirRSAirInFlowRateSensorName , myAirRSAirInFlowRateSensor);
					sensors.put(myAirRSAirInFlowRateSensorName , myAirRSAirInFlowRateSensor);
					AirOutFlowRateSensor myAirRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirOutFlowRateSensorName+myID));
					modules.put(myAirRSAirOutFlowRateSensorName , myAirRSAirOutFlowRateSensor);
					sensors.put(myAirRSAirOutFlowRateSensorName , myAirRSAirOutFlowRateSensor);
					O2OutFlowRateSensor myAirRSO2OutFlowRateSensor = O2OutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSO2OutFlowRateSensorName+myID));
					modules.put(myAirRSO2OutFlowRateSensorName , myAirRSO2OutFlowRateSensor);
					sensors.put(myAirRSO2OutFlowRateSensorName , myAirRSO2OutFlowRateSensor);
					CO2InFlowRateSensor myAirRSCO2InFlowRateSensor = CO2InFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2InFlowRateSensorName+myID));
					modules.put(myAirRSCO2InFlowRateSensorName , myAirRSCO2InFlowRateSensor);
					sensors.put(myAirRSCO2InFlowRateSensorName , myAirRSCO2InFlowRateSensor);
					CO2OutFlowRateSensor myAirRSCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2OutFlowRateSensorName+myID));
					modules.put(myAirRSCO2OutFlowRateSensorName , myAirRSCO2OutFlowRateSensor);
					sensors.put(myAirRSCO2OutFlowRateSensorName , myAirRSCO2OutFlowRateSensor);
				}
				//Stores
				{
					O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myO2StoreLevelSensorName+myID));
					modules.put(myO2StoreLevelSensorName , myO2StoreLevelSensor);
					sensors.put(myO2StoreLevelSensorName , myO2StoreLevelSensor);
					CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCO2StoreLevelSensorName+myID));
					modules.put(myCO2StoreLevelSensorName , myCO2StoreLevelSensor);
					sensors.put(myCO2StoreLevelSensorName , myCO2StoreLevelSensor);
				}
			}
			//Power
			{
				//PowerPS
				{
					PowerOutFlowRateSensor myPowerPSPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPowerPSPowerOutFlowRateSensorName+myID));
					modules.put(myPowerPSPowerOutFlowRateSensorName , myPowerPSPowerOutFlowRateSensor);
					sensors.put(myPowerPSPowerOutFlowRateSensorName , myPowerPSPowerOutFlowRateSensor);
				}
				//Stores
				{
					PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPowerStoreLevelSensorName+myID));
					modules.put(myPowerStoreLevelSensorName , myPowerStoreLevelSensor);
					sensors.put(myPowerStoreLevelSensorName , myPowerStoreLevelSensor);
				}
			}
			//Crew
			{
				CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewGroupDeathSensorName+myID));
				PotableWaterInFlowRateSensor myCrewGroupPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewGroupPotableWaterInFlowRateSensorName+myID));
				GreyWaterOutFlowRateSensor myCrewGroupGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewGroupGreyWaterOutFlowRateSensorName+myID));
				DirtyWaterOutFlowRateSensor myCrewGroupDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewGroupDirtyWaterOutFlowRateSensorName+myID));
				
				modules.put(myCrewGroupDeathSensorName , myCrewGroupDeathSensor);
				modules.put(myCrewGroupPotableWaterInFlowRateSensorName , myCrewGroupPotableWaterInFlowRateSensor);
				modules.put(myCrewGroupGreyWaterOutFlowRateSensorName , myCrewGroupGreyWaterOutFlowRateSensor);
				modules.put(myCrewGroupDirtyWaterOutFlowRateSensorName , myCrewGroupDirtyWaterOutFlowRateSensor);
				
				sensors.put(myCrewGroupDeathSensorName , myCrewGroupDeathSensor);
				sensors.put(myCrewGroupPotableWaterInFlowRateSensorName , myCrewGroupPotableWaterInFlowRateSensor);
				sensors.put(myCrewGroupGreyWaterOutFlowRateSensorName , myCrewGroupGreyWaterOutFlowRateSensor);
				sensors.put(myCrewGroupDirtyWaterOutFlowRateSensorName , myCrewGroupDirtyWaterOutFlowRateSensor);
				
			}
			//Environment
			{
				//Crew
				{
					OtherAirMolesSensor myCrewEnvironmentOtherAirMolesSensor = OtherAirMolesSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentOtherAirMolesSensorName+myID));
					modules.put(myCrewEnvironmentOtherAirMolesSensorName , myCrewEnvironmentOtherAirMolesSensor);
					sensors.put(myCrewEnvironmentOtherAirMolesSensorName , myCrewEnvironmentOtherAirMolesSensor);
					O2AirMolesSensor myCrewEnvironmentO2AirMolesSensor = O2AirMolesSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentO2AirMolesSensorName+myID));
					modules.put(myCrewEnvironmentO2AirMolesSensorName , myCrewEnvironmentO2AirMolesSensor);
					sensors.put(myCrewEnvironmentO2AirMolesSensorName , myCrewEnvironmentO2AirMolesSensor);
					CO2AirMolesSensor myCrewEnvironmentCO2AirMolesSensor = CO2AirMolesSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewEnvironmentCO2AirMolesSensorName+myID));
					modules.put(myCrewEnvironmentCO2AirMolesSensorName , myCrewEnvironmentCO2AirMolesSensor);
					sensors.put(myCrewEnvironmentCO2AirMolesSensorName , myCrewEnvironmentCO2AirMolesSensor);
				}
				//Plant
				{
					OtherAirMolesSensor myPlantEnvironmentOtherAirMolesSensor = OtherAirMolesSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentOtherAirMolesSensorName+myID));
					modules.put(myPlantEnvironmentOtherAirMolesSensorName , myPlantEnvironmentOtherAirMolesSensor);
					sensors.put(myPlantEnvironmentOtherAirMolesSensorName , myPlantEnvironmentOtherAirMolesSensor);
					O2AirMolesSensor myPlantEnvironmentO2AirMolesSensor = O2AirMolesSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentO2AirMolesSensorName+myID));
					modules.put(myPlantEnvironmentO2AirMolesSensorName , myPlantEnvironmentO2AirMolesSensor);
					sensors.put(myPlantEnvironmentO2AirMolesSensorName , myPlantEnvironmentO2AirMolesSensor);
					CO2AirMolesSensor myPlantEnvironmentCO2AirMolesSensor = CO2AirMolesSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPlantEnvironmentCO2AirMolesSensorName+myID));
					modules.put(myPlantEnvironmentCO2AirMolesSensorName , myPlantEnvironmentCO2AirMolesSensor);
					sensors.put(myPlantEnvironmentCO2AirMolesSensorName , myPlantEnvironmentCO2AirMolesSensor);
				}
			}
			//Water
			{
				//WaterRS
				{
					DirtyWaterInFlowRateSensor myWaterRSDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSDirtyWaterInFlowRateSensorName+myID));
					modules.put(myWaterRSDirtyWaterInFlowRateSensorName , myWaterRSDirtyWaterInFlowRateSensor);
					sensors.put(myWaterRSDirtyWaterInFlowRateSensorName , myWaterRSDirtyWaterInFlowRateSensor);
					GreyWaterInFlowRateSensor myWaterRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSGreyWaterInFlowRateSensorName+myID));
					modules.put(myWaterRSGreyWaterInFlowRateSensorName , myWaterRSGreyWaterInFlowRateSensor);
					sensors.put(myWaterRSGreyWaterInFlowRateSensorName , myWaterRSGreyWaterInFlowRateSensor);
					PowerInFlowRateSensor myWaterRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPowerInFlowRateSensorName+myID));
					modules.put(myWaterRSPowerInFlowRateSensorName , myWaterRSPowerInFlowRateSensor);
					sensors.put(myWaterRSPowerInFlowRateSensorName , myWaterRSPowerInFlowRateSensor);
					PotableWaterOutFlowRateSensor myWaterRSPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPotableWaterOutFlowRateSensorName+myID));
					modules.put(myWaterRSPotableWaterOutFlowRateSensorName , myWaterRSPotableWaterOutFlowRateSensor);
					sensors.put(myWaterRSPotableWaterOutFlowRateSensorName , myWaterRSPotableWaterOutFlowRateSensor);
				}
				//Stores
				{
					PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPotableWaterStoreLevelSensorName+myID));
					modules.put(myPotableWaterStoreLevelSensorName , myPotableWaterStoreLevelSensor);
					sensors.put(myPotableWaterStoreLevelSensorName , myPotableWaterStoreLevelSensor);
					GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myGreyWaterStoreLevelSensorName+myID));
					modules.put(myGreyWaterStoreLevelSensorName , myGreyWaterStoreLevelSensor);
					sensors.put(myGreyWaterStoreLevelSensorName , myGreyWaterStoreLevelSensor);
					DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myDirtyWaterStoreLevelSensorName+myID));
					modules.put(myDirtyWaterStoreLevelSensorName , myDirtyWaterStoreLevelSensor);
					sensors.put(myDirtyWaterStoreLevelSensorName , myDirtyWaterStoreLevelSensor);
				}
			}
			//Food
			{
				//BiomassRS
				{
					AirInFlowRateSensor myBiomassRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirInFlowRateSensorName+myID));
					modules.put(myBiomassRSAirInFlowRateSensorName , myBiomassRSAirInFlowRateSensor);
					sensors.put(myBiomassRSAirInFlowRateSensorName , myBiomassRSAirInFlowRateSensor);
					AirOutFlowRateSensor myBiomassRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirOutFlowRateSensorName+myID));
					modules.put(myBiomassRSAirOutFlowRateSensorName , myBiomassRSAirOutFlowRateSensor);
					sensors.put(myBiomassRSAirOutFlowRateSensorName , myBiomassRSAirOutFlowRateSensor);
					PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPotableWaterInFlowRateSensorName+myID));
					modules.put(myBiomassRSPotableWaterInFlowRateSensorName , myBiomassRSPotableWaterInFlowRateSensor);
					sensors.put(myBiomassRSPotableWaterInFlowRateSensorName , myBiomassRSPotableWaterInFlowRateSensor);
					GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSGreyWaterInFlowRateSensorName+myID));
					modules.put(myBiomassRSGreyWaterInFlowRateSensorName , myBiomassRSGreyWaterInFlowRateSensor);
					sensors.put(myBiomassRSGreyWaterInFlowRateSensorName , myBiomassRSGreyWaterInFlowRateSensor);
					DirtyWaterOutFlowRateSensor myBiomassRSDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSDirtyWaterOutFlowRateSensorName+myID));
					modules.put(myBiomassRSDirtyWaterOutFlowRateSensorName , myBiomassRSDirtyWaterOutFlowRateSensor);
					sensors.put(myBiomassRSDirtyWaterOutFlowRateSensorName , myBiomassRSDirtyWaterOutFlowRateSensor);
					BiomassOutFlowRateSensor myBiomassRSBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSBiomassOutFlowRateSensorName+myID));
					modules.put(myBiomassRSBiomassOutFlowRateSensorName , myBiomassRSBiomassOutFlowRateSensor);
					sensors.put(myBiomassRSBiomassOutFlowRateSensorName , myBiomassRSBiomassOutFlowRateSensor);
					PowerInFlowRateSensor myBiomassRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPowerInFlowRateSensorName+myID));
					modules.put(myBiomassRSPowerInFlowRateSensorName , myBiomassRSPowerInFlowRateSensor);
					sensors.put(myBiomassRSPowerInFlowRateSensorName , myBiomassRSPowerInFlowRateSensor);
				}
				//Food Processor
				{
					PowerInFlowRateSensor myFoodProcessorPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorPowerInFlowRateSensorName+myID));
					modules.put(myFoodProcessorPowerInFlowRateSensorName , myFoodProcessorPowerInFlowRateSensor);
					sensors.put(myFoodProcessorPowerInFlowRateSensorName , myFoodProcessorPowerInFlowRateSensor);
					BiomassInFlowRateSensor myFoodProcessorBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorBiomassInFlowRateSensorName+myID));
					modules.put(myFoodProcessorBiomassInFlowRateSensorName , myFoodProcessorBiomassInFlowRateSensor);
					sensors.put(myFoodProcessorBiomassInFlowRateSensorName , myFoodProcessorBiomassInFlowRateSensor);
					FoodOutFlowRateSensor myFoodProcessorFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorFoodOutFlowRateSensorName+myID));
					modules.put(myFoodProcessorFoodOutFlowRateSensorName , myFoodProcessorFoodOutFlowRateSensor);
					sensors.put(myFoodProcessorFoodOutFlowRateSensorName , myFoodProcessorFoodOutFlowRateSensor);
				}
				//Stores
				{
					BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassStoreLevelSensorName+myID));
					modules.put(myBiomassStoreLevelSensorName , myBiomassStoreLevelSensor);
					sensors.put(myBiomassStoreLevelSensorName , myBiomassStoreLevelSensor);
					FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodStoreLevelSensorName+myID));
					modules.put(myFoodStoreLevelSensorName , myFoodStoreLevelSensor);
					sensors.put(myFoodStoreLevelSensorName , myFoodStoreLevelSensor);
				}
			}
			//Framework
			{
				//Accumulator
				{
					CO2AirEnvironmentInFlowRateSensor myAccumulatorCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirEnvironmentInFlowRateSensorName+myID));
					modules.put(myAccumulatorCO2AirEnvironmentInFlowRateSensorName , myAccumulatorCO2AirEnvironmentInFlowRateSensor);
					sensors.put(myAccumulatorCO2AirEnvironmentInFlowRateSensorName , myAccumulatorCO2AirEnvironmentInFlowRateSensor);
					O2AirEnvironmentInFlowRateSensor myAccumulatorO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirEnvironmentInFlowRateSensorName+myID));
					modules.put(myAccumulatorO2AirEnvironmentInFlowRateSensorName , myAccumulatorO2AirEnvironmentInFlowRateSensor);
					sensors.put(myAccumulatorO2AirEnvironmentInFlowRateSensorName , myAccumulatorO2AirEnvironmentInFlowRateSensor);
					CO2AirStoreOutFlowRateSensor myAccumulatorCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirStoreOutFlowRateSensorName+myID));
					modules.put(myAccumulatorCO2AirStoreOutFlowRateSensorName , myAccumulatorCO2AirStoreOutFlowRateSensor);
					sensors.put(myAccumulatorCO2AirStoreOutFlowRateSensorName , myAccumulatorCO2AirStoreOutFlowRateSensor);
					O2AirStoreOutFlowRateSensor myAccumulatorO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirStoreOutFlowRateSensorName+myID));
					modules.put(myAccumulatorO2AirStoreOutFlowRateSensorName , myAccumulatorO2AirStoreOutFlowRateSensor);
					sensors.put(myAccumulatorO2AirStoreOutFlowRateSensorName , myAccumulatorO2AirStoreOutFlowRateSensor);
				}
				//Injector
				{
					CO2AirStoreInFlowRateSensor myInjectorCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirStoreInFlowRateSensorName+myID));
					modules.put(myInjectorCO2AirStoreInFlowRateSensorName , myInjectorCO2AirStoreInFlowRateSensor);
					sensors.put(myInjectorCO2AirStoreInFlowRateSensorName , myInjectorCO2AirStoreInFlowRateSensor);
					O2AirStoreInFlowRateSensor myInjectorO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirStoreInFlowRateSensorName+myID));
					modules.put(myInjectorO2AirStoreInFlowRateSensorName , myInjectorO2AirStoreInFlowRateSensor);
					sensors.put(myInjectorO2AirStoreInFlowRateSensorName , myInjectorO2AirStoreInFlowRateSensor);
					CO2AirEnvironmentOutFlowRateSensor myInjectorCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirEnvironmentOutFlowRateSensorName+myID));
					modules.put(myInjectorCO2AirEnvironmentOutFlowRateSensorName , myInjectorCO2AirEnvironmentOutFlowRateSensor);
					sensors.put(myInjectorCO2AirEnvironmentOutFlowRateSensorName , myInjectorCO2AirEnvironmentOutFlowRateSensor);
					O2AirEnvironmentOutFlowRateSensor myInjectorO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirEnvironmentOutFlowRateSensorName+myID));
					modules.put(myInjectorO2AirEnvironmentOutFlowRateSensorName , myInjectorO2AirEnvironmentOutFlowRateSensor);
					sensors.put(myInjectorO2AirEnvironmentOutFlowRateSensorName , myInjectorO2AirEnvironmentOutFlowRateSensor);
				}
			}
			System.out.println("BioHolder: Collecting actuator references to modules...");
			//Air
			{
				//AirRS
				{
					PowerInFlowRateActuator myAirRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSPowerInFlowRateActuatorName+myID));
					modules.put(myAirRSPowerInFlowRateActuatorName , myAirRSPowerInFlowRateActuator);
					actuators.put(myAirRSPowerInFlowRateActuatorName , myAirRSPowerInFlowRateActuator);
					AirInFlowRateActuator myAirRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirInFlowRateActuatorName+myID));
					modules.put(myAirRSAirInFlowRateActuatorName , myAirRSAirInFlowRateActuator);
					actuators.put(myAirRSAirInFlowRateActuatorName , myAirRSAirInFlowRateActuator);
					AirOutFlowRateActuator myAirRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSAirOutFlowRateActuatorName+myID));
					modules.put(myAirRSAirOutFlowRateActuatorName , myAirRSAirOutFlowRateActuator);
					actuators.put(myAirRSAirOutFlowRateActuatorName , myAirRSAirOutFlowRateActuator);
					O2OutFlowRateActuator myAirRSO2OutFlowRateActuator = O2OutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSO2OutFlowRateActuatorName+myID));
					modules.put(myAirRSO2OutFlowRateActuatorName , myAirRSO2OutFlowRateActuator);
					actuators.put(myAirRSO2OutFlowRateActuatorName , myAirRSO2OutFlowRateActuator);
					CO2InFlowRateActuator myAirRSCO2InFlowRateActuator = CO2InFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2InFlowRateActuatorName+myID));
					modules.put(myAirRSCO2InFlowRateActuatorName , myAirRSCO2InFlowRateActuator);
					actuators.put(myAirRSCO2InFlowRateActuatorName , myAirRSCO2InFlowRateActuator);
					CO2OutFlowRateActuator myAirRSCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAirRSCO2OutFlowRateActuatorName+myID));
					modules.put(myAirRSCO2OutFlowRateActuatorName , myAirRSCO2OutFlowRateActuator);
					actuators.put(myAirRSCO2OutFlowRateActuatorName , myAirRSCO2OutFlowRateActuator);
				}
			}
			//Power
			{
				//PowerPS
				{
					PowerOutFlowRateActuator myPowerPSPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myPowerPSPowerOutFlowRateActuatorName+myID));
					modules.put(myPowerPSPowerOutFlowRateActuatorName , myPowerPSPowerOutFlowRateActuator);
					actuators.put(myPowerPSPowerOutFlowRateActuatorName , myPowerPSPowerOutFlowRateActuator);
				}
			}
			//Water
			{
				//WaterRS
				{
					DirtyWaterInFlowRateActuator myWaterRSDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSDirtyWaterInFlowRateActuatorName+myID));
					modules.put(myWaterRSDirtyWaterInFlowRateActuatorName , myWaterRSDirtyWaterInFlowRateActuator);
					actuators.put(myWaterRSDirtyWaterInFlowRateActuatorName , myWaterRSDirtyWaterInFlowRateActuator);
					GreyWaterInFlowRateActuator myWaterRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSGreyWaterInFlowRateActuatorName+myID));
					modules.put(myWaterRSGreyWaterInFlowRateActuatorName , myWaterRSGreyWaterInFlowRateActuator);
					actuators.put(myWaterRSGreyWaterInFlowRateActuatorName , myWaterRSGreyWaterInFlowRateActuator);
					PowerInFlowRateActuator myWaterRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPowerInFlowRateActuatorName+myID));
					modules.put(myWaterRSPowerInFlowRateActuatorName , myWaterRSPowerInFlowRateActuator);
					actuators.put(myWaterRSPowerInFlowRateActuatorName , myWaterRSPowerInFlowRateActuator);
					PotableWaterOutFlowRateActuator myWaterRSPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myWaterRSPotableWaterOutFlowRateActuatorName+myID));
					modules.put(myWaterRSPotableWaterOutFlowRateActuatorName , myWaterRSPotableWaterOutFlowRateActuator);
					actuators.put(myWaterRSPotableWaterOutFlowRateActuatorName , myWaterRSPotableWaterOutFlowRateActuator);
				}
			}
			//Crew
			{
				PotableWaterInFlowRateActuator myCrewGroupPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewGroupPotableWaterInFlowRateActuatorName+myID));
				GreyWaterOutFlowRateActuator myCrewGroupGreyWaterOutFlowRateActuator = GreyWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewGroupGreyWaterOutFlowRateActuatorName+myID));
				DirtyWaterOutFlowRateActuator myCrewGroupDirtyWaterOutFlowRateActuator = DirtyWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myCrewGroupDirtyWaterOutFlowRateActuatorName+myID));
				
				modules.put(myCrewGroupPotableWaterInFlowRateActuatorName , myCrewGroupPotableWaterInFlowRateActuator);
				modules.put(myCrewGroupGreyWaterOutFlowRateActuatorName , myCrewGroupGreyWaterOutFlowRateActuator);
				modules.put(myCrewGroupDirtyWaterOutFlowRateActuatorName , myCrewGroupDirtyWaterOutFlowRateActuator);
				
				actuators.put(myCrewGroupPotableWaterInFlowRateActuatorName , myCrewGroupPotableWaterInFlowRateActuator);
				actuators.put(myCrewGroupGreyWaterOutFlowRateActuatorName , myCrewGroupGreyWaterOutFlowRateActuator);
				actuators.put(myCrewGroupDirtyWaterOutFlowRateActuatorName , myCrewGroupDirtyWaterOutFlowRateActuator);
				
			}
			//Food
			{
				//BiomassRS
				{
					AirInFlowRateActuator myBiomassRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirInFlowRateActuatorName+myID));
					modules.put(myBiomassRSAirInFlowRateActuatorName , myBiomassRSAirInFlowRateActuator);
					actuators.put(myBiomassRSAirInFlowRateActuatorName , myBiomassRSAirInFlowRateActuator);
					AirOutFlowRateActuator myBiomassRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSAirOutFlowRateActuatorName+myID));
					modules.put(myBiomassRSAirOutFlowRateActuatorName , myBiomassRSAirOutFlowRateActuator);
					actuators.put(myBiomassRSAirOutFlowRateActuatorName , myBiomassRSAirOutFlowRateActuator);
					PotableWaterInFlowRateActuator myBiomassRSPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPotableWaterInFlowRateActuatorName+myID));
					modules.put(myBiomassRSPotableWaterInFlowRateActuatorName , myBiomassRSPotableWaterInFlowRateActuator);
					actuators.put(myBiomassRSPotableWaterInFlowRateActuatorName , myBiomassRSPotableWaterInFlowRateActuator);
					GreyWaterInFlowRateActuator myBiomassRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSGreyWaterInFlowRateActuatorName+myID));
					modules.put(myBiomassRSGreyWaterInFlowRateActuatorName , myBiomassRSGreyWaterInFlowRateActuator);
					actuators.put(myBiomassRSGreyWaterInFlowRateActuatorName , myBiomassRSGreyWaterInFlowRateActuator);
					BiomassOutFlowRateActuator myBiomassRSBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSBiomassOutFlowRateActuatorName+myID));
					modules.put(myBiomassRSBiomassOutFlowRateActuatorName , myBiomassRSBiomassOutFlowRateActuator);
					actuators.put(myBiomassRSBiomassOutFlowRateActuatorName , myBiomassRSBiomassOutFlowRateActuator);
					PowerInFlowRateActuator myBiomassRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myBiomassRSPowerInFlowRateActuatorName+myID));
					modules.put(myBiomassRSPowerInFlowRateActuatorName , myBiomassRSPowerInFlowRateActuator);
					actuators.put(myBiomassRSPowerInFlowRateActuatorName , myBiomassRSPowerInFlowRateActuator);
				}
				//Food Processor
				{
					PowerInFlowRateActuator myFoodProcessorPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorPowerInFlowRateActuatorName+myID));
					modules.put(myFoodProcessorPowerInFlowRateActuatorName , myFoodProcessorPowerInFlowRateActuator);
					actuators.put(myFoodProcessorPowerInFlowRateActuatorName , myFoodProcessorPowerInFlowRateActuator);
					BiomassInFlowRateActuator myFoodProcessorBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorBiomassInFlowRateActuatorName+myID));
					modules.put(myFoodProcessorBiomassInFlowRateActuatorName , myFoodProcessorBiomassInFlowRateActuator);
					actuators.put(myFoodProcessorBiomassInFlowRateActuatorName , myFoodProcessorBiomassInFlowRateActuator);
					FoodOutFlowRateActuator myFoodProcessorFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myFoodProcessorFoodOutFlowRateActuatorName+myID));
					modules.put(myFoodProcessorFoodOutFlowRateActuatorName , myFoodProcessorFoodOutFlowRateActuator);
					actuators.put(myFoodProcessorFoodOutFlowRateActuatorName , myFoodProcessorFoodOutFlowRateActuator);
				}
			}
			//Framework
			{
				//Accumulator
				{
					CO2AirEnvironmentInFlowRateActuator myAccumulatorCO2AirEnvironmentInFlowRateActuator = CO2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName+myID));
					modules.put(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName , myAccumulatorCO2AirEnvironmentInFlowRateActuator);
					actuators.put(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName , myAccumulatorCO2AirEnvironmentInFlowRateActuator);
					O2AirEnvironmentInFlowRateActuator myAccumulatorO2AirEnvironmentInFlowRateActuator = O2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirEnvironmentInFlowRateActuatorName+myID));
					modules.put(myAccumulatorO2AirEnvironmentInFlowRateActuatorName , myAccumulatorO2AirEnvironmentInFlowRateActuator);
					actuators.put(myAccumulatorO2AirEnvironmentInFlowRateActuatorName , myAccumulatorO2AirEnvironmentInFlowRateActuator);
					CO2AirStoreOutFlowRateActuator myAccumulatorCO2AirStoreOutFlowRateActuator = CO2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorCO2AirStoreOutFlowRateActuatorName+myID));
					modules.put(myAccumulatorCO2AirStoreOutFlowRateActuatorName , myAccumulatorCO2AirStoreOutFlowRateActuator);
					actuators.put(myAccumulatorCO2AirStoreOutFlowRateActuatorName , myAccumulatorCO2AirStoreOutFlowRateActuator);
					O2AirStoreOutFlowRateActuator myAccumulatorO2AirStoreOutFlowRateActuator = O2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myAccumulatorO2AirStoreOutFlowRateActuatorName+myID));
					modules.put(myAccumulatorO2AirStoreOutFlowRateActuatorName , myAccumulatorO2AirStoreOutFlowRateActuator);
					actuators.put(myAccumulatorO2AirStoreOutFlowRateActuatorName , myAccumulatorO2AirStoreOutFlowRateActuator);
				}
				//Injector
				{
					CO2AirStoreInFlowRateActuator myInjectorCO2AirStoreInFlowRateActuator = CO2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirStoreInFlowRateActuatorName+myID));
					modules.put(myInjectorCO2AirStoreInFlowRateActuatorName , myInjectorCO2AirStoreInFlowRateActuator);
					actuators.put(myInjectorCO2AirStoreInFlowRateActuatorName , myInjectorCO2AirStoreInFlowRateActuator);
					O2AirStoreInFlowRateActuator myInjectorO2AirStoreInFlowRateActuator = O2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirStoreInFlowRateActuatorName+myID));
					modules.put(myInjectorO2AirStoreInFlowRateActuatorName , myInjectorO2AirStoreInFlowRateActuator);
					actuators.put(myInjectorO2AirStoreInFlowRateActuatorName , myInjectorO2AirStoreInFlowRateActuator);
					CO2AirEnvironmentOutFlowRateActuator myInjectorCO2AirEnvironmentOutFlowRateActuator = CO2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorCO2AirEnvironmentOutFlowRateActuatorName+myID));
					modules.put(myInjectorCO2AirEnvironmentOutFlowRateActuatorName , myInjectorCO2AirEnvironmentOutFlowRateActuator);
					actuators.put(myInjectorCO2AirEnvironmentOutFlowRateActuatorName , myInjectorCO2AirEnvironmentOutFlowRateActuator);
					O2AirEnvironmentOutFlowRateActuator myInjectorO2AirEnvironmentOutFlowRateActuator = O2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNCRef().resolve_str(myInjectorO2AirEnvironmentOutFlowRateActuatorName+myID));
					modules.put(myInjectorO2AirEnvironmentOutFlowRateActuatorName , myInjectorO2AirEnvironmentOutFlowRateActuator);
					actuators.put(myInjectorO2AirEnvironmentOutFlowRateActuatorName , myInjectorO2AirEnvironmentOutFlowRateActuator);
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

