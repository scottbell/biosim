package biosim.client.util;

import java.util.*;

/**
 * Holds references to the servers
 *
 * @author Scott Bell
 */
public class BioHolder{
	//Upper Categories
	public List myModules;
	public List mySimModules;
	public List mySensors;
	public List myActuators;

	//Specific Modules
	//Simulation
	//Air
	public List myAirRSModules;
	public List myO2Stores;
	public List myCO2Stores;
	public List myH2Stores;
	public List myNitrogenStores;
	//Crew
	public List myCrewGroups;
	//Environment
	public List mySimEnvironments;
	//Food
	public List myFoodProcessors;
	public List myBiomassRSModules;
	public List myBiomassStores;
	public List myFoodStores;
	//Framework
	public List myAccumulators;
	public List myInjectors;
	//Power
	public List myPowerPSModules;
	public List myPowerStores;
	//Waste
	public List myIncinerators;
	public List myDryWasteStores;
	//Water
	public List myWaterRSModules;
	public List myPotableWaterStores;
	public List myGreyWaterStores;
	public List myDirtyWaterStores;
	//Sensors
	//Air
	public List myCO2InFlowRateSensors;
	public List myCO2OutFlowRateSensors;
	public List myCO2StoreLevelSensors;
	public List myO2InFlowRateSensors;
	public List myO2OutFlowRateSensors;
	public List myO2StoreLevelSensors;
	public List myH2InFlowRateSensors;
	public List myH2OutFlowRateSensors;
	public List myH2StoreLevelSensors;
	public List myNitrogenInFlowRateSensors;
	public List myNitrogenOutFlowRateSensors;
	public List myNitrogenStoreLevelSensors;
	//Crew
	public List myCrewGroupDeathSensors;
	public List myCrewGroupProductivitySensors;
	public List myCrewGroupAnyDeadSensors;
	//Environment
	public List myAirInFlowRateSensors;
	public List myAirOutFlowRateSensors;
	public List myCO2AirConcentrationSensors;
	public List myCO2AirPressureSensors;
	public List myCO2AirEnvironmentInFlowRateSensors;
	public List myCO2AirEnvironmentOutFlowRateSensors;
	public List myCO2AirStoreInFlowRateSensors;
	public List myCO2AirStoreOutFlowRateSensors;
	public List myO2AirConcentrationSensors;
	public List myO2AirPressureSensors;
	public List myO2AirEnvironmentInFlowRateSensors;
	public List myO2AirEnvironmentOutFlowRateSensors;
	public List myO2AirStoreInFlowRateSensors;
	public List myO2AirStoreOutFlowRateSensors;
	public List myNitrogenAirConcentrationSensors;
	public List myNitrogenAirPressureSensors;
	public List myNitrogenAirEnvironmentInFlowRateSensors;
	public List myNitrogenAirEnvironmentOutFlowRateSensors;
	public List myNitrogenAirStoreInFlowRateSensors;
	public List myNitrogenAirStoreOutFlowRateSensors;
	public List myWaterAirConcentrationSensors;
	public List myWaterAirPressureSensors;
	public List myWaterAirEnvironmentInFlowRateSensors;
	public List myWaterAirEnvironmentOutFlowRateSensors;
	public List myWaterAirStoreInFlowRateSensors;
	public List myWaterAirStoreOutFlowRateSensors;
	public List myOtherAirConcentrationSensors;
	public List myOtherAirPressureSensors;
	//Food
	public List myBiomassInFlowRateSensors;
	public List myBiomassOutFlowRateSensors;
	public List myBiomassStoreLevelSensors;
	public List myFoodInFlowRateSensors;
	public List myFoodOutFlowRateSensors;
	public List myFoodStoreLevelSensors;
	public List myHarvestSensors;
	//Framework
	public List myStoreOverflowSensors;
	public List myStoreLevelSensors;
	//Power
	public List myPowerInFlowRateSensors;
	public List myPowerOutFlowRateSensors;
	public List myPowerStoreLevelSensors;
	//Waste
	public List myDryWasteInFlowRateSensors;
	public List myDryWasteOutFlowRateSensors;
	public List myDryWasteStoreLevelSensors;
	//Water
	public List myPotableWaterInFlowRateSensors;
	public List myPotableWaterOutFlowRateSensors;
	public List myPotableWaterStoreLevelSensors;
	public List myGreyWaterInFlowRateSensors;
	public List myGreyWaterOutFlowRateSensors;
	public List myGreyWaterStoreLevelSensors;
	public List myDirtyWaterInFlowRateSensors;
	public List myDirtyWaterOutFlowRateSensors;
	public List myDirtyWaterStoreLevelSensors;
	//Actuators
	//Air
	public List myCO2InFlowRateActuators;
	public List myCO2OutFlowRateActuators;
	public List myO2InFlowRateActuators;
	public List myO2OutFlowRateActuators;
	public List myH2InFlowRateActuators;
	public List myH2OutFlowRateActuators;
	public List myNitrogenInFlowRateActuators;
	public List myNitrogenOutFlowRateActuators;
	//Environment
	public List myAirInFlowRateActuators;
	public List myAirOutFlowRateActuators;
	public List myCO2AirEnvironmentInFlowRateActuators;
	public List myCO2AirEnvironmentOutFlowRateActuators;
	public List myCO2AirStoreInFlowRateActuators;
	public List myCO2AirStoreOutFlowRateActuators;
	public List myO2AirEnvironmentInFlowRateActuators;
	public List myO2AirEnvironmentOutFlowRateActuators;
	public List myO2AirStoreInFlowRateActuators;
	public List myO2AirStoreOutFlowRateActuators;
	public List myNitrogenAirEnvironmentInFlowRateActuators;
	public List myNitrogenAirEnvironmentOutFlowRateActuators;
	public List myNitrogenAirStoreInFlowRateActuators;
	public List myNitrogenAirStoreOutFlowRateActuators;
	public List myWaterAirEnvironmentInFlowRateActuators;
	public List myWaterAirEnvironmentOutFlowRateActuators;
	public List myWaterAirStoreInFlowRateActuators;
	public List myWaterAirStoreOutFlowRateActuators;
	//Food
	public List myBiomassInFlowRateActuators;
	public List myBiomassOutFlowRateActuators;
	public List myFoodInFlowRateActuators;
	public List myFoodOutFlowRateActuators;
	//Power
	public List myPowerInFlowRateActuators;
	public List myPowerOutFlowRateActuators;
	//Waste
	public List myDryWasteInFlowRateActuators;
	public List myDryWasteOutFlowRateActuators;
	//Water
	public List myPotableWaterInFlowRateActuators;
	public List myPotableWaterOutFlowRateActuators;
	public List myGreyWaterInFlowRateActuators;
	public List myGreyWaterOutFlowRateActuators;
	public List myDirtyWaterInFlowRateActuators;
	public List myDirtyWaterOutFlowRateActuators;

	protected BioHolder(){
		//Upper Categories
		myModules = new Vector();
		mySimModules = new Vector();
		mySensors = new Vector();
		myActuators = new Vector();

		//Specific Modules
		//Simulation
		//Air
		myAirRSModules = new Vector();
		myO2Stores = new Vector();
		myCO2Stores = new Vector();
		myNitrogenStores = new Vector();
		myH2Stores = new Vector();
		//Crew
		myCrewGroups = new Vector();
		//Environment
		mySimEnvironments = new Vector();
		//Food
		myFoodProcessors = new Vector();
		myBiomassRSModules = new Vector();
		myBiomassStores = new Vector();
		myFoodStores = new Vector();
		//Framework
		myAccumulators = new Vector();
		myInjectors = new Vector();
		//Power
		myPowerPSModules = new Vector();
		myPowerStores = new Vector();
		//Waste
		myIncinerators = new Vector();
		myDryWasteStores = new Vector();
		//Water
		myWaterRSModules = new Vector();
		myPotableWaterStores = new Vector();
		myGreyWaterStores = new Vector();
		myDirtyWaterStores = new Vector();
		//Sensors
		//Air
		myCO2InFlowRateSensors = new Vector();
		myCO2OutFlowRateSensors = new Vector();
		myCO2StoreLevelSensors = new Vector();
		myO2InFlowRateSensors = new Vector();
		myO2OutFlowRateSensors = new Vector();
		myO2StoreLevelSensors = new Vector();
		myH2InFlowRateSensors = new Vector();
		myH2OutFlowRateSensors = new Vector();
		myH2StoreLevelSensors = new Vector();
		myNitrogenInFlowRateSensors = new Vector();
		myNitrogenOutFlowRateSensors = new Vector();
		myNitrogenStoreLevelSensors = new Vector();
		//Crew
		myCrewGroupDeathSensors = new Vector();
		myCrewGroupProductivitySensors = new Vector();
		myCrewGroupAnyDeadSensors = new Vector();
		//Environment
		myAirInFlowRateSensors = new Vector();
		myAirOutFlowRateSensors = new Vector();
		myCO2AirConcentrationSensors = new Vector();
		myCO2AirPressureSensors = new Vector();
		myCO2AirEnvironmentInFlowRateSensors = new Vector();
		myCO2AirEnvironmentOutFlowRateSensors = new Vector();
		myCO2AirStoreInFlowRateSensors = new Vector();
		myCO2AirStoreOutFlowRateSensors = new Vector();
		myO2AirConcentrationSensors = new Vector();
		myO2AirPressureSensors = new Vector();
		myO2AirEnvironmentInFlowRateSensors = new Vector();
		myO2AirEnvironmentOutFlowRateSensors = new Vector();
		myO2AirStoreInFlowRateSensors = new Vector();
		myO2AirStoreOutFlowRateSensors = new Vector();
		myNitrogenAirConcentrationSensors = new Vector();
		myNitrogenAirPressureSensors = new Vector();
		myNitrogenAirEnvironmentInFlowRateSensors = new Vector();
		myNitrogenAirEnvironmentOutFlowRateSensors = new Vector();
		myNitrogenAirStoreInFlowRateSensors = new Vector();
		myNitrogenAirStoreOutFlowRateSensors = new Vector();
		myWaterAirConcentrationSensors = new Vector();
		myWaterAirPressureSensors = new Vector();
		myWaterAirEnvironmentInFlowRateSensors = new Vector();
		myWaterAirEnvironmentOutFlowRateSensors = new Vector();
		myWaterAirStoreInFlowRateSensors = new Vector();
		myWaterAirStoreOutFlowRateSensors = new Vector();
		myOtherAirConcentrationSensors = new Vector();
		myOtherAirPressureSensors = new Vector();
		//Food
		myBiomassInFlowRateSensors = new Vector();
		myBiomassOutFlowRateSensors = new Vector();
		myBiomassStoreLevelSensors = new Vector();
		myFoodInFlowRateSensors = new Vector();
		myFoodOutFlowRateSensors = new Vector();
		myFoodStoreLevelSensors = new Vector();
		myHarvestSensors = new Vector();
		//Framework
		myStoreOverflowSensors = new Vector();
		myStoreLevelSensors = new Vector();
		//Power
		myPowerInFlowRateSensors = new Vector();
		myPowerOutFlowRateSensors = new Vector();
		myPowerStoreLevelSensors = new Vector();
		//Waste
		myDryWasteInFlowRateSensors = new Vector();
		myDryWasteOutFlowRateSensors = new Vector();
		myDryWasteStoreLevelSensors = new Vector();
		//Water
		myPotableWaterInFlowRateSensors = new Vector();
		myPotableWaterOutFlowRateSensors = new Vector();
		myPotableWaterStoreLevelSensors = new Vector();
		myGreyWaterInFlowRateSensors = new Vector();
		myGreyWaterOutFlowRateSensors = new Vector();
		myGreyWaterStoreLevelSensors = new Vector();
		myDirtyWaterInFlowRateSensors = new Vector();
		myDirtyWaterOutFlowRateSensors = new Vector();
		myDirtyWaterStoreLevelSensors = new Vector();
		//Actuators
		//Air
		myCO2InFlowRateActuators = new Vector();
		myCO2OutFlowRateActuators = new Vector();
		myO2InFlowRateActuators = new Vector();
		myO2OutFlowRateActuators = new Vector();
		myH2InFlowRateActuators = new Vector();
		myH2OutFlowRateActuators = new Vector();
		myNitrogenInFlowRateActuators = new Vector();
		myNitrogenOutFlowRateActuators = new Vector();
		//Environment
		myAirInFlowRateActuators = new Vector();
		myAirOutFlowRateActuators = new Vector();
		myCO2AirEnvironmentInFlowRateActuators = new Vector();
		myCO2AirEnvironmentOutFlowRateActuators = new Vector();
		myCO2AirStoreInFlowRateActuators = new Vector();
		myCO2AirStoreOutFlowRateActuators = new Vector();
		myO2AirEnvironmentInFlowRateActuators = new Vector();
		myO2AirEnvironmentOutFlowRateActuators = new Vector();
		myO2AirStoreInFlowRateActuators = new Vector();
		myO2AirStoreOutFlowRateActuators = new Vector();
		myNitrogenAirEnvironmentInFlowRateActuators = new Vector();
		myNitrogenAirEnvironmentOutFlowRateActuators = new Vector();
		myNitrogenAirStoreInFlowRateActuators = new Vector();
		myNitrogenAirStoreOutFlowRateActuators = new Vector();
		myWaterAirEnvironmentInFlowRateActuators = new Vector();
		myWaterAirEnvironmentOutFlowRateActuators = new Vector();
		myWaterAirStoreInFlowRateActuators = new Vector();
		myWaterAirStoreOutFlowRateActuators = new Vector();
		//Food
		myBiomassInFlowRateActuators = new Vector();
		myBiomassOutFlowRateActuators = new Vector();
		myFoodInFlowRateActuators = new Vector();
		myFoodOutFlowRateActuators = new Vector();
		//Power
		myPowerInFlowRateActuators = new Vector();
		myPowerOutFlowRateActuators = new Vector();
		//Waste
		myDryWasteInFlowRateActuators = new Vector();
		myDryWasteOutFlowRateActuators = new Vector();
		//Water
		myPotableWaterInFlowRateActuators = new Vector();
		myPotableWaterOutFlowRateActuators = new Vector();
		myGreyWaterInFlowRateActuators = new Vector();
		myGreyWaterOutFlowRateActuators = new Vector();
		myDirtyWaterInFlowRateActuators = new Vector();
		myDirtyWaterOutFlowRateActuators = new Vector();
	}

	protected void coallateLists(){
		//Specific Modules
		//Simulation
		//Air
		mySimModules.addAll(myAirRSModules);
		mySimModules.addAll(myO2Stores);
		mySimModules.addAll(myCO2Stores);
		mySimModules.addAll(myNitrogenStores);
		mySimModules.addAll(myH2Stores);
		//Crew
		mySimModules.addAll(myCrewGroups);
		//Environment
		mySimModules.addAll(mySimEnvironments);
		//Food
		mySimModules.addAll(myFoodProcessors);
		mySimModules.addAll(myBiomassRSModules);
		mySimModules.addAll(myBiomassStores);
		mySimModules.addAll(myFoodStores);
		//Framework
		mySimModules.addAll(myAccumulators);
		mySimModules.addAll(myInjectors);
		//Power
		mySimModules.addAll(myPowerPSModules);
		mySimModules.addAll(myPowerStores);
		//Waste
		mySimModules.addAll(myIncinerators);
		mySimModules.addAll(myDryWasteStores);
		//Water
		mySimModules.addAll(myWaterRSModules);
		mySimModules.addAll(myPotableWaterStores);
		mySimModules.addAll(myGreyWaterStores);
		mySimModules.addAll(myDirtyWaterStores);
		//Sensors
		//Air
		mySensors.addAll(myCO2InFlowRateSensors);
		mySensors.addAll(myCO2OutFlowRateSensors);
		mySensors.addAll(myCO2StoreLevelSensors);
		mySensors.addAll(myO2InFlowRateSensors);
		mySensors.addAll(myO2OutFlowRateSensors);
		mySensors.addAll(myO2StoreLevelSensors);
		mySensors.addAll(myH2InFlowRateSensors);
		mySensors.addAll(myH2OutFlowRateSensors);
		mySensors.addAll(myH2StoreLevelSensors);
		mySensors.addAll(myNitrogenInFlowRateSensors);
		mySensors.addAll(myNitrogenOutFlowRateSensors);
		mySensors.addAll(myNitrogenStoreLevelSensors);
		//Crew
		mySensors.addAll(myCrewGroupDeathSensors);
		mySensors.addAll(myCrewGroupProductivitySensors);
		mySensors.addAll(myCrewGroupAnyDeadSensors);
		//Environment
		mySensors.addAll(myAirInFlowRateSensors);
		mySensors.addAll(myAirOutFlowRateSensors);
		mySensors.addAll(myCO2AirConcentrationSensors);
		mySensors.addAll(myCO2AirPressureSensors);
		mySensors.addAll(myCO2AirEnvironmentInFlowRateSensors);
		mySensors.addAll(myCO2AirEnvironmentOutFlowRateSensors);
		mySensors.addAll(myCO2AirStoreInFlowRateSensors);
		mySensors.addAll(myCO2AirStoreOutFlowRateSensors);
		mySensors.addAll(myO2AirConcentrationSensors);
		mySensors.addAll(myO2AirPressureSensors);
		mySensors.addAll(myO2AirEnvironmentInFlowRateSensors);
		mySensors.addAll(myO2AirEnvironmentOutFlowRateSensors);
		mySensors.addAll(myO2AirStoreInFlowRateSensors);
		mySensors.addAll(myO2AirStoreOutFlowRateSensors);
		mySensors.addAll(myNitrogenAirConcentrationSensors);
		mySensors.addAll(myNitrogenAirPressureSensors);
		mySensors.addAll(myNitrogenAirEnvironmentInFlowRateSensors);
		mySensors.addAll(myNitrogenAirEnvironmentOutFlowRateSensors);
		mySensors.addAll(myNitrogenAirStoreInFlowRateSensors);
		mySensors.addAll(myNitrogenAirStoreOutFlowRateSensors);
		mySensors.addAll(myWaterAirConcentrationSensors);
		mySensors.addAll(myWaterAirPressureSensors);
		mySensors.addAll(myWaterAirEnvironmentInFlowRateSensors);
		mySensors.addAll(myWaterAirEnvironmentOutFlowRateSensors);
		mySensors.addAll(myWaterAirStoreInFlowRateSensors);
		mySensors.addAll(myWaterAirStoreOutFlowRateSensors);
		mySensors.addAll(myOtherAirConcentrationSensors);
		mySensors.addAll(myOtherAirPressureSensors);
		//Food
		mySensors.addAll(myBiomassInFlowRateSensors);
		mySensors.addAll(myBiomassOutFlowRateSensors);
		mySensors.addAll(myBiomassStoreLevelSensors);
		mySensors.addAll(myFoodInFlowRateSensors);
		mySensors.addAll(myFoodOutFlowRateSensors);
		mySensors.addAll(myFoodStoreLevelSensors);
		mySensors.addAll(myHarvestSensors);
		//Framework
		mySensors.addAll(myStoreOverflowSensors);
		mySensors.addAll(myStoreLevelSensors);
		//Power
		mySensors.addAll(myPowerInFlowRateSensors);
		mySensors.addAll(myPowerOutFlowRateSensors);
		mySensors.addAll(myPowerStoreLevelSensors);
		//Waste
		mySensors.addAll(myDryWasteInFlowRateSensors);
		mySensors.addAll(myDryWasteOutFlowRateSensors);
		mySensors.addAll(myDryWasteStoreLevelSensors);
		//Water
		mySensors.addAll(myPotableWaterInFlowRateSensors);
		mySensors.addAll(myPotableWaterOutFlowRateSensors);
		mySensors.addAll(myPotableWaterStoreLevelSensors);
		mySensors.addAll(myGreyWaterInFlowRateSensors);
		mySensors.addAll(myGreyWaterOutFlowRateSensors);
		mySensors.addAll(myGreyWaterStoreLevelSensors);
		mySensors.addAll(myDirtyWaterInFlowRateSensors);
		mySensors.addAll(myDirtyWaterOutFlowRateSensors);
		mySensors.addAll(myDirtyWaterStoreLevelSensors);
		//Actuators
		//Air
		myActuators.addAll(myCO2InFlowRateActuators);
		myActuators.addAll(myCO2OutFlowRateActuators);
		myActuators.addAll(myO2InFlowRateActuators);
		myActuators.addAll(myO2OutFlowRateActuators);
		myActuators.addAll(myH2InFlowRateActuators);
		myActuators.addAll(myH2OutFlowRateActuators);
		myActuators.addAll(myNitrogenInFlowRateActuators);
		myActuators.addAll(myNitrogenOutFlowRateActuators);
		//Environment
		myActuators.addAll(myAirInFlowRateActuators);
		myActuators.addAll(myAirOutFlowRateActuators);
		myActuators.addAll(myCO2AirEnvironmentInFlowRateActuators);
		myActuators.addAll(myCO2AirEnvironmentOutFlowRateActuators);
		myActuators.addAll(myCO2AirStoreInFlowRateActuators);
		myActuators.addAll(myCO2AirStoreOutFlowRateActuators);
		myActuators.addAll(myO2AirEnvironmentInFlowRateActuators);
		myActuators.addAll(myO2AirEnvironmentOutFlowRateActuators);
		myActuators.addAll(myO2AirStoreInFlowRateActuators);
		myActuators.addAll(myO2AirStoreOutFlowRateActuators);
		myActuators.addAll(myNitrogenAirEnvironmentInFlowRateActuators);
		myActuators.addAll(myNitrogenAirEnvironmentOutFlowRateActuators);
		myActuators.addAll(myNitrogenAirStoreInFlowRateActuators);
		myActuators.addAll(myNitrogenAirStoreOutFlowRateActuators);
		myActuators.addAll(myWaterAirEnvironmentInFlowRateActuators);
		myActuators.addAll(myWaterAirEnvironmentOutFlowRateActuators);
		myActuators.addAll(myWaterAirStoreInFlowRateActuators);
		myActuators.addAll(myWaterAirStoreOutFlowRateActuators);
		//Food
		myActuators.addAll(myBiomassInFlowRateActuators);
		myActuators.addAll(myBiomassOutFlowRateActuators);
		myActuators.addAll(myFoodInFlowRateActuators);
		myActuators.addAll(myFoodOutFlowRateActuators);
		//Power
		myActuators.addAll(myPowerInFlowRateActuators);
		myActuators.addAll(myPowerOutFlowRateActuators);
		//Waste
		myActuators.addAll(myDryWasteInFlowRateActuators);
		myActuators.addAll(myDryWasteOutFlowRateActuators);
		//Water
		myActuators.addAll(myPotableWaterInFlowRateActuators);
		myActuators.addAll(myPotableWaterOutFlowRateActuators);
		myActuators.addAll(myGreyWaterInFlowRateActuators);
		myActuators.addAll(myGreyWaterOutFlowRateActuators);
		myActuators.addAll(myDirtyWaterInFlowRateActuators);
		myActuators.addAll(myDirtyWaterOutFlowRateActuators);

		myModules.addAll(mySimModules);
		myModules.addAll(mySensors);
		myModules.addAll(myActuators);
	}

	protected void reset(){
		//Upper Categories
		myModules.clear();
		mySimModules.clear();
		mySensors.clear();
		myActuators.clear();

		//Specific Modules
		//Simulation
		//Air
		myAirRSModules.clear();
		myO2Stores.clear();
		myCO2Stores.clear();
		myNitrogenStores.clear();
		myH2Stores.clear();
		//Crew
		myCrewGroups.clear();
		//Environment
		mySimEnvironments.clear();
		//Food
		myFoodProcessors.clear();
		myBiomassRSModules.clear();
		myBiomassStores.clear();
		myFoodStores.clear();
		//Framework
		myAccumulators.clear();
		myInjectors.clear();
		//Power
		myPowerPSModules.clear();
		myPowerStores.clear();
		//Waste
		myIncinerators.clear();
		myDryWasteStores.clear();
		//Water
		myWaterRSModules.clear();
		myPotableWaterStores.clear();
		myGreyWaterStores.clear();
		myDirtyWaterStores.clear();
		//Sensors
		//Air
		myCO2InFlowRateSensors.clear();
		myCO2OutFlowRateSensors.clear();
		myCO2StoreLevelSensors.clear();
		myO2InFlowRateSensors.clear();
		myO2OutFlowRateSensors.clear();
		myO2StoreLevelSensors.clear();
		myH2InFlowRateSensors.clear();
		myH2OutFlowRateSensors.clear();
		myH2StoreLevelSensors.clear();
		myNitrogenInFlowRateSensors.clear();
		myNitrogenOutFlowRateSensors.clear();
		myNitrogenStoreLevelSensors.clear();
		//Crew
		myCrewGroupDeathSensors.clear();
		myCrewGroupProductivitySensors.clear();
		myCrewGroupAnyDeadSensors.clear();
		//Environment
		myAirInFlowRateSensors.clear();
		myAirOutFlowRateSensors.clear();
		myCO2AirConcentrationSensors.clear();
		myCO2AirPressureSensors.clear();
		myCO2AirEnvironmentInFlowRateSensors.clear();
		myCO2AirEnvironmentOutFlowRateSensors.clear();
		myCO2AirStoreInFlowRateSensors.clear();
		myCO2AirStoreOutFlowRateSensors.clear();
		myO2AirConcentrationSensors.clear();
		myO2AirPressureSensors.clear();
		myO2AirEnvironmentInFlowRateSensors.clear();
		myO2AirEnvironmentOutFlowRateSensors.clear();
		myO2AirStoreInFlowRateSensors.clear();
		myO2AirStoreOutFlowRateSensors.clear();
		myNitrogenAirConcentrationSensors.clear();
		myNitrogenAirPressureSensors.clear();
		myNitrogenAirEnvironmentInFlowRateSensors.clear();
		myNitrogenAirEnvironmentOutFlowRateSensors.clear();
		myNitrogenAirStoreInFlowRateSensors.clear();
		myNitrogenAirStoreOutFlowRateSensors.clear();
		myWaterAirConcentrationSensors.clear();
		myWaterAirPressureSensors.clear();
		myWaterAirEnvironmentInFlowRateSensors.clear();
		myWaterAirEnvironmentOutFlowRateSensors.clear();
		myWaterAirStoreInFlowRateSensors.clear();
		myWaterAirStoreOutFlowRateSensors.clear();
		myOtherAirConcentrationSensors.clear();
		myOtherAirPressureSensors.clear();
		//Food
		myBiomassInFlowRateSensors.clear();
		myBiomassOutFlowRateSensors.clear();
		myBiomassStoreLevelSensors.clear();
		myFoodInFlowRateSensors.clear();
		myFoodOutFlowRateSensors.clear();
		myFoodStoreLevelSensors.clear();
		myHarvestSensors.clear();
		//Framework
		myStoreOverflowSensors.clear();
		myStoreLevelSensors.clear();
		//Power
		myPowerInFlowRateSensors.clear();
		myPowerOutFlowRateSensors.clear();
		myPowerStoreLevelSensors.clear();
		//Waste
		myDryWasteInFlowRateSensors.clear();
		myDryWasteOutFlowRateSensors.clear();
		myDryWasteStoreLevelSensors.clear();
		//Water
		myPotableWaterInFlowRateSensors.clear();
		myPotableWaterOutFlowRateSensors.clear();
		myPotableWaterStoreLevelSensors.clear();
		myGreyWaterInFlowRateSensors.clear();
		myGreyWaterOutFlowRateSensors.clear();
		myGreyWaterStoreLevelSensors.clear();
		myDirtyWaterInFlowRateSensors.clear();
		myDirtyWaterOutFlowRateSensors.clear();
		myDirtyWaterStoreLevelSensors.clear();
		//Actuators
		//Air
		myCO2InFlowRateActuators.clear();
		myCO2OutFlowRateActuators.clear();
		myO2InFlowRateActuators.clear();
		myO2OutFlowRateActuators.clear();
		myH2InFlowRateActuators.clear();
		myH2OutFlowRateActuators.clear();
		myNitrogenInFlowRateActuators.clear();
		myNitrogenOutFlowRateActuators.clear();
		//Environment
		myAirInFlowRateActuators.clear();
		myAirOutFlowRateActuators.clear();
		myCO2AirEnvironmentInFlowRateActuators.clear();
		myCO2AirEnvironmentOutFlowRateActuators.clear();
		myCO2AirStoreInFlowRateActuators.clear();
		myCO2AirStoreOutFlowRateActuators.clear();
		myO2AirEnvironmentInFlowRateActuators.clear();
		myO2AirEnvironmentOutFlowRateActuators.clear();
		myO2AirStoreInFlowRateActuators.clear();
		myO2AirStoreOutFlowRateActuators.clear();
		myNitrogenAirEnvironmentInFlowRateActuators.clear();
		myNitrogenAirEnvironmentOutFlowRateActuators.clear();
		myNitrogenAirStoreInFlowRateActuators.clear();
		myNitrogenAirStoreOutFlowRateActuators.clear();
		myWaterAirEnvironmentInFlowRateActuators.clear();
		myWaterAirEnvironmentOutFlowRateActuators.clear();
		myWaterAirStoreInFlowRateActuators.clear();
		myWaterAirStoreOutFlowRateActuators.clear();
		//Food
		myBiomassInFlowRateActuators.clear();
		myBiomassOutFlowRateActuators.clear();
		myFoodInFlowRateActuators.clear();
		myFoodOutFlowRateActuators.clear();
		//Power
		myPowerInFlowRateActuators.clear();
		myPowerOutFlowRateActuators.clear();
		//Waste
		myDryWasteInFlowRateActuators.clear();
		myDryWasteOutFlowRateActuators.clear();
		//Water
		myPotableWaterInFlowRateActuators.clear();
		myPotableWaterOutFlowRateActuators.clear();
		myGreyWaterInFlowRateActuators.clear();
		myGreyWaterOutFlowRateActuators.clear();
		myDirtyWaterInFlowRateActuators.clear();
		myDirtyWaterOutFlowRateActuators.clear();
	}
}
