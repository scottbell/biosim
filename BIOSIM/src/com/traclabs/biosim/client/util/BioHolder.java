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
	public final static String H2StoreName = "H2Store";
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
	public final static String myAirRSH2InFlowRateSensorName = "AirRSH2InFlowRateSensor";
	public final static String myAirRSH2OutFlowRateSensorName = "AirRSH2OutFlowRateSensor";
	public final static String myAirRSPotableWaterInFlowRateSensorName = "AirRSPotableWaterInFlowRateSensor";
	public final static String myAirRSPotableWaterOutFlowRateSensorName = "AirRSPotableWaterOutFlowRateSensor";
	//Stores
	public final static String myO2StoreLevelSensorName = "O2StoreLevelSensor";
	public final static String myCO2StoreLevelSensorName = "CO2StoreLevelSensor";
	public final static String myH2StoreLevelSensorName = "H2StoreLevelSensor";
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
	public final static String myCrewEnvironmentOtherAirConcentrationSensorName = "CrewEnvironmentOtherAirConcentrationSensor";
	public final static String myCrewEnvironmentCO2AirConcentrationSensorName = "CrewEnvironmentCO2AirConcentrationSensor";
	public final static String myCrewEnvironmentO2AirConcentrationSensorName = "CrewEnvironmentO2AirConcentrationSensor";
	public final static String myCrewEnvironmentWaterAirConcentrationSensorName = "CrewEnvironmentWaterAirConcentrationSensor";
	public final static String myCrewEnvironmentOtherAirPressureSensorName = "CrewEnvironmentOtherAirPressureSensor";
	public final static String myCrewEnvironmentCO2AirPressureSensorName = "CrewEnvironmentCO2AirPressureSensor";
	public final static String myCrewEnvironmentO2AirPressureSensorName = "CrewEnvironmentO2AirPressureSensor";
	public final static String myCrewEnvironmentWaterAirPressureSensorName = "CrewEnvironmentWaterAirPressureSensor";
	//Plant
	public final static String myPlantEnvironmentOtherAirConcentrationSensorName = "PlantEnvironmentOtherAirConcentrationSensor";
	public final static String myPlantEnvironmentCO2AirConcentrationSensorName = "PlantEnvironmentCO2AirConcentrationSensor";
	public final static String myPlantEnvironmentO2AirConcentrationSensorName = "PlantEnvironmentO2AirConcentrationSensor";
	public final static String myPlantEnvironmentWaterAirConcentrationSensorName = "PlantEnvironmentWaterAirConcentrationSensor";
	public final static String myPlantEnvironmentOtherAirPressureSensorName = "PlantEnvironmentOtherAirPressureSensor";
	public final static String myPlantEnvironmentCO2AirPressureSensorName = "PlantEnvironmentCO2AirPressureSensor";
	public final static String myPlantEnvironmentO2AirPressureSensorName = "PlantEnvironmentO2AirPressureSensor";
	public final static String myPlantEnvironmentWaterAirPressureSensorName = "PlantEnvironmentWaterAirPressureSensor";
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
	public final static String myAirRSH2InFlowRateActuatorName = "AirRSH2InFlowRateActuator";
	public final static String myAirRSH2OutFlowRateActuatorName = "AirRSH2OutFlowRateActuator";
	public final static String myAirRSPotableWaterInFlowRateActuatorName = "AirRSPotableWaterInFlowRateActuator";
	public final static String myAirRSPotableWaterOutFlowRateActuatorName = "AirRSPotableWaterOutFlowRateActuator";
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

	public static BioModule[] getSensors(){
		collectReferences();
		BioModule[] arraySensors = new GenericSensor[sensors.size()];
		return (BioModule[])(sensors.values().toArray(arraySensors));
	}

	public static BioModule[] getActuators(){
		collectReferences();
		BioModule[] arrayActuators = new GenericActuator[actuators.size()];
		return (BioModule[])(actuators.values().toArray(arrayActuators));
	}

	public static BioDriver getBioDriver(){
		collectReferences();
		return myBioDriver;
	}

	public static void setID(int pID){
		myID = pID;
	}
	
	private static Map fetchModules(String[] moduleNameArray) throws org.omg.CORBA.UserException{
		Map newMap = new Hashtable();
		for (int i = 0; i < moduleNameArray.length; i++){
			BioModule module = BioModuleHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleNameArray[i]));
			newMap.put(moduleNameArray[i], module);
		}
		return newMap;
	}
	
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
			myBioDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str("BioDriver"));
			String[] myModuleNameArray = myBioDriver.getModuleNames();
			String[] mySensorNameArray = myBioDriver.getSensorNames();
			String[] myActuatorNameArray = myBioDriver.getActuatorNames();
			if ((myModuleNameArray.length <= 0) || (mySensorNameArray.length <= 0) || (myActuatorNameArray.length <= 0))
				throw new Exception();
			modules = fetchModules(myModuleNameArray);
			sensors = fetchModules(mySensorNameArray);
			actuators = fetchModules(myActuatorNameArray);
			hasCollectedReferences = true;
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.sleepAwhile();
			collectReferences();
			return;
		}
		catch (Exception e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			e.printStackTrace();
			OrbUtils.resetInit();
			OrbUtils.sleepAwhile();
			collectReferences();
			return;
		}
	}
}

