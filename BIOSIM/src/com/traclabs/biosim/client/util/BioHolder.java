package biosim.client.util;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.simulation.waste.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.water.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.waste.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.water.*;
import biosim.idl.actuator.framework.*;
import biosim.idl.actuator.waste.*;
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
	public final static String nitrogenStoreName = "NitrogenStore";
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
	public final static String incineratorName = "Incinerator";
	public final static String dryWasteStoreName = "DryWasteStore";
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
	public final static String myNitrogenStoreLevelSensorName = "NitrogenStoreLevelSensor";
	public final static String myO2StoreOverflowSensorName = "O2StoreOverflowSensor";
	public final static String myCO2StoreOverflowSensorName = "CO2StoreOverflowSensor";
	public final static String myH2StoreOverflowSensorName = "H2StoreOverflowSensor";
	public final static String myNitrogenStoreOverflowSensorName = "NitrogenStoreOverflowSensor";
	//Crew
	public final static String myCrewGroupDeathSensorName = "CrewGroupDeathSensor";
	public final static String myCrewGroupAnyDeadSensorName = "CrewGroupAnyDeadSensor";
	public final static String myCrewGroupProductivitySensorName = "CrewGroupProductivitySensor";
	public final static String myCrewGroupPotableWaterInFlowRateSensorName = "CrewGroupPotableWaterInFlowRateSensor";
	public final static String myCrewGroupGreyWaterOutFlowRateSensorName = "CrewGroupGreyWaterOutFlowRateSensor";
	public final static String myCrewGroupDirtyWaterOutFlowRateSensorName = "CrewGroupDirtyWaterOutFlowRateSensor";
	public final static String myCrewGroupDryWasteOutFlowRateSensorName = "CrewGroupDryWasteOutFlowRateSensor";
	//Power
	//PowerPS
	public final static String myPowerPSPowerOutFlowRateSensorName = "PowerPSPowerOutFlowRateSensor";
	//Stores
	public final static String myPowerStoreLevelSensorName = "PowerStoreLevelSensor";
	public final static String myPowerStoreOverflowSensorName = "PowerStoreOverflowSensor";
	//Environment
	//Crew
	public final static String myCrewEnvironmentOtherAirConcentrationSensorName = "CrewEnvironmentOtherAirConcentrationSensor";
	public final static String myCrewEnvironmentCO2AirConcentrationSensorName = "CrewEnvironmentCO2AirConcentrationSensor";
	public final static String myCrewEnvironmentO2AirConcentrationSensorName = "CrewEnvironmentO2AirConcentrationSensor";
	public final static String myCrewEnvironmentWaterAirConcentrationSensorName = "CrewEnvironmentWaterAirConcentrationSensor";
	public final static String myCrewEnvironmentNitrogenAirConcentrationSensorName = "CrewEnvironmentNitrogenAirConcentrationSensor";
	public final static String myCrewEnvironmentOtherAirPressureSensorName = "CrewEnvironmentOtherAirPressureSensor";
	public final static String myCrewEnvironmentCO2AirPressureSensorName = "CrewEnvironmentCO2AirPressureSensor";
	public final static String myCrewEnvironmentO2AirPressureSensorName = "CrewEnvironmentO2AirPressureSensor";
	public final static String myCrewEnvironmentWaterAirPressureSensorName = "CrewEnvironmentWaterAirPressureSensor";
	public final static String myCrewEnvironmentNitrogenAirPressureSensorName = "CrewEnvironmentNitrogenAirPressureSensor";
	//Plant
	public final static String myPlantEnvironmentOtherAirConcentrationSensorName = "PlantEnvironmentOtherAirConcentrationSensor";
	public final static String myPlantEnvironmentCO2AirConcentrationSensorName = "PlantEnvironmentCO2AirConcentrationSensor";
	public final static String myPlantEnvironmentO2AirConcentrationSensorName = "PlantEnvironmentO2AirConcentrationSensor";
	public final static String myPlantEnvironmentWaterAirConcentrationSensorName = "PlantEnvironmentWaterAirConcentrationSensor";
	public final static String myPlantEnvironmentNitrogenAirConcentrationSensorName = "PlantEnvironmentNitrogenAirConcentrationSensor";
	public final static String myPlantEnvironmentOtherAirPressureSensorName = "PlantEnvironmentOtherAirPressureSensor";
	public final static String myPlantEnvironmentCO2AirPressureSensorName = "PlantEnvironmentCO2AirPressureSensor";
	public final static String myPlantEnvironmentO2AirPressureSensorName = "PlantEnvironmentO2AirPressureSensor";
	public final static String myPlantEnvironmentWaterAirPressureSensorName = "PlantEnvironmentWaterAirPressureSensor";
	public final static String myPlantEnvironmentNitrogenAirPressureSensorName = "PlantEnvironmentNitrogenAirPressureSensor";
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
	public final static String myPotableWaterStoreOverflowSensorName = "PotableWaterStoreOverflowSensor";
	public final static String myGreyWaterStoreOverflowSensorName = "GreyWaterStoreOverflowSensor";
	public final static String myDirtyWaterStoreOverflowSensorName = "DirtyWaterStoreOverflowSensor";
	//Food
	//BiomassRS
	public final static String myBiomassRSPotableWaterInFlowRateSensorName = "BiomassRSPotableWaterInFlowRateSensor";
	public final static String myBiomassRSGreyWaterInFlowRateSensorName = "BiomassRSGreyWaterInFlowRateSensor";
	public final static String myBiomassRSBiomassOutFlowRateSensorName = "BiomassRSBiomassOutFlowRateSensor";
	public final static String myBiomassRSPowerInFlowRateSensorName = "BiomassRSPowerInFlowRateSensor";
	public final static String myBiomassRSDirtyWaterOutFlowRateSensorName = "BiomassRSDirtyWaterOutFlowRateSensor";
	public final static String myShelf0HarvestSensorName = "Shelf0HarvestSensor";
	//Food Processor
	public final static String myFoodProcessorPowerInFlowRateSensorName = "FoodProcessorPowerInFlowRateSensor";
	public final static String myFoodProcessorBiomassInFlowRateSensorName = "FoodProcessorBiomassInFlowRateSensor";
	public final static String myFoodProcessorFoodOutFlowRateSensorName = "FoodProcessorFoodOutFlowRateSensor";
	//Stores
	public final static String myBiomassStoreLevelSensorName = "BiomassStoreLevelSensor";
	public final static String myFoodStoreLevelSensorName = "FoodStoreLevelSensor";
	public final static String myBiomassStoreOverflowSensorName = "BiomassStoreOverflowSensor";
	public final static String myFoodStoreOverflowSensorName = "FoodStoreOverflowSensor";
	//Waste
	//Incinerator
	public final static String myIncineratorPowerInFlowRateSensorName = "IncineratorPowerInFlowRateSensor";
	public final static String myIncineratorO2InFlowRateSensorName = "IncineratorO2InFlowRateSensor";
	public final static String myIncineratorDryWasteInFlowRateSensorName = "IncineratorDryWasteInFlowRateSensor";
	public final static String myIncineratorCO2OutFlowRateSensorName = "IncineratorCO2OutFlowRateSensor";
	//Stores
	public final static String myDryWasteStoreLevelSensorName = "DryWasteStoreLevelSensor";
	public final static String myDryWasteStoreOverflowSensorName = "DryWasteStoreOverflowSensor";
	//Framework
	//Accumulator
	public final static String myAccumulatorCO2AirEnvironmentInFlowRateSensorName = "AccumulatorCO2AirEnvironmentInFlowRateSensor";
	public final static String myAccumulatorCO2AirStoreOutFlowRateSensorName = "AccumulatorCO2AirStoreOutFlowRateSensor";
	public final static String myAccumulatorO2AirEnvironmentInFlowRateSensorName = "AccumulatorO2AirEnvironmentInFlowRateSensor";
	public final static String myAccumulatorO2AirStoreOutFlowRateSensorName = "AccumulatorO2AirStoreOutFlowRateSensor";
	public final static String myAccumulatorCrewWaterAirEnvironmentInFlowRateSensorName = "AccumulatorCrewWaterAirEnvironmentInFlowRateSensor";
	public final static String myAccumulatorPlantWaterAirEnvironmentInFlowRateSensorName = "AccumulatorPlantWaterAirEnvironmentInFlowRateSensor";
	public final static String myAccumulatorCrewWaterAirStoreOutFlowRateSensorName = "AccumulatorCrewWaterAirStoreOutFlowRateSensor";
	public final static String myAccumulatorPlantWaterAirStoreOutFlowRateSensorName = "AccumulatorPlantWaterAirStoreOutFlowRateSensor";
	//Injector
	public final static String myInjectorCO2AirStoreInFlowRateSensorName = "InjectorCO2AirStoreInFlowRateSensor";
	public final static String myInjectorO2AirStoreInFlowRateSensorName = "InjectorO2AirStoreInFlowRateSensor";
	public final static String myInjectorCO2AirEnvironmentOutFlowRateSensorName = "InjectorCO2AirEnvironmentOutFlowRateSensor";
	public final static String myInjectorO2AirEnvironmentOutFlowRateSensorName = "InjectorO2AirEnvironmentOutFlowRateSensor";
	public final static String myInjectorCrewNitrogenAirEnvironmentOutFlowRateSensorName = "InjectorCrewNitrogenAirEnvironmentOutFlowRateSensor";
	public final static String myInjectorPlantNitrogenAirEnvironmentOutFlowRateSensorName = "InjectorPlantNitrogenAirEnvironmentOutFlowRateSensor";
	public final static String myInjectorCrewNitrogenAirStoreInFlowRateSensorName = "InjectorCrewNitrogenAirStoreInFlowRateSensor";
	public final static String myInjectorPlantNitrogenAirStoreInFlowRateSensorName = "InjectorPlantNitrogenAirStoreInFlowRateSensor";
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
	public final static String myShelf0PlantingActuatorName = "Shelf0PlantingActuator";
	public final static String myShelf0HarvestingActuatorName = "Shelf0HarvestingActuator";
	//Food Processor
	public final static String myFoodProcessorPowerInFlowRateActuatorName = "FoodProcessorPowerInFlowRateActuator";
	public final static String myFoodProcessorBiomassInFlowRateActuatorName = "FoodProcessorBiomassInFlowRateActuator";
	public final static String myFoodProcessorFoodOutFlowRateActuatorName = "FoodProcessorFoodOutFlowRateActuator";
	//Waste
	//Incinerator
	public final static String myIncineratorPowerInFlowRateActuatorName = "IncineratorPowerInFlowRateActuator";
	public final static String myIncineratorO2InFlowRateActuatorName = "IncineratorO2InFlowRateActuator";
	public final static String myIncineratorDryWasteInFlowRateActuatorName = "IncineratorDryWasteInFlowRateActuator";
	public final static String myIncineratorCO2OutFlowRateActuatorName = "IncineratorCO2OutFlowRateActuator";
	//Framework
	//Accumulator
	public final static String myAccumulatorCO2AirEnvironmentInFlowRateActuatorName = "AccumulatorCO2AirEnvironmentInFlowRateActuator";
	public final static String myAccumulatorO2AirEnvironmentInFlowRateActuatorName = "AccumulatorO2AirEnvironmentInFlowRateActuator";
	public final static String myAccumulatorCO2AirStoreOutFlowRateActuatorName = "AccumulatorCO2AirStoreOutFlowRateActuator";
	public final static String myAccumulatorO2AirStoreOutFlowRateActuatorName = "AccumulatorO2AirStoreOutFlowRateActuator";
	public final static String myAccumulatorCrewWaterAirEnvironmentInFlowRateActuatorName = "AccumulatorCrewWaterAirEnvironmentInFlowRateActuator";
	public final static String myAccumulatorPlantWaterAirEnvironmentInFlowRateActuatorName = "AccumulatorPlantWaterAirEnvironmentInFlowRateActuator";
	public final static String myAccumulatorCrewWaterAirStoreOutFlowRateActuatorName = "AccumulatorCrewWaterAirStoreOutFlowRateActuator";
	public final static String myAccumulatorPlantWaterAirStoreOutFlowRateActuatorName = "AccumulatorPlantWaterAirStoreOutFlowRateActuator";
	//Injector
	public final static String myInjectorCO2AirStoreInFlowRateActuatorName = "InjectorCO2AirStoreInFlowRateActuator";
	public final static String myInjectorO2AirStoreInFlowRateActuatorName = "InjectorO2AirStoreInFlowRateActuator";
	public final static String myInjectorCO2AirEnvironmentOutFlowRateActuatorName = "InjectorCO2AirEnvironmentOutFlowRateActuator";
	public final static String myInjectorO2AirEnvironmentOutFlowRateActuatorName = "InjectorO2AirEnvironmentOutFlowRateActuator";
	public final static String myInjectorCrewNitrogenAirEnvironmentOutFlowRateActuatorName = "InjectorCrewNitrogenAirEnvironmentOutFlowRateActuator";
	public final static String myInjectorPlantNitrogenAirEnvironmentOutFlowRateActuatorName = "InjectorPlantNitrogenAirEnvironmentOutFlowRateActuator";
	public final static String myInjectorCrewNitrogenAirStoreInFlowRateActuatorName = "InjectorCrewNitrogenAirStoreInFlowRateActuator";
	public final static String myInjectorPlantNitrogenAirStoreInFlowRateActuatorName = "InjectorPlantNitrogenAirStoreInFlowRateActuator";

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
			CrewGroup myCrew = CrewGroupHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(crewName));
			modules.put(crewName , myCrew);
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(powerPSName));
			modules.put(powerPSName , myPowerPS);
			PowerStore myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(powerStoreName));
			modules.put(powerStoreName , myPowerStore);
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(airRSName));
			modules.put(airRSName , myAirRS);
			SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(crewEnvironmentName));
			modules.put(crewEnvironmentName , myCrewEnvironment);
			SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(plantEnvironmentName));
			modules.put(plantEnvironmentName , myPlantEnvironment);
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(greyWaterStoreName));
			modules.put(greyWaterStoreName , myGreyWaterStore);
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(potableWaterStoreName));
			modules.put(potableWaterStoreName , myPotableWaterStore);
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(dirtyWaterStoreName));
			modules.put(dirtyWaterStoreName , myDirtyWaterStore);
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(foodProcessorName));
			modules.put(foodProcessorName , myFoodProcessor);
			FoodStore myFoodStore= FoodStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(foodStoreName));
			modules.put(foodStoreName , myFoodStore);
			CO2Store myCO2Store = CO2StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(CO2StoreName));
			modules.put(CO2StoreName , myCO2Store);
			H2Store myH2Store = H2StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(H2StoreName));
			modules.put(H2StoreName , myH2Store);
			O2Store myO2Store = O2StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(O2StoreName));
			modules.put(O2StoreName , myO2Store);
			NitrogenStore myNitrogenStore = NitrogenStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(nitrogenStoreName));
			modules.put(nitrogenStoreName , myNitrogenStore);
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(biomassRSName));
			modules.put(biomassRSName , myBiomassRS);
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(biomassStoreName));
			modules.put(biomassStoreName, myBiomassStore);
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(waterRSName));
			modules.put(waterRSName , myWaterRS);
			Incinerator myIncinerator = IncineratorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(incineratorName));
			modules.put(incineratorName , myIncinerator);
			DryWasteStore myDryWasteStore = DryWasteStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(dryWasteStoreName));
			modules.put(dryWasteStoreName, myDryWasteStore);

			System.out.println("BioHolder: Collecting sensor references to modules...");
			//Air
			{
				//AirRS
				{
					PowerInFlowRateSensor myAirRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSPowerInFlowRateSensorName));
					modules.put(myAirRSPowerInFlowRateSensorName , myAirRSPowerInFlowRateSensor);
					sensors.put(myAirRSPowerInFlowRateSensorName , myAirRSPowerInFlowRateSensor);
					AirInFlowRateSensor myAirRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSAirInFlowRateSensorName));
					modules.put(myAirRSAirInFlowRateSensorName , myAirRSAirInFlowRateSensor);
					sensors.put(myAirRSAirInFlowRateSensorName , myAirRSAirInFlowRateSensor);
					AirOutFlowRateSensor myAirRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSAirOutFlowRateSensorName));
					modules.put(myAirRSAirOutFlowRateSensorName , myAirRSAirOutFlowRateSensor);
					sensors.put(myAirRSAirOutFlowRateSensorName , myAirRSAirOutFlowRateSensor);
					O2OutFlowRateSensor myAirRSO2OutFlowRateSensor = O2OutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSO2OutFlowRateSensorName));
					modules.put(myAirRSO2OutFlowRateSensorName , myAirRSO2OutFlowRateSensor);
					sensors.put(myAirRSO2OutFlowRateSensorName , myAirRSO2OutFlowRateSensor);
					CO2InFlowRateSensor myAirRSCO2InFlowRateSensor = CO2InFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSCO2InFlowRateSensorName));
					modules.put(myAirRSCO2InFlowRateSensorName , myAirRSCO2InFlowRateSensor);
					sensors.put(myAirRSCO2InFlowRateSensorName , myAirRSCO2InFlowRateSensor);
					CO2OutFlowRateSensor myAirRSCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSCO2OutFlowRateSensorName));
					modules.put(myAirRSCO2OutFlowRateSensorName , myAirRSCO2OutFlowRateSensor);
					sensors.put(myAirRSCO2OutFlowRateSensorName , myAirRSCO2OutFlowRateSensor);
					H2InFlowRateSensor myAirRSH2InFlowRateSensor = H2InFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSH2InFlowRateSensorName));
					modules.put(myAirRSH2InFlowRateSensorName , myAirRSH2InFlowRateSensor);
					sensors.put(myAirRSH2InFlowRateSensorName , myAirRSH2InFlowRateSensor);
					H2OutFlowRateSensor myAirRSH2OutFlowRateSensor = H2OutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSH2OutFlowRateSensorName));
					modules.put(myAirRSH2OutFlowRateSensorName , myAirRSH2OutFlowRateSensor);
					sensors.put(myAirRSH2OutFlowRateSensorName , myAirRSH2OutFlowRateSensor);
					PotableWaterInFlowRateSensor myAirRSPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSPotableWaterInFlowRateSensorName));
					modules.put(myAirRSPotableWaterInFlowRateSensorName , myAirRSPotableWaterInFlowRateSensor);
					sensors.put(myAirRSPotableWaterInFlowRateSensorName , myAirRSPotableWaterInFlowRateSensor);
					PotableWaterOutFlowRateSensor myAirRSPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSPotableWaterOutFlowRateSensorName));
					modules.put(myAirRSPotableWaterOutFlowRateSensorName , myAirRSPotableWaterOutFlowRateSensor);
					sensors.put(myAirRSPotableWaterOutFlowRateSensorName , myAirRSPotableWaterOutFlowRateSensor);
				}
				//Stores
				{
					O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myO2StoreLevelSensorName));
					modules.put(myO2StoreLevelSensorName , myO2StoreLevelSensor);
					sensors.put(myO2StoreLevelSensorName , myO2StoreLevelSensor);
					CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCO2StoreLevelSensorName));
					modules.put(myCO2StoreLevelSensorName , myCO2StoreLevelSensor);
					sensors.put(myCO2StoreLevelSensorName , myCO2StoreLevelSensor);
					H2StoreLevelSensor myH2StoreLevelSensor = H2StoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myH2StoreLevelSensorName));
					modules.put(myH2StoreLevelSensorName , myH2StoreLevelSensor);
					sensors.put(myH2StoreLevelSensorName , myH2StoreLevelSensor);
					NitrogenStoreLevelSensor myNitrogenStoreLevelSensor = NitrogenStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myNitrogenStoreLevelSensorName));
					modules.put(myNitrogenStoreLevelSensorName , myNitrogenStoreLevelSensor);
					sensors.put(myNitrogenStoreLevelSensorName , myNitrogenStoreLevelSensor);
					
					StoreOverflowSensor myO2StoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myO2StoreOverflowSensorName));
					modules.put(myO2StoreOverflowSensorName , myO2StoreOverflowSensor);
					sensors.put(myO2StoreOverflowSensorName , myO2StoreOverflowSensor);
					StoreOverflowSensor myCO2StoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCO2StoreOverflowSensorName));
					modules.put(myCO2StoreOverflowSensorName , myCO2StoreOverflowSensor);
					sensors.put(myCO2StoreOverflowSensorName , myCO2StoreOverflowSensor);
					StoreOverflowSensor myH2StoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myH2StoreOverflowSensorName));
					modules.put(myH2StoreOverflowSensorName , myH2StoreOverflowSensor);
					sensors.put(myH2StoreOverflowSensorName , myH2StoreOverflowSensor);
					StoreOverflowSensor myNitrogenStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myNitrogenStoreOverflowSensorName));
					modules.put(myNitrogenStoreOverflowSensorName , myNitrogenStoreOverflowSensor);
					sensors.put(myNitrogenStoreOverflowSensorName , myNitrogenStoreOverflowSensor);
				}
			}
			//Power
			{
				//PowerPS
				{
					PowerOutFlowRateSensor myPowerPSPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPowerPSPowerOutFlowRateSensorName));
					modules.put(myPowerPSPowerOutFlowRateSensorName , myPowerPSPowerOutFlowRateSensor);
					sensors.put(myPowerPSPowerOutFlowRateSensorName , myPowerPSPowerOutFlowRateSensor);
				}
				//Stores
				{
					PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPowerStoreLevelSensorName));
					modules.put(myPowerStoreLevelSensorName , myPowerStoreLevelSensor);
					sensors.put(myPowerStoreLevelSensorName , myPowerStoreLevelSensor);
					
					StoreOverflowSensor myPowerStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPowerStoreOverflowSensorName));
					modules.put(myPowerStoreOverflowSensorName , myPowerStoreOverflowSensor);
					sensors.put(myPowerStoreOverflowSensorName , myPowerStoreOverflowSensor);
				}
			}
			//Crew
			{
				CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupDeathSensorName));
				CrewGroupAnyDeadSensor myCrewGroupAnyDeadSensor = CrewGroupAnyDeadSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupAnyDeadSensorName));
				CrewGroupProductivitySensor myCrewGroupProductivitySensor = CrewGroupProductivitySensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupProductivitySensorName));
				PotableWaterInFlowRateSensor myCrewGroupPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupPotableWaterInFlowRateSensorName));
				GreyWaterOutFlowRateSensor myCrewGroupGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupGreyWaterOutFlowRateSensorName));
				DirtyWaterOutFlowRateSensor myCrewGroupDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupDirtyWaterOutFlowRateSensorName));
				DryWasteOutFlowRateSensor myCrewGroupDryWasteOutFlowRateSensor = DryWasteOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupDryWasteOutFlowRateSensorName));
				
				modules.put(myCrewGroupDeathSensorName , myCrewGroupDeathSensor);
				modules.put(myCrewGroupAnyDeadSensorName , myCrewGroupAnyDeadSensor);
				modules.put(myCrewGroupProductivitySensorName , myCrewGroupProductivitySensor);
				modules.put(myCrewGroupPotableWaterInFlowRateSensorName , myCrewGroupPotableWaterInFlowRateSensor);
				modules.put(myCrewGroupGreyWaterOutFlowRateSensorName , myCrewGroupGreyWaterOutFlowRateSensor);
				modules.put(myCrewGroupDirtyWaterOutFlowRateSensorName , myCrewGroupDirtyWaterOutFlowRateSensor);
				modules.put(myCrewGroupDryWasteOutFlowRateSensorName , myCrewGroupDryWasteOutFlowRateSensor);
				
				sensors.put(myCrewGroupDeathSensorName , myCrewGroupDeathSensor);
				sensors.put(myCrewGroupAnyDeadSensorName , myCrewGroupAnyDeadSensor);
				sensors.put(myCrewGroupProductivitySensorName , myCrewGroupProductivitySensor);
				sensors.put(myCrewGroupPotableWaterInFlowRateSensorName , myCrewGroupPotableWaterInFlowRateSensor);
				sensors.put(myCrewGroupGreyWaterOutFlowRateSensorName , myCrewGroupGreyWaterOutFlowRateSensor);
				sensors.put(myCrewGroupDirtyWaterOutFlowRateSensorName , myCrewGroupDirtyWaterOutFlowRateSensor);
				sensors.put(myCrewGroupDryWasteOutFlowRateSensorName , myCrewGroupDryWasteOutFlowRateSensor);
				
			}
			//Environment
			{
				//Crew
				{
					OtherAirConcentrationSensor myCrewEnvironmentOtherAirConcentrationSensor = OtherAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentOtherAirConcentrationSensorName));
					modules.put(myCrewEnvironmentOtherAirConcentrationSensorName , myCrewEnvironmentOtherAirConcentrationSensor);
					sensors.put(myCrewEnvironmentOtherAirConcentrationSensorName , myCrewEnvironmentOtherAirConcentrationSensor);
					O2AirConcentrationSensor myCrewEnvironmentO2AirConcentrationSensor = O2AirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentO2AirConcentrationSensorName));
					modules.put(myCrewEnvironmentO2AirConcentrationSensorName , myCrewEnvironmentO2AirConcentrationSensor);
					sensors.put(myCrewEnvironmentO2AirConcentrationSensorName , myCrewEnvironmentO2AirConcentrationSensor);
					CO2AirConcentrationSensor myCrewEnvironmentCO2AirConcentrationSensor = CO2AirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentCO2AirConcentrationSensorName));
					modules.put(myCrewEnvironmentCO2AirConcentrationSensorName , myCrewEnvironmentCO2AirConcentrationSensor);
					sensors.put(myCrewEnvironmentCO2AirConcentrationSensorName , myCrewEnvironmentCO2AirConcentrationSensor);
					WaterAirConcentrationSensor myCrewEnvironmentWaterAirConcentrationSensor = WaterAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentWaterAirConcentrationSensorName));
					modules.put(myCrewEnvironmentWaterAirConcentrationSensorName , myCrewEnvironmentWaterAirConcentrationSensor);
					sensors.put(myCrewEnvironmentWaterAirConcentrationSensorName , myCrewEnvironmentWaterAirConcentrationSensor);
					NitrogenAirConcentrationSensor myCrewEnvironmentNitrogenAirConcentrationSensor = NitrogenAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentNitrogenAirConcentrationSensorName));
					modules.put(myCrewEnvironmentNitrogenAirConcentrationSensorName , myCrewEnvironmentNitrogenAirConcentrationSensor);
					sensors.put(myCrewEnvironmentNitrogenAirConcentrationSensorName , myCrewEnvironmentNitrogenAirConcentrationSensor);
					OtherAirPressureSensor myCrewEnvironmentOtherAirPressureSensor = OtherAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentOtherAirPressureSensorName));
					modules.put(myCrewEnvironmentOtherAirPressureSensorName , myCrewEnvironmentOtherAirPressureSensor);
					sensors.put(myCrewEnvironmentOtherAirPressureSensorName , myCrewEnvironmentOtherAirPressureSensor);
					O2AirPressureSensor myCrewEnvironmentO2AirPressureSensor = O2AirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentO2AirPressureSensorName));
					modules.put(myCrewEnvironmentO2AirPressureSensorName , myCrewEnvironmentO2AirPressureSensor);
					sensors.put(myCrewEnvironmentO2AirPressureSensorName , myCrewEnvironmentO2AirPressureSensor);
					CO2AirPressureSensor myCrewEnvironmentCO2AirPressureSensor = CO2AirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentCO2AirPressureSensorName));
					modules.put(myCrewEnvironmentCO2AirPressureSensorName , myCrewEnvironmentCO2AirPressureSensor);
					sensors.put(myCrewEnvironmentCO2AirPressureSensorName , myCrewEnvironmentCO2AirPressureSensor);
					WaterAirPressureSensor myCrewEnvironmentWaterAirPressureSensor = WaterAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentWaterAirPressureSensorName));
					modules.put(myCrewEnvironmentWaterAirPressureSensorName , myCrewEnvironmentWaterAirPressureSensor);
					sensors.put(myCrewEnvironmentWaterAirPressureSensorName , myCrewEnvironmentWaterAirPressureSensor);
					NitrogenAirPressureSensor myCrewEnvironmentNitrogenAirPressureSensor = NitrogenAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewEnvironmentNitrogenAirPressureSensorName));
					modules.put(myCrewEnvironmentNitrogenAirPressureSensorName , myCrewEnvironmentNitrogenAirPressureSensor);
					sensors.put(myCrewEnvironmentNitrogenAirPressureSensorName , myCrewEnvironmentNitrogenAirPressureSensor);
				}
				//Plant
				{
					OtherAirConcentrationSensor myPlantEnvironmentOtherAirConcentrationSensor = OtherAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentOtherAirConcentrationSensorName));
					modules.put(myPlantEnvironmentOtherAirConcentrationSensorName , myPlantEnvironmentOtherAirConcentrationSensor);
					sensors.put(myPlantEnvironmentOtherAirConcentrationSensorName , myPlantEnvironmentOtherAirConcentrationSensor);
					O2AirConcentrationSensor myPlantEnvironmentO2AirConcentrationSensor = O2AirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentO2AirConcentrationSensorName));
					modules.put(myPlantEnvironmentO2AirConcentrationSensorName , myPlantEnvironmentO2AirConcentrationSensor);
					sensors.put(myPlantEnvironmentO2AirConcentrationSensorName , myPlantEnvironmentO2AirConcentrationSensor);
					CO2AirConcentrationSensor myPlantEnvironmentCO2AirConcentrationSensor = CO2AirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentCO2AirConcentrationSensorName));
					modules.put(myPlantEnvironmentCO2AirConcentrationSensorName , myPlantEnvironmentCO2AirConcentrationSensor);
					sensors.put(myPlantEnvironmentCO2AirConcentrationSensorName , myPlantEnvironmentCO2AirConcentrationSensor);
					WaterAirConcentrationSensor myPlantEnvironmentWaterAirConcentrationSensor = WaterAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentWaterAirConcentrationSensorName));
					modules.put(myPlantEnvironmentWaterAirConcentrationSensorName , myPlantEnvironmentWaterAirConcentrationSensor);
					sensors.put(myPlantEnvironmentWaterAirConcentrationSensorName , myPlantEnvironmentWaterAirConcentrationSensor);
					NitrogenAirConcentrationSensor myPlantEnvironmentNitrogenAirConcentrationSensor = NitrogenAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentNitrogenAirConcentrationSensorName));
					modules.put(myPlantEnvironmentNitrogenAirConcentrationSensorName , myPlantEnvironmentNitrogenAirConcentrationSensor);
					sensors.put(myPlantEnvironmentNitrogenAirConcentrationSensorName , myPlantEnvironmentNitrogenAirConcentrationSensor);
					OtherAirPressureSensor myPlantEnvironmentOtherAirPressureSensor = OtherAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentOtherAirPressureSensorName));
					modules.put(myPlantEnvironmentOtherAirPressureSensorName , myPlantEnvironmentOtherAirPressureSensor);
					sensors.put(myPlantEnvironmentOtherAirPressureSensorName , myPlantEnvironmentOtherAirPressureSensor);
					O2AirPressureSensor myPlantEnvironmentO2AirPressureSensor = O2AirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentO2AirPressureSensorName));
					modules.put(myPlantEnvironmentO2AirPressureSensorName , myPlantEnvironmentO2AirPressureSensor);
					sensors.put(myPlantEnvironmentO2AirPressureSensorName , myPlantEnvironmentO2AirPressureSensor);
					CO2AirPressureSensor myPlantEnvironmentCO2AirPressureSensor = CO2AirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentCO2AirPressureSensorName));
					modules.put(myPlantEnvironmentCO2AirPressureSensorName , myPlantEnvironmentCO2AirPressureSensor);
					sensors.put(myPlantEnvironmentCO2AirPressureSensorName , myPlantEnvironmentCO2AirPressureSensor);
					WaterAirPressureSensor myPlantEnvironmentWaterAirPressureSensor = WaterAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentWaterAirPressureSensorName));
					modules.put(myPlantEnvironmentWaterAirPressureSensorName , myPlantEnvironmentWaterAirPressureSensor);
					sensors.put(myPlantEnvironmentWaterAirPressureSensorName , myPlantEnvironmentWaterAirPressureSensor);
					NitrogenAirPressureSensor myPlantEnvironmentNitrogenAirPressureSensor = NitrogenAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPlantEnvironmentNitrogenAirPressureSensorName));
					modules.put(myPlantEnvironmentNitrogenAirPressureSensorName , myPlantEnvironmentNitrogenAirPressureSensor);
					sensors.put(myPlantEnvironmentNitrogenAirPressureSensorName , myPlantEnvironmentNitrogenAirPressureSensor);
				}
			}
			//Water
			{
				//WaterRS
				{
					DirtyWaterInFlowRateSensor myWaterRSDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSDirtyWaterInFlowRateSensorName));
					modules.put(myWaterRSDirtyWaterInFlowRateSensorName , myWaterRSDirtyWaterInFlowRateSensor);
					sensors.put(myWaterRSDirtyWaterInFlowRateSensorName , myWaterRSDirtyWaterInFlowRateSensor);
					GreyWaterInFlowRateSensor myWaterRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSGreyWaterInFlowRateSensorName));
					modules.put(myWaterRSGreyWaterInFlowRateSensorName , myWaterRSGreyWaterInFlowRateSensor);
					sensors.put(myWaterRSGreyWaterInFlowRateSensorName , myWaterRSGreyWaterInFlowRateSensor);
					PowerInFlowRateSensor myWaterRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSPowerInFlowRateSensorName));
					modules.put(myWaterRSPowerInFlowRateSensorName , myWaterRSPowerInFlowRateSensor);
					sensors.put(myWaterRSPowerInFlowRateSensorName , myWaterRSPowerInFlowRateSensor);
					PotableWaterOutFlowRateSensor myWaterRSPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSPotableWaterOutFlowRateSensorName));
					modules.put(myWaterRSPotableWaterOutFlowRateSensorName , myWaterRSPotableWaterOutFlowRateSensor);
					sensors.put(myWaterRSPotableWaterOutFlowRateSensorName , myWaterRSPotableWaterOutFlowRateSensor);
				}
				//Stores
				{
					PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPotableWaterStoreLevelSensorName));
					modules.put(myPotableWaterStoreLevelSensorName , myPotableWaterStoreLevelSensor);
					sensors.put(myPotableWaterStoreLevelSensorName , myPotableWaterStoreLevelSensor);
					GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myGreyWaterStoreLevelSensorName));
					modules.put(myGreyWaterStoreLevelSensorName , myGreyWaterStoreLevelSensor);
					sensors.put(myGreyWaterStoreLevelSensorName , myGreyWaterStoreLevelSensor);
					DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myDirtyWaterStoreLevelSensorName));
					modules.put(myDirtyWaterStoreLevelSensorName , myDirtyWaterStoreLevelSensor);
					sensors.put(myDirtyWaterStoreLevelSensorName , myDirtyWaterStoreLevelSensor);
					
					StoreOverflowSensor myPotableWaterStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPotableWaterStoreOverflowSensorName));
					modules.put(myPotableWaterStoreOverflowSensorName , myPotableWaterStoreOverflowSensor);
					sensors.put(myPotableWaterStoreOverflowSensorName , myPotableWaterStoreOverflowSensor);
					StoreOverflowSensor myGreyWaterStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myGreyWaterStoreOverflowSensorName));
					modules.put(myGreyWaterStoreOverflowSensorName , myGreyWaterStoreOverflowSensor);
					sensors.put(myGreyWaterStoreOverflowSensorName , myGreyWaterStoreOverflowSensor);
					StoreOverflowSensor myDirtyWaterStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myDirtyWaterStoreOverflowSensorName));
					modules.put(myDirtyWaterStoreOverflowSensorName , myDirtyWaterStoreOverflowSensor);
					sensors.put(myDirtyWaterStoreOverflowSensorName , myDirtyWaterStoreOverflowSensor);
				}
			}
			//Food
			{
				//BiomassRS
				{
					PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSPotableWaterInFlowRateSensorName));
					modules.put(myBiomassRSPotableWaterInFlowRateSensorName , myBiomassRSPotableWaterInFlowRateSensor);
					sensors.put(myBiomassRSPotableWaterInFlowRateSensorName , myBiomassRSPotableWaterInFlowRateSensor);
					GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSGreyWaterInFlowRateSensorName));
					modules.put(myBiomassRSGreyWaterInFlowRateSensorName , myBiomassRSGreyWaterInFlowRateSensor);
					sensors.put(myBiomassRSGreyWaterInFlowRateSensorName , myBiomassRSGreyWaterInFlowRateSensor);
					DirtyWaterOutFlowRateSensor myBiomassRSDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSDirtyWaterOutFlowRateSensorName));
					modules.put(myBiomassRSDirtyWaterOutFlowRateSensorName , myBiomassRSDirtyWaterOutFlowRateSensor);
					sensors.put(myBiomassRSDirtyWaterOutFlowRateSensorName , myBiomassRSDirtyWaterOutFlowRateSensor);
					BiomassOutFlowRateSensor myBiomassRSBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSBiomassOutFlowRateSensorName));
					modules.put(myBiomassRSBiomassOutFlowRateSensorName , myBiomassRSBiomassOutFlowRateSensor);
					sensors.put(myBiomassRSBiomassOutFlowRateSensorName , myBiomassRSBiomassOutFlowRateSensor);
					PowerInFlowRateSensor myBiomassRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSPowerInFlowRateSensorName));
					modules.put(myBiomassRSPowerInFlowRateSensorName , myBiomassRSPowerInFlowRateSensor);
					sensors.put(myBiomassRSPowerInFlowRateSensorName , myBiomassRSPowerInFlowRateSensor);
					HarvestSensor myShelf0HarvestSensor = HarvestSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myShelf0HarvestSensorName));
					modules.put(myShelf0HarvestSensorName , myShelf0HarvestSensor);
					sensors.put(myShelf0HarvestSensorName , myShelf0HarvestSensor);
				}
				//Food Processor
				{
					PowerInFlowRateSensor myFoodProcessorPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodProcessorPowerInFlowRateSensorName));
					modules.put(myFoodProcessorPowerInFlowRateSensorName , myFoodProcessorPowerInFlowRateSensor);
					sensors.put(myFoodProcessorPowerInFlowRateSensorName , myFoodProcessorPowerInFlowRateSensor);
					BiomassInFlowRateSensor myFoodProcessorBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodProcessorBiomassInFlowRateSensorName));
					modules.put(myFoodProcessorBiomassInFlowRateSensorName , myFoodProcessorBiomassInFlowRateSensor);
					sensors.put(myFoodProcessorBiomassInFlowRateSensorName , myFoodProcessorBiomassInFlowRateSensor);
					FoodOutFlowRateSensor myFoodProcessorFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodProcessorFoodOutFlowRateSensorName));
					modules.put(myFoodProcessorFoodOutFlowRateSensorName , myFoodProcessorFoodOutFlowRateSensor);
					sensors.put(myFoodProcessorFoodOutFlowRateSensorName , myFoodProcessorFoodOutFlowRateSensor);
				}
				//Stores
				{
					BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassStoreLevelSensorName));
					modules.put(myBiomassStoreLevelSensorName , myBiomassStoreLevelSensor);
					sensors.put(myBiomassStoreLevelSensorName , myBiomassStoreLevelSensor);
					FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodStoreLevelSensorName));
					modules.put(myFoodStoreLevelSensorName , myFoodStoreLevelSensor);
					sensors.put(myFoodStoreLevelSensorName , myFoodStoreLevelSensor);
					
					StoreOverflowSensor myBiomassStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassStoreOverflowSensorName));
					modules.put(myBiomassStoreOverflowSensorName , myBiomassStoreOverflowSensor);
					sensors.put(myBiomassStoreOverflowSensorName , myBiomassStoreOverflowSensor);
					StoreOverflowSensor myFoodStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodStoreOverflowSensorName));
					modules.put(myFoodStoreOverflowSensorName , myFoodStoreOverflowSensor);
					sensors.put(myFoodStoreOverflowSensorName , myFoodStoreOverflowSensor);
				}
			}
			//Waste
			{
				//Incinerator
				{
					DryWasteInFlowRateSensor myIncineratorDryWasteInFlowRateSensor = DryWasteInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorDryWasteInFlowRateSensorName));
					modules.put(myIncineratorDryWasteInFlowRateSensorName , myIncineratorDryWasteInFlowRateSensor);
					sensors.put(myIncineratorDryWasteInFlowRateSensorName , myIncineratorDryWasteInFlowRateSensor);
					O2InFlowRateSensor myIncineratorO2InFlowRateSensor = O2InFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorO2InFlowRateSensorName));
					modules.put(myIncineratorO2InFlowRateSensorName , myIncineratorO2InFlowRateSensor);
					sensors.put(myIncineratorO2InFlowRateSensorName , myIncineratorO2InFlowRateSensor);
					CO2OutFlowRateSensor myIncineratorCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorCO2OutFlowRateSensorName));
					modules.put(myIncineratorCO2OutFlowRateSensorName , myIncineratorCO2OutFlowRateSensor);
					sensors.put(myIncineratorCO2OutFlowRateSensorName , myIncineratorCO2OutFlowRateSensor);
					PowerInFlowRateSensor myIncineratorPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorPowerInFlowRateSensorName));
					modules.put(myIncineratorPowerInFlowRateSensorName , myIncineratorPowerInFlowRateSensor);
					sensors.put(myIncineratorPowerInFlowRateSensorName , myIncineratorPowerInFlowRateSensor);
				}
				//Stores
				{
					DryWasteStoreLevelSensor myDryWasteStoreLevelSensor = DryWasteStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myDryWasteStoreLevelSensorName));
					modules.put(myDryWasteStoreLevelSensorName , myDryWasteStoreLevelSensor);
					sensors.put(myDryWasteStoreLevelSensorName , myDryWasteStoreLevelSensor);
					StoreOverflowSensor myDryWasteStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myDryWasteStoreOverflowSensorName));
					modules.put(myDryWasteStoreOverflowSensorName , myDryWasteStoreOverflowSensor);
					sensors.put(myDryWasteStoreOverflowSensorName , myDryWasteStoreOverflowSensor);
				}
			}
			//Framework
			{
				//Accumulator
				{
					CO2AirEnvironmentInFlowRateSensor myAccumulatorCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCO2AirEnvironmentInFlowRateSensorName));
					modules.put(myAccumulatorCO2AirEnvironmentInFlowRateSensorName , myAccumulatorCO2AirEnvironmentInFlowRateSensor);
					sensors.put(myAccumulatorCO2AirEnvironmentInFlowRateSensorName , myAccumulatorCO2AirEnvironmentInFlowRateSensor);
					O2AirEnvironmentInFlowRateSensor myAccumulatorO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorO2AirEnvironmentInFlowRateSensorName));
					modules.put(myAccumulatorO2AirEnvironmentInFlowRateSensorName , myAccumulatorO2AirEnvironmentInFlowRateSensor);
					sensors.put(myAccumulatorO2AirEnvironmentInFlowRateSensorName , myAccumulatorO2AirEnvironmentInFlowRateSensor);
					CO2AirStoreOutFlowRateSensor myAccumulatorCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCO2AirStoreOutFlowRateSensorName));
					modules.put(myAccumulatorCO2AirStoreOutFlowRateSensorName , myAccumulatorCO2AirStoreOutFlowRateSensor);
					sensors.put(myAccumulatorCO2AirStoreOutFlowRateSensorName , myAccumulatorCO2AirStoreOutFlowRateSensor);
					O2AirStoreOutFlowRateSensor myAccumulatorO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorO2AirStoreOutFlowRateSensorName));
					modules.put(myAccumulatorO2AirStoreOutFlowRateSensorName , myAccumulatorO2AirStoreOutFlowRateSensor);
					sensors.put(myAccumulatorO2AirStoreOutFlowRateSensorName , myAccumulatorO2AirStoreOutFlowRateSensor);
					
					WaterAirEnvironmentInFlowRateSensor myAccumulatorCrewWaterAirEnvironmentInFlowRateSensor = WaterAirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCrewWaterAirEnvironmentInFlowRateSensorName));
					modules.put(myAccumulatorCrewWaterAirEnvironmentInFlowRateSensorName , myAccumulatorCrewWaterAirEnvironmentInFlowRateSensor);
					sensors.put(myAccumulatorCrewWaterAirEnvironmentInFlowRateSensorName , myAccumulatorCrewWaterAirEnvironmentInFlowRateSensor);
					WaterAirEnvironmentInFlowRateSensor myAccumulatorPlantWaterAirEnvironmentInFlowRateSensor = WaterAirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorPlantWaterAirEnvironmentInFlowRateSensorName));
					modules.put(myAccumulatorPlantWaterAirEnvironmentInFlowRateSensorName , myAccumulatorPlantWaterAirEnvironmentInFlowRateSensor);
					sensors.put(myAccumulatorPlantWaterAirEnvironmentInFlowRateSensorName , myAccumulatorPlantWaterAirEnvironmentInFlowRateSensor);
					WaterAirStoreOutFlowRateSensor myAccumulatorCrewWaterAirStoreOutFlowRateSensor = WaterAirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCrewWaterAirStoreOutFlowRateSensorName));
					modules.put(myAccumulatorCrewWaterAirStoreOutFlowRateSensorName , myAccumulatorCrewWaterAirStoreOutFlowRateSensor);
					sensors.put(myAccumulatorCrewWaterAirStoreOutFlowRateSensorName , myAccumulatorCrewWaterAirStoreOutFlowRateSensor);
					WaterAirStoreOutFlowRateSensor myAccumulatorPlantWaterAirStoreOutFlowRateSensor = WaterAirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorPlantWaterAirStoreOutFlowRateSensorName));
					modules.put(myAccumulatorPlantWaterAirStoreOutFlowRateSensorName , myAccumulatorPlantWaterAirStoreOutFlowRateSensor);
					sensors.put(myAccumulatorPlantWaterAirStoreOutFlowRateSensorName , myAccumulatorPlantWaterAirStoreOutFlowRateSensor);
				}
				//Injector
				{
					CO2AirStoreInFlowRateSensor myInjectorCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCO2AirStoreInFlowRateSensorName));
					modules.put(myInjectorCO2AirStoreInFlowRateSensorName , myInjectorCO2AirStoreInFlowRateSensor);
					sensors.put(myInjectorCO2AirStoreInFlowRateSensorName , myInjectorCO2AirStoreInFlowRateSensor);
					O2AirStoreInFlowRateSensor myInjectorO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorO2AirStoreInFlowRateSensorName));
					modules.put(myInjectorO2AirStoreInFlowRateSensorName , myInjectorO2AirStoreInFlowRateSensor);
					sensors.put(myInjectorO2AirStoreInFlowRateSensorName , myInjectorO2AirStoreInFlowRateSensor);
					CO2AirEnvironmentOutFlowRateSensor myInjectorCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCO2AirEnvironmentOutFlowRateSensorName));
					modules.put(myInjectorCO2AirEnvironmentOutFlowRateSensorName , myInjectorCO2AirEnvironmentOutFlowRateSensor);
					sensors.put(myInjectorCO2AirEnvironmentOutFlowRateSensorName , myInjectorCO2AirEnvironmentOutFlowRateSensor);
					O2AirEnvironmentOutFlowRateSensor myInjectorO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorO2AirEnvironmentOutFlowRateSensorName));
					modules.put(myInjectorO2AirEnvironmentOutFlowRateSensorName , myInjectorO2AirEnvironmentOutFlowRateSensor);
					sensors.put(myInjectorO2AirEnvironmentOutFlowRateSensorName , myInjectorO2AirEnvironmentOutFlowRateSensor);
					
					NitrogenAirEnvironmentOutFlowRateSensor myInjectorCrewNitrogenAirEnvironmentOutFlowRateSensor = NitrogenAirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCrewNitrogenAirEnvironmentOutFlowRateSensorName));
					modules.put(myInjectorCrewNitrogenAirEnvironmentOutFlowRateSensorName , myInjectorCrewNitrogenAirEnvironmentOutFlowRateSensor);
					sensors.put(myInjectorCrewNitrogenAirEnvironmentOutFlowRateSensorName , myInjectorCrewNitrogenAirEnvironmentOutFlowRateSensor);
					NitrogenAirEnvironmentOutFlowRateSensor myInjectorPlantNitrogenAirEnvironmentOutFlowRateSensor = NitrogenAirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorPlantNitrogenAirEnvironmentOutFlowRateSensorName));
					modules.put(myInjectorPlantNitrogenAirEnvironmentOutFlowRateSensorName , myInjectorPlantNitrogenAirEnvironmentOutFlowRateSensor);
					sensors.put(myInjectorPlantNitrogenAirEnvironmentOutFlowRateSensorName , myInjectorPlantNitrogenAirEnvironmentOutFlowRateSensor);
					NitrogenAirStoreInFlowRateSensor myInjectorCrewNitrogenAirStoreInFlowRateSensor = NitrogenAirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCrewNitrogenAirStoreInFlowRateSensorName));
					modules.put(myInjectorCrewNitrogenAirStoreInFlowRateSensorName , myInjectorCrewNitrogenAirStoreInFlowRateSensor);
					sensors.put(myInjectorCrewNitrogenAirStoreInFlowRateSensorName , myInjectorCrewNitrogenAirStoreInFlowRateSensor);
					NitrogenAirStoreInFlowRateSensor myInjectorPlantNitrogenAirStoreInFlowRateSensor = NitrogenAirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorPlantNitrogenAirStoreInFlowRateSensorName));
					modules.put(myInjectorPlantNitrogenAirStoreInFlowRateSensorName , myInjectorPlantNitrogenAirStoreInFlowRateSensor);
					sensors.put(myInjectorPlantNitrogenAirStoreInFlowRateSensorName , myInjectorPlantNitrogenAirStoreInFlowRateSensor);
				}
			}
			System.out.println("BioHolder: Collecting actuator references to modules...");
			//Air
			{
				//AirRS
				{
					PowerInFlowRateActuator myAirRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSPowerInFlowRateActuatorName));
					modules.put(myAirRSPowerInFlowRateActuatorName , myAirRSPowerInFlowRateActuator);
					actuators.put(myAirRSPowerInFlowRateActuatorName , myAirRSPowerInFlowRateActuator);
					AirInFlowRateActuator myAirRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSAirInFlowRateActuatorName));
					modules.put(myAirRSAirInFlowRateActuatorName , myAirRSAirInFlowRateActuator);
					actuators.put(myAirRSAirInFlowRateActuatorName , myAirRSAirInFlowRateActuator);
					AirOutFlowRateActuator myAirRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSAirOutFlowRateActuatorName));
					modules.put(myAirRSAirOutFlowRateActuatorName , myAirRSAirOutFlowRateActuator);
					actuators.put(myAirRSAirOutFlowRateActuatorName , myAirRSAirOutFlowRateActuator);
					O2OutFlowRateActuator myAirRSO2OutFlowRateActuator = O2OutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSO2OutFlowRateActuatorName));
					modules.put(myAirRSO2OutFlowRateActuatorName , myAirRSO2OutFlowRateActuator);
					actuators.put(myAirRSO2OutFlowRateActuatorName , myAirRSO2OutFlowRateActuator);
					CO2InFlowRateActuator myAirRSCO2InFlowRateActuator = CO2InFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSCO2InFlowRateActuatorName));
					modules.put(myAirRSCO2InFlowRateActuatorName , myAirRSCO2InFlowRateActuator);
					actuators.put(myAirRSCO2InFlowRateActuatorName , myAirRSCO2InFlowRateActuator);
					CO2OutFlowRateActuator myAirRSCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSCO2OutFlowRateActuatorName));
					modules.put(myAirRSCO2OutFlowRateActuatorName , myAirRSCO2OutFlowRateActuator);
					actuators.put(myAirRSCO2OutFlowRateActuatorName , myAirRSCO2OutFlowRateActuator);
					H2InFlowRateActuator myAirRSH2InFlowRateActuator = H2InFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSH2InFlowRateActuatorName));
					modules.put(myAirRSH2InFlowRateActuatorName , myAirRSH2InFlowRateActuator);
					actuators.put(myAirRSH2InFlowRateActuatorName , myAirRSH2InFlowRateActuator);
					H2OutFlowRateActuator myAirRSH2OutFlowRateActuator = H2OutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSH2OutFlowRateActuatorName));
					modules.put(myAirRSH2OutFlowRateActuatorName , myAirRSH2OutFlowRateActuator);
					actuators.put(myAirRSH2OutFlowRateActuatorName , myAirRSH2OutFlowRateActuator);
					PotableWaterInFlowRateActuator myAirRSPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSPotableWaterInFlowRateActuatorName));
					modules.put(myAirRSPotableWaterInFlowRateActuatorName , myAirRSPotableWaterInFlowRateActuator);
					actuators.put(myAirRSPotableWaterInFlowRateActuatorName , myAirRSPotableWaterInFlowRateActuator);
					PotableWaterOutFlowRateActuator myAirRSPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAirRSPotableWaterOutFlowRateActuatorName));
					modules.put(myAirRSPotableWaterOutFlowRateActuatorName , myAirRSPotableWaterOutFlowRateActuator);
					actuators.put(myAirRSPotableWaterOutFlowRateActuatorName , myAirRSPotableWaterOutFlowRateActuator);
				}
			}
			//Power
			{
				//PowerPS
				{
					PowerOutFlowRateActuator myPowerPSPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myPowerPSPowerOutFlowRateActuatorName));
					modules.put(myPowerPSPowerOutFlowRateActuatorName , myPowerPSPowerOutFlowRateActuator);
					actuators.put(myPowerPSPowerOutFlowRateActuatorName , myPowerPSPowerOutFlowRateActuator);
				}
			}
			//Water
			{
				//WaterRS
				{
					DirtyWaterInFlowRateActuator myWaterRSDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSDirtyWaterInFlowRateActuatorName));
					modules.put(myWaterRSDirtyWaterInFlowRateActuatorName , myWaterRSDirtyWaterInFlowRateActuator);
					actuators.put(myWaterRSDirtyWaterInFlowRateActuatorName , myWaterRSDirtyWaterInFlowRateActuator);
					GreyWaterInFlowRateActuator myWaterRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSGreyWaterInFlowRateActuatorName));
					modules.put(myWaterRSGreyWaterInFlowRateActuatorName , myWaterRSGreyWaterInFlowRateActuator);
					actuators.put(myWaterRSGreyWaterInFlowRateActuatorName , myWaterRSGreyWaterInFlowRateActuator);
					PowerInFlowRateActuator myWaterRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSPowerInFlowRateActuatorName));
					modules.put(myWaterRSPowerInFlowRateActuatorName , myWaterRSPowerInFlowRateActuator);
					actuators.put(myWaterRSPowerInFlowRateActuatorName , myWaterRSPowerInFlowRateActuator);
					PotableWaterOutFlowRateActuator myWaterRSPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myWaterRSPotableWaterOutFlowRateActuatorName));
					modules.put(myWaterRSPotableWaterOutFlowRateActuatorName , myWaterRSPotableWaterOutFlowRateActuator);
					actuators.put(myWaterRSPotableWaterOutFlowRateActuatorName , myWaterRSPotableWaterOutFlowRateActuator);
				}
			}
			//Crew
			{
				PotableWaterInFlowRateActuator myCrewGroupPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupPotableWaterInFlowRateActuatorName));
				GreyWaterOutFlowRateActuator myCrewGroupGreyWaterOutFlowRateActuator = GreyWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupGreyWaterOutFlowRateActuatorName));
				DirtyWaterOutFlowRateActuator myCrewGroupDirtyWaterOutFlowRateActuator = DirtyWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myCrewGroupDirtyWaterOutFlowRateActuatorName));
				
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
					PotableWaterInFlowRateActuator myBiomassRSPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSPotableWaterInFlowRateActuatorName));
					modules.put(myBiomassRSPotableWaterInFlowRateActuatorName , myBiomassRSPotableWaterInFlowRateActuator);
					actuators.put(myBiomassRSPotableWaterInFlowRateActuatorName , myBiomassRSPotableWaterInFlowRateActuator);
					GreyWaterInFlowRateActuator myBiomassRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSGreyWaterInFlowRateActuatorName));
					modules.put(myBiomassRSGreyWaterInFlowRateActuatorName , myBiomassRSGreyWaterInFlowRateActuator);
					actuators.put(myBiomassRSGreyWaterInFlowRateActuatorName , myBiomassRSGreyWaterInFlowRateActuator);
					BiomassOutFlowRateActuator myBiomassRSBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSBiomassOutFlowRateActuatorName));
					modules.put(myBiomassRSBiomassOutFlowRateActuatorName , myBiomassRSBiomassOutFlowRateActuator);
					actuators.put(myBiomassRSBiomassOutFlowRateActuatorName , myBiomassRSBiomassOutFlowRateActuator);
					PowerInFlowRateActuator myBiomassRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myBiomassRSPowerInFlowRateActuatorName));
					modules.put(myBiomassRSPowerInFlowRateActuatorName , myBiomassRSPowerInFlowRateActuator);
					actuators.put(myBiomassRSPowerInFlowRateActuatorName , myBiomassRSPowerInFlowRateActuator);
					HarvestingActuator myShelf0HarvestingActuator = HarvestingActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myShelf0HarvestingActuatorName));
					modules.put(myShelf0HarvestingActuator , myShelf0HarvestingActuatorName);
					actuators.put(myShelf0HarvestingActuator , myShelf0HarvestingActuatorName);
					PlantingActuator myShelf0PlantingActuator = PlantingActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myShelf0PlantingActuatorName));
					modules.put(myShelf0PlantingActuator , myShelf0PlantingActuatorName);
					actuators.put(myShelf0PlantingActuator , myShelf0PlantingActuatorName);
				}
				//Food Processor
				{
					PowerInFlowRateActuator myFoodProcessorPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodProcessorPowerInFlowRateActuatorName));
					modules.put(myFoodProcessorPowerInFlowRateActuatorName , myFoodProcessorPowerInFlowRateActuator);
					actuators.put(myFoodProcessorPowerInFlowRateActuatorName , myFoodProcessorPowerInFlowRateActuator);
					BiomassInFlowRateActuator myFoodProcessorBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodProcessorBiomassInFlowRateActuatorName));
					modules.put(myFoodProcessorBiomassInFlowRateActuatorName , myFoodProcessorBiomassInFlowRateActuator);
					actuators.put(myFoodProcessorBiomassInFlowRateActuatorName , myFoodProcessorBiomassInFlowRateActuator);
					FoodOutFlowRateActuator myFoodProcessorFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myFoodProcessorFoodOutFlowRateActuatorName));
					modules.put(myFoodProcessorFoodOutFlowRateActuatorName , myFoodProcessorFoodOutFlowRateActuator);
					actuators.put(myFoodProcessorFoodOutFlowRateActuatorName , myFoodProcessorFoodOutFlowRateActuator);
				}
			}
			//Waste
				//Incinerator
				{
					DryWasteInFlowRateActuator myIncineratorDryWasteInFlowRateActuator = DryWasteInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorDryWasteInFlowRateActuatorName));
					modules.put(myIncineratorDryWasteInFlowRateActuatorName , myIncineratorDryWasteInFlowRateActuator);
					actuators.put(myIncineratorDryWasteInFlowRateActuatorName , myIncineratorDryWasteInFlowRateActuator);
					O2InFlowRateActuator myIncineratorO2InFlowRateActuator = O2InFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorO2InFlowRateActuatorName));
					modules.put(myIncineratorO2InFlowRateActuatorName , myIncineratorO2InFlowRateActuator);
					actuators.put(myIncineratorO2InFlowRateActuatorName , myIncineratorO2InFlowRateActuator);
					CO2OutFlowRateActuator myIncineratorCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorCO2OutFlowRateActuatorName));
					modules.put(myIncineratorCO2OutFlowRateActuatorName , myIncineratorCO2OutFlowRateActuator);
					actuators.put(myIncineratorCO2OutFlowRateActuatorName , myIncineratorCO2OutFlowRateActuator);
					PowerInFlowRateActuator myIncineratorPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myIncineratorPowerInFlowRateActuatorName));
					modules.put(myIncineratorPowerInFlowRateActuatorName , myIncineratorPowerInFlowRateActuator);
					actuators.put(myIncineratorPowerInFlowRateActuatorName , myIncineratorPowerInFlowRateActuator);
				}
			//Framework
			{
				//Accumulator
				{
					CO2AirEnvironmentInFlowRateActuator myAccumulatorCO2AirEnvironmentInFlowRateActuator = CO2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName));
					modules.put(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName , myAccumulatorCO2AirEnvironmentInFlowRateActuator);
					actuators.put(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName , myAccumulatorCO2AirEnvironmentInFlowRateActuator);
					O2AirEnvironmentInFlowRateActuator myAccumulatorO2AirEnvironmentInFlowRateActuator = O2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorO2AirEnvironmentInFlowRateActuatorName));
					modules.put(myAccumulatorO2AirEnvironmentInFlowRateActuatorName , myAccumulatorO2AirEnvironmentInFlowRateActuator);
					actuators.put(myAccumulatorO2AirEnvironmentInFlowRateActuatorName , myAccumulatorO2AirEnvironmentInFlowRateActuator);
					CO2AirStoreOutFlowRateActuator myAccumulatorCO2AirStoreOutFlowRateActuator = CO2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCO2AirStoreOutFlowRateActuatorName));
					modules.put(myAccumulatorCO2AirStoreOutFlowRateActuatorName , myAccumulatorCO2AirStoreOutFlowRateActuator);
					actuators.put(myAccumulatorCO2AirStoreOutFlowRateActuatorName , myAccumulatorCO2AirStoreOutFlowRateActuator);
					O2AirStoreOutFlowRateActuator myAccumulatorO2AirStoreOutFlowRateActuator = O2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorO2AirStoreOutFlowRateActuatorName));
					modules.put(myAccumulatorO2AirStoreOutFlowRateActuatorName , myAccumulatorO2AirStoreOutFlowRateActuator);
					actuators.put(myAccumulatorO2AirStoreOutFlowRateActuatorName , myAccumulatorO2AirStoreOutFlowRateActuator);
					
					WaterAirEnvironmentInFlowRateActuator myAccumulatorCrewWaterAirEnvironmentInFlowRateActuator = WaterAirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCrewWaterAirEnvironmentInFlowRateActuatorName));
					modules.put(myAccumulatorCrewWaterAirEnvironmentInFlowRateActuatorName , myAccumulatorCrewWaterAirEnvironmentInFlowRateActuator);
					actuators.put(myAccumulatorCrewWaterAirEnvironmentInFlowRateActuatorName , myAccumulatorCrewWaterAirEnvironmentInFlowRateActuator);
					WaterAirEnvironmentInFlowRateActuator myAccumulatorPlantWaterAirEnvironmentInFlowRateActuator = WaterAirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorPlantWaterAirEnvironmentInFlowRateActuatorName));
					modules.put(myAccumulatorPlantWaterAirEnvironmentInFlowRateActuatorName , myAccumulatorPlantWaterAirEnvironmentInFlowRateActuator);
					actuators.put(myAccumulatorPlantWaterAirEnvironmentInFlowRateActuatorName , myAccumulatorPlantWaterAirEnvironmentInFlowRateActuator);
					WaterAirStoreOutFlowRateActuator myAccumulatorCrewWaterAirStoreOutFlowRateActuator = WaterAirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorCrewWaterAirStoreOutFlowRateActuatorName));
					modules.put(myAccumulatorCrewWaterAirStoreOutFlowRateActuatorName , myAccumulatorCrewWaterAirStoreOutFlowRateActuator);
					actuators.put(myAccumulatorCrewWaterAirStoreOutFlowRateActuatorName , myAccumulatorCrewWaterAirStoreOutFlowRateActuator);
					WaterAirStoreOutFlowRateActuator myAccumulatorPlantWaterAirStoreOutFlowRateActuator = WaterAirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myAccumulatorPlantWaterAirStoreOutFlowRateActuatorName));
					modules.put(myAccumulatorPlantWaterAirStoreOutFlowRateActuatorName , myAccumulatorPlantWaterAirStoreOutFlowRateActuator);
					actuators.put(myAccumulatorPlantWaterAirStoreOutFlowRateActuatorName , myAccumulatorPlantWaterAirStoreOutFlowRateActuator);
				
				}
				//Injector
				{
					CO2AirStoreInFlowRateActuator myInjectorCO2AirStoreInFlowRateActuator = CO2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCO2AirStoreInFlowRateActuatorName));
					modules.put(myInjectorCO2AirStoreInFlowRateActuatorName , myInjectorCO2AirStoreInFlowRateActuator);
					actuators.put(myInjectorCO2AirStoreInFlowRateActuatorName , myInjectorCO2AirStoreInFlowRateActuator);
					O2AirStoreInFlowRateActuator myInjectorO2AirStoreInFlowRateActuator = O2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorO2AirStoreInFlowRateActuatorName));
					modules.put(myInjectorO2AirStoreInFlowRateActuatorName , myInjectorO2AirStoreInFlowRateActuator);
					actuators.put(myInjectorO2AirStoreInFlowRateActuatorName , myInjectorO2AirStoreInFlowRateActuator);
					CO2AirEnvironmentOutFlowRateActuator myInjectorCO2AirEnvironmentOutFlowRateActuator = CO2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCO2AirEnvironmentOutFlowRateActuatorName));
					modules.put(myInjectorCO2AirEnvironmentOutFlowRateActuatorName , myInjectorCO2AirEnvironmentOutFlowRateActuator);
					actuators.put(myInjectorCO2AirEnvironmentOutFlowRateActuatorName , myInjectorCO2AirEnvironmentOutFlowRateActuator);
					O2AirEnvironmentOutFlowRateActuator myInjectorO2AirEnvironmentOutFlowRateActuator = O2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorO2AirEnvironmentOutFlowRateActuatorName));
					modules.put(myInjectorO2AirEnvironmentOutFlowRateActuatorName , myInjectorO2AirEnvironmentOutFlowRateActuator);
					actuators.put(myInjectorO2AirEnvironmentOutFlowRateActuatorName , myInjectorO2AirEnvironmentOutFlowRateActuator);
				
					NitrogenAirEnvironmentOutFlowRateActuator myInjectorCrewNitrogenAirEnvironmentOutFlowRateActuator = NitrogenAirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCrewNitrogenAirEnvironmentOutFlowRateActuatorName));
					modules.put(myInjectorCrewNitrogenAirEnvironmentOutFlowRateActuatorName , myInjectorCrewNitrogenAirEnvironmentOutFlowRateActuator);
					actuators.put(myInjectorCrewNitrogenAirEnvironmentOutFlowRateActuatorName , myInjectorCrewNitrogenAirEnvironmentOutFlowRateActuator);
					NitrogenAirEnvironmentOutFlowRateActuator myInjectorPlantNitrogenAirEnvironmentOutFlowRateActuator = NitrogenAirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorPlantNitrogenAirEnvironmentOutFlowRateActuatorName));
					modules.put(myInjectorPlantNitrogenAirEnvironmentOutFlowRateActuatorName , myInjectorPlantNitrogenAirEnvironmentOutFlowRateActuator);
					actuators.put(myInjectorPlantNitrogenAirEnvironmentOutFlowRateActuatorName , myInjectorPlantNitrogenAirEnvironmentOutFlowRateActuator);
					NitrogenAirStoreInFlowRateActuator myInjectorCrewNitrogenAirStoreInFlowRateActuator = NitrogenAirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorCrewNitrogenAirStoreInFlowRateActuatorName));
					modules.put(myInjectorCrewNitrogenAirStoreInFlowRateActuatorName , myInjectorCrewNitrogenAirStoreInFlowRateActuator);
					actuators.put(myInjectorCrewNitrogenAirStoreInFlowRateActuatorName , myInjectorCrewNitrogenAirStoreInFlowRateActuator);
					NitrogenAirStoreInFlowRateActuator myInjectorPlantNitrogenAirStoreInFlowRateActuator = NitrogenAirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(myInjectorPlantNitrogenAirStoreInFlowRateActuatorName));
					modules.put(myInjectorPlantNitrogenAirStoreInFlowRateActuatorName , myInjectorPlantNitrogenAirStoreInFlowRateActuator);
					actuators.put(myInjectorPlantNitrogenAirStoreInFlowRateActuatorName , myInjectorPlantNitrogenAirStoreInFlowRateActuator);
				}
			}
			System.out.println("BioHolder: Collecting framework references to modules...");
			myBioDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str("BioDriver"));
			hasCollectedReferences = true;
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace();
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.sleepAwhile();
			collectReferences();
		}
		catch (Exception e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			e.printStackTrace();
			OrbUtils.resetInit();
			OrbUtils.sleepAwhile();
			collectReferences();
		}
	}
}



