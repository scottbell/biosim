package com.traclabs.biosim.client.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.actuator.food.ShelfActuator;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.ShelfSensor;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.food.BiomassRS;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.idl.simulation.food.Shelf;

/**
 * Holds references to the servers
 * 
 * @author Scott Bell
 */
public class BioHolder {
    public BioDriver theBioDriver;

    public Map theModulesMapped = new Hashtable();

    //Upper Categories
    public List theModules = new Vector();

    public List theSimModules = new Vector();

    public List theSensors = new Vector();

    public List theActuators = new Vector();

    //Specific Modules
    //Simulation
    //Air
    public List theAirRSModules = new Vector();
    
    public List theOGSModules = new Vector();
    
    public List theVCCRModules = new Vector();

    public List theO2Stores = new Vector();

    public List theCO2Stores = new Vector();

    public List theH2Stores = new Vector();

    public List theNitrogenStores = new Vector();

    //Crew
    public List theCrewGroups = new Vector();

    //Environment
    public List theSimEnvironments = new Vector();

    public List theDehumidifiers = new Vector();

    //Food
    public List theFoodProcessors = new Vector();

    public List theBiomassRSModules = new Vector();

    public List theBiomassStores = new Vector();

    public List theFoodStores = new Vector();

    //Framework
    public List theAccumulators = new Vector();

    public List theInjectors = new Vector();

    //Power
    public List thePowerPSModules = new Vector();

    public List thePowerStores = new Vector();

    //Waste
    public List theIncinerators = new Vector();

    public List theDryWasteStores = new Vector();

    //Water
    public List theWaterRSModules = new Vector();

    public List thePotableWaterStores = new Vector();

    public List theGreyWaterStores = new Vector();

    public List theDirtyWaterStores = new Vector();

    //Sensors
    //Air
    public List theCO2InFlowRateSensors = new Vector();

    public List theCO2OutFlowRateSensors = new Vector();

    public List theCO2StoreLevelSensors = new Vector();

    public List theO2InFlowRateSensors = new Vector();

    public List theO2OutFlowRateSensors = new Vector();

    public List theO2StoreLevelSensors = new Vector();

    public List theH2InFlowRateSensors = new Vector();

    public List theH2OutFlowRateSensors = new Vector();

    public List theH2StoreLevelSensors = new Vector();

    public List theNitrogenInFlowRateSensors = new Vector();

    public List theNitrogenOutFlowRateSensors = new Vector();

    public List theNitrogenStoreLevelSensors = new Vector();

    //Crew
    public List theCrewGroupDeathSensors = new Vector();

    public List theCrewGroupProductivitySensors = new Vector();

    public List theCrewGroupAnyDeadSensors = new Vector();

    //Environment
    public List theAirInFlowRateSensors = new Vector();

    public List theAirOutFlowRateSensors = new Vector();

    public List theCO2AirConcentrationSensors = new Vector();

    public List theCO2AirPressureSensors = new Vector();

    public List theCO2AirEnvironmentInFlowRateSensors = new Vector();

    public List theCO2AirEnvironmentOutFlowRateSensors = new Vector();

    public List theCO2AirStoreInFlowRateSensors = new Vector();

    public List theCO2AirStoreOutFlowRateSensors = new Vector();

    public List theO2AirConcentrationSensors = new Vector();

    public List theO2AirPressureSensors = new Vector();

    public List theO2AirEnvironmentInFlowRateSensors = new Vector();

    public List theO2AirEnvironmentOutFlowRateSensors = new Vector();

    public List theO2AirStoreInFlowRateSensors = new Vector();

    public List theO2AirStoreOutFlowRateSensors = new Vector();

    public List theNitrogenAirConcentrationSensors = new Vector();

    public List theNitrogenAirPressureSensors = new Vector();

    public List theNitrogenAirEnvironmentInFlowRateSensors = new Vector();

    public List theNitrogenAirEnvironmentOutFlowRateSensors = new Vector();

    public List theNitrogenAirStoreInFlowRateSensors = new Vector();

    public List theNitrogenAirStoreOutFlowRateSensors = new Vector();

    public List theWaterAirConcentrationSensors = new Vector();

    public List theWaterAirPressureSensors = new Vector();

    public List theWaterAirEnvironmentInFlowRateSensors = new Vector();

    public List theWaterAirEnvironmentOutFlowRateSensors = new Vector();

    public List theWaterAirStoreInFlowRateSensors = new Vector();

    public List theWaterAirStoreOutFlowRateSensors = new Vector();

    public List theOtherAirConcentrationSensors = new Vector();

    public List theOtherAirPressureSensors = new Vector();

    //Food
    public List theBiomassInFlowRateSensors = new Vector();

    public List theBiomassOutFlowRateSensors = new Vector();

    public List theBiomassStoreLevelSensors = new Vector();

    public List theFoodInFlowRateSensors = new Vector();

    public List theFoodOutFlowRateSensors = new Vector();

    public List theFoodStoreLevelSensors = new Vector();

    public List theHarvestSensors = new Vector();

    public List thePlantDeathSensors = new Vector();

    public List theBiomassStoreWaterContentSensors = new Vector();

    //Framework
    public List theStoreOverflowSensors = new Vector();

    public List theStoreLevelSensors = new Vector();

    //Power
    public List thePowerInFlowRateSensors = new Vector();

    public List thePowerOutFlowRateSensors = new Vector();

    public List thePowerStoreLevelSensors = new Vector();

    //Waste
    public List theDryWasteInFlowRateSensors = new Vector();

    public List theDryWasteOutFlowRateSensors = new Vector();

    public List theDryWasteStoreLevelSensors = new Vector();

    //Water
    public List thePotableWaterInFlowRateSensors = new Vector();

    public List thePotableWaterOutFlowRateSensors = new Vector();

    public List thePotableWaterStoreLevelSensors = new Vector();

    public List theGreyWaterInFlowRateSensors = new Vector();

    public List theGreyWaterOutFlowRateSensors = new Vector();

    public List theGreyWaterStoreLevelSensors = new Vector();

    public List theDirtyWaterInFlowRateSensors = new Vector();

    public List theDirtyWaterOutFlowRateSensors = new Vector();

    public List theDirtyWaterStoreLevelSensors = new Vector();

    public List theWaterInFlowRateSensors = new Vector();

    public List theWaterOutFlowRateSensors = new Vector();

    public List theWaterStoreLevelSensors = new Vector();

    //Actuators
    //Air
    public List theCO2InFlowRateActuators = new Vector();

    public List theCO2OutFlowRateActuators = new Vector();

    public List theO2InFlowRateActuators = new Vector();

    public List theO2OutFlowRateActuators = new Vector();

    public List theH2InFlowRateActuators = new Vector();

    public List theH2OutFlowRateActuators = new Vector();

    public List theNitrogenInFlowRateActuators = new Vector();

    public List theNitrogenOutFlowRateActuators = new Vector();

    //Environment
    public List theAirInFlowRateActuators = new Vector();

    public List theAirOutFlowRateActuators = new Vector();

    public List theCO2AirEnvironmentInFlowRateActuators = new Vector();

    public List theCO2AirEnvironmentOutFlowRateActuators = new Vector();

    public List theCO2AirStoreInFlowRateActuators = new Vector();

    public List theCO2AirStoreOutFlowRateActuators = new Vector();

    public List theO2AirEnvironmentInFlowRateActuators = new Vector();

    public List theO2AirEnvironmentOutFlowRateActuators = new Vector();

    public List theO2AirStoreInFlowRateActuators = new Vector();

    public List theO2AirStoreOutFlowRateActuators = new Vector();

    public List theNitrogenAirEnvironmentInFlowRateActuators = new Vector();

    public List theNitrogenAirEnvironmentOutFlowRateActuators = new Vector();

    public List theNitrogenAirStoreInFlowRateActuators = new Vector();

    public List theNitrogenAirStoreOutFlowRateActuators = new Vector();

    public List theWaterAirEnvironmentInFlowRateActuators = new Vector();

    public List theWaterAirEnvironmentOutFlowRateActuators = new Vector();

    public List theWaterAirStoreInFlowRateActuators = new Vector();

    public List theWaterAirStoreOutFlowRateActuators = new Vector();

    //Food
    public List theBiomassInFlowRateActuators = new Vector();

    public List theBiomassOutFlowRateActuators = new Vector();

    public List theFoodInFlowRateActuators = new Vector();

    public List theFoodOutFlowRateActuators = new Vector();

    public List thePlantingActuators = new Vector();

    public List theHarvestingActuators = new Vector();

    //Power
    public List thePowerInFlowRateActuators = new Vector();

    public List thePowerOutFlowRateActuators = new Vector();

    //Waste
    public List theDryWasteInFlowRateActuators = new Vector();

    public List theDryWasteOutFlowRateActuators = new Vector();

    //Water
    public List thePotableWaterInFlowRateActuators = new Vector();

    public List thePotableWaterOutFlowRateActuators = new Vector();

    public List theGreyWaterInFlowRateActuators = new Vector();

    public List theGreyWaterOutFlowRateActuators = new Vector();

    public List theDirtyWaterInFlowRateActuators = new Vector();

    public List theDirtyWaterOutFlowRateActuators = new Vector();

    public List theWaterInFlowRateActuators = new Vector();

    public List theWaterOutFlowRateActuators = new Vector();

    protected BioHolder() {
    }

    public GenericSensor getShelfSensorAttachedTo(List sensorList,
            BioModule moduleWatched, int shelfIndex) {
        for (Iterator iter = sensorList.iterator(); iter.hasNext();) {
            ShelfSensor currentSensor = (ShelfSensor) (iter.next());
            if (currentSensor.getInputModule()._is_equivalent(moduleWatched)) {
                BiomassRS currentBiomassRS = BiomassRSHelper
                        .narrow(currentSensor.getInputModule());
                Shelf[] shelfArray = currentBiomassRS.getShelves();
                if (currentSensor.getInput()._is_equivalent(
                        shelfArray[shelfIndex]))
                    return currentSensor;
            }
        }
        return null;
    }

    public GenericActuator getShelfActuatorAttachedTo(List sensorList,
            BioModule moduleWatched, int shelfIndex) {
        for (Iterator iter = sensorList.iterator(); iter.hasNext();) {
            ShelfActuator currentActuator = (ShelfActuator) (iter.next());
            if (currentActuator.getOutputModule()._is_equivalent(moduleWatched)) {
                BiomassRS currentBiomassRS = BiomassRSHelper
                        .narrow(currentActuator.getOutputModule());
                Shelf[] shelfArray = currentBiomassRS.getShelves();
                if (currentActuator.getOutput()._is_equivalent(
                        shelfArray[shelfIndex]))
                    return currentActuator;
            }
        }
        return null;
    }

    public GenericSensor getSensorAttachedTo(List sensorList,
            BioModule moduleWatched) {
        for (Iterator iter = sensorList.iterator(); iter.hasNext();) {
            GenericSensor currentSensor = (GenericSensor) (iter.next());
            if (currentSensor.getInputModule()._is_equivalent(moduleWatched))
                return currentSensor;
        }
        return null;
    }

    public GenericActuator getActuatorAttachedTo(List actuatorList,
            BioModule moduleWatched) {
        for (Iterator iter = actuatorList.iterator(); iter.hasNext();) {
            GenericActuator currentActuator = (GenericActuator) (iter.next());
            if (currentActuator.getOutputModule()._is_equivalent(moduleWatched))
                return currentActuator;
        }
        return null;
    }

    protected void coallateLists() {
        Logger.getLogger(BioHolder.class.toString()).debug("coallating lists");
        //Specific Modules
        //Simulation
        //Air
        theSimModules.addAll(theAirRSModules);
        theSimModules.addAll(theOGSModules);
        theSimModules.addAll(theVCCRModules);
        theSimModules.addAll(theO2Stores);
        theSimModules.addAll(theCO2Stores);
        theSimModules.addAll(theNitrogenStores);
        theSimModules.addAll(theH2Stores);
        //Crew
        theSimModules.addAll(theCrewGroups);
        //Environment
        theSimModules.addAll(theSimEnvironments);
        theSimModules.addAll(theDehumidifiers);
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
        theSensors.addAll(thePlantDeathSensors);
        theSensors.addAll(theBiomassStoreWaterContentSensors);
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
        theSensors.addAll(theWaterInFlowRateSensors);
        theSensors.addAll(theWaterOutFlowRateSensors);
        theSensors.addAll(theWaterStoreLevelSensors);
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
        theActuators.addAll(theWaterInFlowRateActuators);
        theActuators.addAll(theWaterOutFlowRateActuators);

        theModules.addAll(theSimModules);
        theModules.addAll(theSensors);
        theModules.addAll(theActuators);
        
        Logger.getLogger(BioHolder.class.toString()).debug("theModules.size() = "+theModules.size());
        //Make named list;
        for (Iterator iter = theModules.iterator(); iter.hasNext();) {
            BioModule currentModule = (BioModule) (iter.next());
            theModulesMapped.put(currentModule.getModuleName(), currentModule);
            Logger.getLogger(BioHolder.class.toString()).debug("mapped "+currentModule.getModuleName());
        }
    }

    protected void reset() {
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
        theOGSModules.clear();
        theVCCRModules.clear();
        theO2Stores.clear();
        theCO2Stores.clear();
        theNitrogenStores.clear();
        theH2Stores.clear();
        //Crew
        theCrewGroups.clear();
        //Environment
        theSimEnvironments.clear();
        theDehumidifiers.clear();
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
        thePlantDeathSensors.clear();
        theBiomassStoreWaterContentSensors.clear();
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
        theWaterInFlowRateSensors.clear();
        theWaterOutFlowRateSensors.clear();
        theWaterStoreLevelSensors.clear();
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
        theWaterInFlowRateActuators.clear();
        theWaterOutFlowRateActuators.clear();
    }
}