package biosim.client.util;

import java.util.*;
import biosim.idl.framework.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.simulation.food.*;

/**
 * Holds references to the servers
 *
 * @author Scott Bell
 */
public class BioHolder{
	public BioDriver theBioDriver;
	
	public Map theModulesMapped;
	
	//Upper Categories
	public List theModules;
	public List theSimModules;
	public List theSensors;
	public List theActuators;

	//Specific Modules
	//Simulation
	//Air
	public List theAirRSModules;
	public List theO2Stores;
	public List theCO2Stores;
	public List theH2Stores;
	public List theNitrogenStores;
	//Crew
	public List theCrewGroups;
	//Environment
	public List theSimEnvironments;
	//Food
	public List theFoodProcessors;
	public List theBiomassRSModules;
	public List theBiomassStores;
	public List theFoodStores;
	//Framework
	public List theAccumulators;
	public List theInjectors;
	//Power
	public List thePowerPSModules;
	public List thePowerStores;
	//Waste
	public List theIncinerators;
	public List theDryWasteStores;
	//Water
	public List theWaterRSModules;
	public List thePotableWaterStores;
	public List theGreyWaterStores;
	public List theDirtyWaterStores;
	//Sensors
	//Air
	public List theCO2InFlowRateSensors;
	public List theCO2OutFlowRateSensors;
	public List theCO2StoreLevelSensors;
	public List theO2InFlowRateSensors;
	public List theO2OutFlowRateSensors;
	public List theO2StoreLevelSensors;
	public List theH2InFlowRateSensors;
	public List theH2OutFlowRateSensors;
	public List theH2StoreLevelSensors;
	public List theNitrogenInFlowRateSensors;
	public List theNitrogenOutFlowRateSensors;
	public List theNitrogenStoreLevelSensors;
	//Crew
	public List theCrewGroupDeathSensors;
	public List theCrewGroupProductivitySensors;
	public List theCrewGroupAnyDeadSensors;
	//Environment
	public List theAirInFlowRateSensors;
	public List theAirOutFlowRateSensors;
	public List theCO2AirConcentrationSensors;
	public List theCO2AirPressureSensors;
	public List theCO2AirEnvironmentInFlowRateSensors;
	public List theCO2AirEnvironmentOutFlowRateSensors;
	public List theCO2AirStoreInFlowRateSensors;
	public List theCO2AirStoreOutFlowRateSensors;
	public List theO2AirConcentrationSensors;
	public List theO2AirPressureSensors;
	public List theO2AirEnvironmentInFlowRateSensors;
	public List theO2AirEnvironmentOutFlowRateSensors;
	public List theO2AirStoreInFlowRateSensors;
	public List theO2AirStoreOutFlowRateSensors;
	public List theNitrogenAirConcentrationSensors;
	public List theNitrogenAirPressureSensors;
	public List theNitrogenAirEnvironmentInFlowRateSensors;
	public List theNitrogenAirEnvironmentOutFlowRateSensors;
	public List theNitrogenAirStoreInFlowRateSensors;
	public List theNitrogenAirStoreOutFlowRateSensors;
	public List theWaterAirConcentrationSensors;
	public List theWaterAirPressureSensors;
	public List theWaterAirEnvironmentInFlowRateSensors;
	public List theWaterAirEnvironmentOutFlowRateSensors;
	public List theWaterAirStoreInFlowRateSensors;
	public List theWaterAirStoreOutFlowRateSensors;
	public List theOtherAirConcentrationSensors;
	public List theOtherAirPressureSensors;
	//Food
	public List theBiomassInFlowRateSensors;
	public List theBiomassOutFlowRateSensors;
	public List theBiomassStoreLevelSensors;
	public List theFoodInFlowRateSensors;
	public List theFoodOutFlowRateSensors;
	public List theFoodStoreLevelSensors;
	public List theHarvestSensors;
	//Framework
	public List theStoreOverflowSensors;
	public List theStoreLevelSensors;
	//Power
	public List thePowerInFlowRateSensors;
	public List thePowerOutFlowRateSensors;
	public List thePowerStoreLevelSensors;
	//Waste
	public List theDryWasteInFlowRateSensors;
	public List theDryWasteOutFlowRateSensors;
	public List theDryWasteStoreLevelSensors;
	//Water
	public List thePotableWaterInFlowRateSensors;
	public List thePotableWaterOutFlowRateSensors;
	public List thePotableWaterStoreLevelSensors;
	public List theGreyWaterInFlowRateSensors;
	public List theGreyWaterOutFlowRateSensors;
	public List theGreyWaterStoreLevelSensors;
	public List theDirtyWaterInFlowRateSensors;
	public List theDirtyWaterOutFlowRateSensors;
	public List theDirtyWaterStoreLevelSensors;
	//Actuators
	//Air
	public List theCO2InFlowRateActuators;
	public List theCO2OutFlowRateActuators;
	public List theO2InFlowRateActuators;
	public List theO2OutFlowRateActuators;
	public List theH2InFlowRateActuators;
	public List theH2OutFlowRateActuators;
	public List theNitrogenInFlowRateActuators;
	public List theNitrogenOutFlowRateActuators;
	//Environment
	public List theAirInFlowRateActuators;
	public List theAirOutFlowRateActuators;
	public List theCO2AirEnvironmentInFlowRateActuators;
	public List theCO2AirEnvironmentOutFlowRateActuators;
	public List theCO2AirStoreInFlowRateActuators;
	public List theCO2AirStoreOutFlowRateActuators;
	public List theO2AirEnvironmentInFlowRateActuators;
	public List theO2AirEnvironmentOutFlowRateActuators;
	public List theO2AirStoreInFlowRateActuators;
	public List theO2AirStoreOutFlowRateActuators;
	public List theNitrogenAirEnvironmentInFlowRateActuators;
	public List theNitrogenAirEnvironmentOutFlowRateActuators;
	public List theNitrogenAirStoreInFlowRateActuators;
	public List theNitrogenAirStoreOutFlowRateActuators;
	public List theWaterAirEnvironmentInFlowRateActuators;
	public List theWaterAirEnvironmentOutFlowRateActuators;
	public List theWaterAirStoreInFlowRateActuators;
	public List theWaterAirStoreOutFlowRateActuators;
	//Food
	public List theBiomassInFlowRateActuators;
	public List theBiomassOutFlowRateActuators;
	public List theFoodInFlowRateActuators;
	public List theFoodOutFlowRateActuators;
	public List thePlantingActuators;
	public List theHarvestingActuators;
	//Power
	public List thePowerInFlowRateActuators;
	public List thePowerOutFlowRateActuators;
	//Waste
	public List theDryWasteInFlowRateActuators;
	public List theDryWasteOutFlowRateActuators;
	//Water
	public List thePotableWaterInFlowRateActuators;
	public List thePotableWaterOutFlowRateActuators;
	public List theGreyWaterInFlowRateActuators;
	public List theGreyWaterOutFlowRateActuators;
	public List theDirtyWaterInFlowRateActuators;
	public List theDirtyWaterOutFlowRateActuators;

	protected BioHolder(){
		theModulesMapped = new Hashtable();
		//Upper Categories
		theModules = new Vector();
		theSimModules = new Vector();
		theSensors = new Vector();
		theActuators = new Vector();

		//Specific Modules
		//Simulation
		//Air
		theAirRSModules = new Vector();
		theO2Stores = new Vector();
		theCO2Stores = new Vector();
		theNitrogenStores = new Vector();
		theH2Stores = new Vector();
		//Crew
		theCrewGroups = new Vector();
		//Environment
		theSimEnvironments = new Vector();
		//Food
		theFoodProcessors = new Vector();
		theBiomassRSModules = new Vector();
		theBiomassStores = new Vector();
		theFoodStores = new Vector();
		//Framework
		theAccumulators = new Vector();
		theInjectors = new Vector();
		//Power
		thePowerPSModules = new Vector();
		thePowerStores = new Vector();
		//Waste
		theIncinerators = new Vector();
		theDryWasteStores = new Vector();
		//Water
		theWaterRSModules = new Vector();
		thePotableWaterStores = new Vector();
		theGreyWaterStores = new Vector();
		theDirtyWaterStores = new Vector();
		//Sensors
		//Air
		theCO2InFlowRateSensors = new Vector();
		theCO2OutFlowRateSensors = new Vector();
		theCO2StoreLevelSensors = new Vector();
		theO2InFlowRateSensors = new Vector();
		theO2OutFlowRateSensors = new Vector();
		theO2StoreLevelSensors = new Vector();
		theH2InFlowRateSensors = new Vector();
		theH2OutFlowRateSensors = new Vector();
		theH2StoreLevelSensors = new Vector();
		theNitrogenInFlowRateSensors = new Vector();
		theNitrogenOutFlowRateSensors = new Vector();
		theNitrogenStoreLevelSensors = new Vector();
		//Crew
		theCrewGroupDeathSensors = new Vector();
		theCrewGroupProductivitySensors = new Vector();
		theCrewGroupAnyDeadSensors = new Vector();
		//Environment
		theAirInFlowRateSensors = new Vector();
		theAirOutFlowRateSensors = new Vector();
		theCO2AirConcentrationSensors = new Vector();
		theCO2AirPressureSensors = new Vector();
		theCO2AirEnvironmentInFlowRateSensors = new Vector();
		theCO2AirEnvironmentOutFlowRateSensors = new Vector();
		theCO2AirStoreInFlowRateSensors = new Vector();
		theCO2AirStoreOutFlowRateSensors = new Vector();
		theO2AirConcentrationSensors = new Vector();
		theO2AirPressureSensors = new Vector();
		theO2AirEnvironmentInFlowRateSensors = new Vector();
		theO2AirEnvironmentOutFlowRateSensors = new Vector();
		theO2AirStoreInFlowRateSensors = new Vector();
		theO2AirStoreOutFlowRateSensors = new Vector();
		theNitrogenAirConcentrationSensors = new Vector();
		theNitrogenAirPressureSensors = new Vector();
		theNitrogenAirEnvironmentInFlowRateSensors = new Vector();
		theNitrogenAirEnvironmentOutFlowRateSensors = new Vector();
		theNitrogenAirStoreInFlowRateSensors = new Vector();
		theNitrogenAirStoreOutFlowRateSensors = new Vector();
		theWaterAirConcentrationSensors = new Vector();
		theWaterAirPressureSensors = new Vector();
		theWaterAirEnvironmentInFlowRateSensors = new Vector();
		theWaterAirEnvironmentOutFlowRateSensors = new Vector();
		theWaterAirStoreInFlowRateSensors = new Vector();
		theWaterAirStoreOutFlowRateSensors = new Vector();
		theOtherAirConcentrationSensors = new Vector();
		theOtherAirPressureSensors = new Vector();
		//Food
		theBiomassInFlowRateSensors = new Vector();
		theBiomassOutFlowRateSensors = new Vector();
		theBiomassStoreLevelSensors = new Vector();
		theFoodInFlowRateSensors = new Vector();
		theFoodOutFlowRateSensors = new Vector();
		theFoodStoreLevelSensors = new Vector();
		theHarvestSensors = new Vector();
		//Framework
		theStoreOverflowSensors = new Vector();
		theStoreLevelSensors = new Vector();
		//Power
		thePowerInFlowRateSensors = new Vector();
		thePowerOutFlowRateSensors = new Vector();
		thePowerStoreLevelSensors = new Vector();
		//Waste
		theDryWasteInFlowRateSensors = new Vector();
		theDryWasteOutFlowRateSensors = new Vector();
		theDryWasteStoreLevelSensors = new Vector();
		//Water
		thePotableWaterInFlowRateSensors = new Vector();
		thePotableWaterOutFlowRateSensors = new Vector();
		thePotableWaterStoreLevelSensors = new Vector();
		theGreyWaterInFlowRateSensors = new Vector();
		theGreyWaterOutFlowRateSensors = new Vector();
		theGreyWaterStoreLevelSensors = new Vector();
		theDirtyWaterInFlowRateSensors = new Vector();
		theDirtyWaterOutFlowRateSensors = new Vector();
		theDirtyWaterStoreLevelSensors = new Vector();
		//Actuators
		//Air
		theCO2InFlowRateActuators = new Vector();
		theCO2OutFlowRateActuators = new Vector();
		theO2InFlowRateActuators = new Vector();
		theO2OutFlowRateActuators = new Vector();
		theH2InFlowRateActuators = new Vector();
		theH2OutFlowRateActuators = new Vector();
		theNitrogenInFlowRateActuators = new Vector();
		theNitrogenOutFlowRateActuators = new Vector();
		//Environment
		theAirInFlowRateActuators = new Vector();
		theAirOutFlowRateActuators = new Vector();
		theCO2AirEnvironmentInFlowRateActuators = new Vector();
		theCO2AirEnvironmentOutFlowRateActuators = new Vector();
		theCO2AirStoreInFlowRateActuators = new Vector();
		theCO2AirStoreOutFlowRateActuators = new Vector();
		theO2AirEnvironmentInFlowRateActuators = new Vector();
		theO2AirEnvironmentOutFlowRateActuators = new Vector();
		theO2AirStoreInFlowRateActuators = new Vector();
		theO2AirStoreOutFlowRateActuators = new Vector();
		theNitrogenAirEnvironmentInFlowRateActuators = new Vector();
		theNitrogenAirEnvironmentOutFlowRateActuators = new Vector();
		theNitrogenAirStoreInFlowRateActuators = new Vector();
		theNitrogenAirStoreOutFlowRateActuators = new Vector();
		theWaterAirEnvironmentInFlowRateActuators = new Vector();
		theWaterAirEnvironmentOutFlowRateActuators = new Vector();
		theWaterAirStoreInFlowRateActuators = new Vector();
		theWaterAirStoreOutFlowRateActuators = new Vector();
		//Food
		theBiomassInFlowRateActuators = new Vector();
		theBiomassOutFlowRateActuators = new Vector();
		theFoodInFlowRateActuators = new Vector();
		theFoodOutFlowRateActuators = new Vector();
		thePlantingActuators = new Vector();
		theHarvestingActuators = new Vector();
		//Power
		thePowerInFlowRateActuators = new Vector();
		thePowerOutFlowRateActuators = new Vector();
		//Waste
		theDryWasteInFlowRateActuators = new Vector();
		theDryWasteOutFlowRateActuators = new Vector();
		//Water
		thePotableWaterInFlowRateActuators = new Vector();
		thePotableWaterOutFlowRateActuators = new Vector();
		theGreyWaterInFlowRateActuators = new Vector();
		theGreyWaterOutFlowRateActuators = new Vector();
		theDirtyWaterInFlowRateActuators = new Vector();
		theDirtyWaterOutFlowRateActuators = new Vector();
	}
	
	public GenericSensor getShelfSensorAttachedTo(List sensorList, BioModule moduleWatched, int shelfIndex){
		for (Iterator iter = sensorList.iterator(); iter.hasNext();){
			ShelfSensor currentSensor = (ShelfSensor)(iter.next());
			if (currentSensor.getInputModule()._is_equivalent(moduleWatched)){
				BiomassRS currentBiomassRS = BiomassRSHelper.narrow(currentSensor.getInputModule());
				Shelf[] shelfArray = currentBiomassRS.getShelves();
				for (int i = 0; i < shelfArray.length; i++)
					if (currentSensor.getInput()._is_equivalent(shelfArray[i]))
						return currentSensor;
			}
		}
		return null;
	}
	
	public GenericActuator getShelfActuatorAttachedTo(List sensorList, BioModule moduleWatched, int shelfIndex){
		for (Iterator iter = sensorList.iterator(); iter.hasNext();){
			ShelfActuator currentActuator = (ShelfActuator)(iter.next());
			if (currentActuator.getOutputModule()._is_equivalent(moduleWatched)){
				BiomassRS currentBiomassRS = BiomassRSHelper.narrow(currentActuator.getOutputModule());
				Shelf[] shelfArray = currentBiomassRS.getShelves();
				for (int i = 0; i < shelfArray.length; i++)
					if (currentActuator.getOutput()._is_equivalent(shelfArray[i]))
						return currentActuator;
			}
		}
		return null;
	}
	
	public GenericSensor getSensorAttachedTo(List sensorList, BioModule moduleWatched){
		for (Iterator iter = sensorList.iterator(); iter.hasNext();){
			GenericSensor currentSensor = (GenericSensor)(iter.next());
			if (currentSensor.getInputModule()._is_equivalent(moduleWatched))
				return currentSensor;
		}
		return null;
	}
	
	public GenericActuator getActuatorAttachedTo(List actuatorList, BioModule moduleWatched){
		for (Iterator iter = actuatorList.iterator(); iter.hasNext();){
			GenericActuator currentActuator = (GenericActuator)(iter.next());
			if (currentActuator.getOutputModule()._is_equivalent(moduleWatched))
				return currentActuator;
		}
		return null;
	}

	protected void coallateLists(){
		//Make named list;
		for (Iterator iter = theModules.iterator(); iter.hasNext();){
			BioModule currentModule = (BioModule)(iter.next());
			theModulesMapped.put(currentModule.getModuleName(), currentModule);
		}
		//Specific Modules
		//Simulation
		//Air
		theSimModules.addAll(theAirRSModules);
		theSimModules.addAll(theO2Stores);
		theSimModules.addAll(theCO2Stores);
		theSimModules.addAll(theNitrogenStores);
		theSimModules.addAll(theH2Stores);
		//Crew
		theSimModules.addAll(theCrewGroups);
		//Environment
		theSimModules.addAll(theSimEnvironments);
		//Food
		theSimModules.addAll(theFoodProcessors);
		theSimModules.addAll(theBiomassRSModules);
		theSimModules.addAll(theBiomassStores);
		theSimModules.addAll(theFoodStores);
		//Framework
		theSimModules.addAll(theAccumulators);
		theSimModules.addAll(theInjectors);
		//Power
		theSimModules.addAll(thePowerPSModules);
		theSimModules.addAll(thePowerStores);
		//Waste
		theSimModules.addAll(theIncinerators);
		theSimModules.addAll(theDryWasteStores);
		//Water
		theSimModules.addAll(theWaterRSModules);
		theSimModules.addAll(thePotableWaterStores);
		theSimModules.addAll(theGreyWaterStores);
		theSimModules.addAll(theDirtyWaterStores);
		//Sensors
		//Air
		theSensors.addAll(theCO2InFlowRateSensors);
		theSensors.addAll(theCO2OutFlowRateSensors);
		theSensors.addAll(theCO2StoreLevelSensors);
		theSensors.addAll(theO2InFlowRateSensors);
		theSensors.addAll(theO2OutFlowRateSensors);
		theSensors.addAll(theO2StoreLevelSensors);
		theSensors.addAll(theH2InFlowRateSensors);
		theSensors.addAll(theH2OutFlowRateSensors);
		theSensors.addAll(theH2StoreLevelSensors);
		theSensors.addAll(theNitrogenInFlowRateSensors);
		theSensors.addAll(theNitrogenOutFlowRateSensors);
		theSensors.addAll(theNitrogenStoreLevelSensors);
		//Crew
		theSensors.addAll(theCrewGroupDeathSensors);
		theSensors.addAll(theCrewGroupProductivitySensors);
		theSensors.addAll(theCrewGroupAnyDeadSensors);
		//Environment
		theSensors.addAll(theAirInFlowRateSensors);
		theSensors.addAll(theAirOutFlowRateSensors);
		theSensors.addAll(theCO2AirConcentrationSensors);
		theSensors.addAll(theCO2AirPressureSensors);
		theSensors.addAll(theCO2AirEnvironmentInFlowRateSensors);
		theSensors.addAll(theCO2AirEnvironmentOutFlowRateSensors);
		theSensors.addAll(theCO2AirStoreInFlowRateSensors);
		theSensors.addAll(theCO2AirStoreOutFlowRateSensors);
		theSensors.addAll(theO2AirConcentrationSensors);
		theSensors.addAll(theO2AirPressureSensors);
		theSensors.addAll(theO2AirEnvironmentInFlowRateSensors);
		theSensors.addAll(theO2AirEnvironmentOutFlowRateSensors);
		theSensors.addAll(theO2AirStoreInFlowRateSensors);
		theSensors.addAll(theO2AirStoreOutFlowRateSensors);
		theSensors.addAll(theNitrogenAirConcentrationSensors);
		theSensors.addAll(theNitrogenAirPressureSensors);
		theSensors.addAll(theNitrogenAirEnvironmentInFlowRateSensors);
		theSensors.addAll(theNitrogenAirEnvironmentOutFlowRateSensors);
		theSensors.addAll(theNitrogenAirStoreInFlowRateSensors);
		theSensors.addAll(theNitrogenAirStoreOutFlowRateSensors);
		theSensors.addAll(theWaterAirConcentrationSensors);
		theSensors.addAll(theWaterAirPressureSensors);
		theSensors.addAll(theWaterAirEnvironmentInFlowRateSensors);
		theSensors.addAll(theWaterAirEnvironmentOutFlowRateSensors);
		theSensors.addAll(theWaterAirStoreInFlowRateSensors);
		theSensors.addAll(theWaterAirStoreOutFlowRateSensors);
		theSensors.addAll(theOtherAirConcentrationSensors);
		theSensors.addAll(theOtherAirPressureSensors);
		//Food
		theSensors.addAll(theBiomassInFlowRateSensors);
		theSensors.addAll(theBiomassOutFlowRateSensors);
		theSensors.addAll(theBiomassStoreLevelSensors);
		theSensors.addAll(theFoodInFlowRateSensors);
		theSensors.addAll(theFoodOutFlowRateSensors);
		theSensors.addAll(theFoodStoreLevelSensors);
		theSensors.addAll(theHarvestSensors);
		//Framework
		theSensors.addAll(theStoreOverflowSensors);
		theSensors.addAll(theStoreLevelSensors);
		//Power
		theSensors.addAll(thePowerInFlowRateSensors);
		theSensors.addAll(thePowerOutFlowRateSensors);
		theSensors.addAll(thePowerStoreLevelSensors);
		//Waste
		theSensors.addAll(theDryWasteInFlowRateSensors);
		theSensors.addAll(theDryWasteOutFlowRateSensors);
		theSensors.addAll(theDryWasteStoreLevelSensors);
		//Water
		theSensors.addAll(thePotableWaterInFlowRateSensors);
		theSensors.addAll(thePotableWaterOutFlowRateSensors);
		theSensors.addAll(thePotableWaterStoreLevelSensors);
		theSensors.addAll(theGreyWaterInFlowRateSensors);
		theSensors.addAll(theGreyWaterOutFlowRateSensors);
		theSensors.addAll(theGreyWaterStoreLevelSensors);
		theSensors.addAll(theDirtyWaterInFlowRateSensors);
		theSensors.addAll(theDirtyWaterOutFlowRateSensors);
		theSensors.addAll(theDirtyWaterStoreLevelSensors);
		//Actuators
		//Air
		theActuators.addAll(theCO2InFlowRateActuators);
		theActuators.addAll(theCO2OutFlowRateActuators);
		theActuators.addAll(theO2InFlowRateActuators);
		theActuators.addAll(theO2OutFlowRateActuators);
		theActuators.addAll(theH2InFlowRateActuators);
		theActuators.addAll(theH2OutFlowRateActuators);
		theActuators.addAll(theNitrogenInFlowRateActuators);
		theActuators.addAll(theNitrogenOutFlowRateActuators);
		//Environment
		theActuators.addAll(theAirInFlowRateActuators);
		theActuators.addAll(theAirOutFlowRateActuators);
		theActuators.addAll(theCO2AirEnvironmentInFlowRateActuators);
		theActuators.addAll(theCO2AirEnvironmentOutFlowRateActuators);
		theActuators.addAll(theCO2AirStoreInFlowRateActuators);
		theActuators.addAll(theCO2AirStoreOutFlowRateActuators);
		theActuators.addAll(theO2AirEnvironmentInFlowRateActuators);
		theActuators.addAll(theO2AirEnvironmentOutFlowRateActuators);
		theActuators.addAll(theO2AirStoreInFlowRateActuators);
		theActuators.addAll(theO2AirStoreOutFlowRateActuators);
		theActuators.addAll(theNitrogenAirEnvironmentInFlowRateActuators);
		theActuators.addAll(theNitrogenAirEnvironmentOutFlowRateActuators);
		theActuators.addAll(theNitrogenAirStoreInFlowRateActuators);
		theActuators.addAll(theNitrogenAirStoreOutFlowRateActuators);
		theActuators.addAll(theWaterAirEnvironmentInFlowRateActuators);
		theActuators.addAll(theWaterAirEnvironmentOutFlowRateActuators);
		theActuators.addAll(theWaterAirStoreInFlowRateActuators);
		theActuators.addAll(theWaterAirStoreOutFlowRateActuators);
		//Food
		theActuators.addAll(theBiomassInFlowRateActuators);
		theActuators.addAll(theBiomassOutFlowRateActuators);
		theActuators.addAll(theFoodInFlowRateActuators);
		theActuators.addAll(theFoodOutFlowRateActuators);
		theActuators.addAll(thePlantingActuators);
		theActuators.addAll(theHarvestingActuators);
		//Power
		theActuators.addAll(thePowerInFlowRateActuators);
		theActuators.addAll(thePowerOutFlowRateActuators);
		//Waste
		theActuators.addAll(theDryWasteInFlowRateActuators);
		theActuators.addAll(theDryWasteOutFlowRateActuators);
		//Water
		theActuators.addAll(thePotableWaterInFlowRateActuators);
		theActuators.addAll(thePotableWaterOutFlowRateActuators);
		theActuators.addAll(theGreyWaterInFlowRateActuators);
		theActuators.addAll(theGreyWaterOutFlowRateActuators);
		theActuators.addAll(theDirtyWaterInFlowRateActuators);
		theActuators.addAll(theDirtyWaterOutFlowRateActuators);

		theModules.addAll(theSimModules);
		theModules.addAll(theSensors);
		theModules.addAll(theActuators);
	}

	protected void reset(){
		theModulesMapped.clear();
		//Upper Categories
		theModules.clear();
		theSimModules.clear();
		theSensors.clear();
		theActuators.clear();

		//Specific Modules
		//Simulation
		//Air
		theAirRSModules.clear();
		theO2Stores.clear();
		theCO2Stores.clear();
		theNitrogenStores.clear();
		theH2Stores.clear();
		//Crew
		theCrewGroups.clear();
		//Environment
		theSimEnvironments.clear();
		//Food
		theFoodProcessors.clear();
		theBiomassRSModules.clear();
		theBiomassStores.clear();
		theFoodStores.clear();
		//Framework
		theAccumulators.clear();
		theInjectors.clear();
		//Power
		thePowerPSModules.clear();
		thePowerStores.clear();
		//Waste
		theIncinerators.clear();
		theDryWasteStores.clear();
		//Water
		theWaterRSModules.clear();
		thePotableWaterStores.clear();
		theGreyWaterStores.clear();
		theDirtyWaterStores.clear();
		//Sensors
		//Air
		theCO2InFlowRateSensors.clear();
		theCO2OutFlowRateSensors.clear();
		theCO2StoreLevelSensors.clear();
		theO2InFlowRateSensors.clear();
		theO2OutFlowRateSensors.clear();
		theO2StoreLevelSensors.clear();
		theH2InFlowRateSensors.clear();
		theH2OutFlowRateSensors.clear();
		theH2StoreLevelSensors.clear();
		theNitrogenInFlowRateSensors.clear();
		theNitrogenOutFlowRateSensors.clear();
		theNitrogenStoreLevelSensors.clear();
		//Crew
		theCrewGroupDeathSensors.clear();
		theCrewGroupProductivitySensors.clear();
		theCrewGroupAnyDeadSensors.clear();
		//Environment
		theAirInFlowRateSensors.clear();
		theAirOutFlowRateSensors.clear();
		theCO2AirConcentrationSensors.clear();
		theCO2AirPressureSensors.clear();
		theCO2AirEnvironmentInFlowRateSensors.clear();
		theCO2AirEnvironmentOutFlowRateSensors.clear();
		theCO2AirStoreInFlowRateSensors.clear();
		theCO2AirStoreOutFlowRateSensors.clear();
		theO2AirConcentrationSensors.clear();
		theO2AirPressureSensors.clear();
		theO2AirEnvironmentInFlowRateSensors.clear();
		theO2AirEnvironmentOutFlowRateSensors.clear();
		theO2AirStoreInFlowRateSensors.clear();
		theO2AirStoreOutFlowRateSensors.clear();
		theNitrogenAirConcentrationSensors.clear();
		theNitrogenAirPressureSensors.clear();
		theNitrogenAirEnvironmentInFlowRateSensors.clear();
		theNitrogenAirEnvironmentOutFlowRateSensors.clear();
		theNitrogenAirStoreInFlowRateSensors.clear();
		theNitrogenAirStoreOutFlowRateSensors.clear();
		theWaterAirConcentrationSensors.clear();
		theWaterAirPressureSensors.clear();
		theWaterAirEnvironmentInFlowRateSensors.clear();
		theWaterAirEnvironmentOutFlowRateSensors.clear();
		theWaterAirStoreInFlowRateSensors.clear();
		theWaterAirStoreOutFlowRateSensors.clear();
		theOtherAirConcentrationSensors.clear();
		theOtherAirPressureSensors.clear();
		//Food
		theBiomassInFlowRateSensors.clear();
		theBiomassOutFlowRateSensors.clear();
		theBiomassStoreLevelSensors.clear();
		theFoodInFlowRateSensors.clear();
		theFoodOutFlowRateSensors.clear();
		theFoodStoreLevelSensors.clear();
		theHarvestSensors.clear();
		//Framework
		theStoreOverflowSensors.clear();
		theStoreLevelSensors.clear();
		//Power
		thePowerInFlowRateSensors.clear();
		thePowerOutFlowRateSensors.clear();
		thePowerStoreLevelSensors.clear();
		//Waste
		theDryWasteInFlowRateSensors.clear();
		theDryWasteOutFlowRateSensors.clear();
		theDryWasteStoreLevelSensors.clear();
		//Water
		thePotableWaterInFlowRateSensors.clear();
		thePotableWaterOutFlowRateSensors.clear();
		thePotableWaterStoreLevelSensors.clear();
		theGreyWaterInFlowRateSensors.clear();
		theGreyWaterOutFlowRateSensors.clear();
		theGreyWaterStoreLevelSensors.clear();
		theDirtyWaterInFlowRateSensors.clear();
		theDirtyWaterOutFlowRateSensors.clear();
		theDirtyWaterStoreLevelSensors.clear();
		//Actuators
		//Air
		theCO2InFlowRateActuators.clear();
		theCO2OutFlowRateActuators.clear();
		theO2InFlowRateActuators.clear();
		theO2OutFlowRateActuators.clear();
		theH2InFlowRateActuators.clear();
		theH2OutFlowRateActuators.clear();
		theNitrogenInFlowRateActuators.clear();
		theNitrogenOutFlowRateActuators.clear();
		//Environment
		theAirInFlowRateActuators.clear();
		theAirOutFlowRateActuators.clear();
		theCO2AirEnvironmentInFlowRateActuators.clear();
		theCO2AirEnvironmentOutFlowRateActuators.clear();
		theCO2AirStoreInFlowRateActuators.clear();
		theCO2AirStoreOutFlowRateActuators.clear();
		theO2AirEnvironmentInFlowRateActuators.clear();
		theO2AirEnvironmentOutFlowRateActuators.clear();
		theO2AirStoreInFlowRateActuators.clear();
		theO2AirStoreOutFlowRateActuators.clear();
		theNitrogenAirEnvironmentInFlowRateActuators.clear();
		theNitrogenAirEnvironmentOutFlowRateActuators.clear();
		theNitrogenAirStoreInFlowRateActuators.clear();
		theNitrogenAirStoreOutFlowRateActuators.clear();
		theWaterAirEnvironmentInFlowRateActuators.clear();
		theWaterAirEnvironmentOutFlowRateActuators.clear();
		theWaterAirStoreInFlowRateActuators.clear();
		theWaterAirStoreOutFlowRateActuators.clear();
		//Food
		theBiomassInFlowRateActuators.clear();
		theBiomassOutFlowRateActuators.clear();
		theFoodInFlowRateActuators.clear();
		theFoodOutFlowRateActuators.clear();
		thePlantingActuators.clear();
		theHarvestingActuators.clear();
		//Power
		thePowerInFlowRateActuators.clear();
		thePowerOutFlowRateActuators.clear();
		//Waste
		theDryWasteInFlowRateActuators.clear();
		theDryWasteOutFlowRateActuators.clear();
		//Water
		thePotableWaterInFlowRateActuators.clear();
		thePotableWaterOutFlowRateActuators.clear();
		theGreyWaterInFlowRateActuators.clear();
		theGreyWaterOutFlowRateActuators.clear();
		theDirtyWaterInFlowRateActuators.clear();
		theDirtyWaterOutFlowRateActuators.clear();
	}
}
