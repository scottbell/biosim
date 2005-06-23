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
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensor;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.OtherAirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.OtherAirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensor;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.food.HarvestSensor;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensor;
import com.traclabs.biosim.idl.sensor.food.ShelfSensor;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensor;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterStoreLevelSensor;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterStoreLevelSensor;
import com.traclabs.biosim.idl.simulation.air.AirRS;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.CRS;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.air.OGS;
import com.traclabs.biosim.idl.simulation.air.VCCR;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.environment.Dehumidifier;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.BiomassRS;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.FoodProcessor;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.food.Shelf;
import com.traclabs.biosim.idl.simulation.framework.Accumulator;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.idl.simulation.framework.SimBioModule;
import com.traclabs.biosim.idl.simulation.power.PowerPS;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.idl.simulation.waste.Incinerator;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterRS;

/**
 * Holds references to the servers
 * 
 * @author Scott Bell
 */
public class BioHolder {
    public BioDriver theBioDriver;

    public Map theModulesMapped = new Hashtable();

    //Upper Categories
    public List<BioModule> theModules = new Vector<BioModule>();

    public List<SimBioModule> theSimModules = new Vector<SimBioModule>();

    public List<GenericSensor> theSensors = new Vector<GenericSensor>();

    public List<GenericActuator> theActuators = new Vector<GenericActuator>();

    //Specific Modules
    //Simulation
    //Air
    public List<AirRS> theAirRSModules = new Vector<AirRS>();

    public List<OGS> theOGSModules = new Vector<OGS>();

    public List<VCCR> theVCCRModules = new Vector<VCCR>();

    public List<CRS> theCRSModules = new Vector<CRS>();

    public List<O2Store> theO2Stores = new Vector<O2Store>();

    public List<CO2Store> theCO2Stores = new Vector<CO2Store>();

    public List<H2Store> theH2Stores = new Vector<H2Store>();

    public List<NitrogenStore> theNitrogenStores = new Vector<NitrogenStore>();

    //Crew
    public List<CrewGroup> theCrewGroups = new Vector<CrewGroup>();

    //Environment
    public List<SimEnvironment> theSimEnvironments = new Vector<SimEnvironment>();

    public List<Dehumidifier> theDehumidifiers = new Vector<Dehumidifier>();

    //Food
    public List<FoodProcessor> theFoodProcessors = new Vector<FoodProcessor>();

    public List<BiomassRS> theBiomassRSModules = new Vector<BiomassRS>();

    public List<BiomassStore> theBiomassStores = new Vector<BiomassStore>();

    public List<FoodStore> theFoodStores = new Vector<FoodStore>();

    //Framework
    public List<Accumulator> theAccumulators = new Vector<Accumulator>();

    public List<Injector> theInjectors = new Vector<Injector>();

    //Power
    public List<PowerPS> thePowerPSModules = new Vector<PowerPS>();

    public List<PowerStore> thePowerStores = new Vector<PowerStore>();

    //Waste
    public List<Incinerator> theIncinerators = new Vector<Incinerator>();

    public List<DryWasteStore> theDryWasteStores = new Vector<DryWasteStore>();

    //Water
    public List<WaterRS> theWaterRSModules = new Vector<WaterRS>();

    public List<PotableWaterStore> thePotableWaterStores = new Vector<PotableWaterStore>();

    public List<GreyWaterStore> theGreyWaterStores = new Vector<GreyWaterStore>();

    public List<DirtyWaterStore> theDirtyWaterStores = new Vector<DirtyWaterStore>();

    //Sensors
    //Air
    public List<CO2InFlowRateSensor> theCO2InFlowRateSensors = new Vector<CO2InFlowRateSensor>();

    public List<CO2OutFlowRateSensor> theCO2OutFlowRateSensors = new Vector<CO2OutFlowRateSensor>();

    public List<CO2StoreLevelSensor> theCO2StoreLevelSensors = new Vector<CO2StoreLevelSensor>();

    public List<O2InFlowRateSensor> theO2InFlowRateSensors = new Vector<O2InFlowRateSensor>();

    public List<O2OutFlowRateSensor> theO2OutFlowRateSensors = new Vector<O2OutFlowRateSensor>();

    public List<O2StoreLevelSensor> theO2StoreLevelSensors = new Vector<O2StoreLevelSensor>();

    public List<H2InFlowRateSensor> theH2InFlowRateSensors = new Vector<H2InFlowRateSensor>();

    public List<H2OutFlowRateSensor> theH2OutFlowRateSensors = new Vector<H2OutFlowRateSensor>();

    public List<H2StoreLevelSensor> theH2StoreLevelSensors = new Vector<H2StoreLevelSensor>();

    public List<NitrogenInFlowRateSensor> theNitrogenInFlowRateSensors = new Vector<NitrogenInFlowRateSensor>();

    public List<NitrogenOutFlowRateSensor> theNitrogenOutFlowRateSensors = new Vector<NitrogenOutFlowRateSensor>();

    public List<NitrogenStoreLevelSensor> theNitrogenStoreLevelSensors = new Vector<NitrogenStoreLevelSensor>();

    //Crew
    public List<CrewGroupDeathSensor> theCrewGroupDeathSensors = new Vector<CrewGroupDeathSensor>();

    public List<CrewGroupProductivitySensor> theCrewGroupProductivitySensors = new Vector<CrewGroupProductivitySensor>();

    public List<CrewGroupAnyDeadSensor> theCrewGroupAnyDeadSensors = new Vector<CrewGroupAnyDeadSensor>();

    //Environment
    public List<AirInFlowRateSensor> theAirInFlowRateSensors = new Vector<AirInFlowRateSensor>();

    public List<AirOutFlowRateSensor> theAirOutFlowRateSensors = new Vector<AirOutFlowRateSensor>();

    public List<CO2AirConcentrationSensor> theCO2AirConcentrationSensors = new Vector<CO2AirConcentrationSensor>();

    public List<CO2AirPressureSensor> theCO2AirPressureSensors = new Vector<CO2AirPressureSensor>();

    public List<CO2AirEnvironmentInFlowRateSensor> theCO2AirEnvironmentInFlowRateSensors = new Vector<CO2AirEnvironmentInFlowRateSensor>();

    public List<CO2AirEnvironmentOutFlowRateSensor> theCO2AirEnvironmentOutFlowRateSensors = new Vector<CO2AirEnvironmentOutFlowRateSensor>();

    public List<CO2AirStoreInFlowRateSensor> theCO2AirStoreInFlowRateSensors = new Vector<CO2AirStoreInFlowRateSensor>();

    public List<CO2AirStoreOutFlowRateSensor> theCO2AirStoreOutFlowRateSensors = new Vector<CO2AirStoreOutFlowRateSensor>();

    public List<O2AirConcentrationSensor> theO2AirConcentrationSensors = new Vector<O2AirConcentrationSensor>();

    public List<O2AirPressureSensor> theO2AirPressureSensors = new Vector<O2AirPressureSensor>();

    public List<O2AirEnvironmentInFlowRateSensor> theO2AirEnvironmentInFlowRateSensors = new Vector<O2AirEnvironmentInFlowRateSensor>();

    public List<O2AirEnvironmentOutFlowRateSensor> theO2AirEnvironmentOutFlowRateSensors = new Vector<O2AirEnvironmentOutFlowRateSensor>();

    public List<O2AirStoreInFlowRateSensor> theO2AirStoreInFlowRateSensors = new Vector<O2AirStoreInFlowRateSensor>();

    public List<O2AirStoreOutFlowRateSensor> theO2AirStoreOutFlowRateSensors = new Vector<O2AirStoreOutFlowRateSensor>();

    public List<NitrogenAirConcentrationSensor> theNitrogenAirConcentrationSensors = new Vector<NitrogenAirConcentrationSensor>();

    public List<NitrogenAirPressureSensor> theNitrogenAirPressureSensors = new Vector<NitrogenAirPressureSensor>();

    public List<NitrogenAirEnvironmentInFlowRateSensor> theNitrogenAirEnvironmentInFlowRateSensors = new Vector<NitrogenAirEnvironmentInFlowRateSensor>();

    public List<NitrogenAirEnvironmentOutFlowRateSensor> theNitrogenAirEnvironmentOutFlowRateSensors = new Vector<NitrogenAirEnvironmentOutFlowRateSensor>();

    public List<NitrogenAirStoreInFlowRateSensor> theNitrogenAirStoreInFlowRateSensors = new Vector<NitrogenAirStoreInFlowRateSensor>();

    public List<NitrogenAirStoreOutFlowRateSensor> theNitrogenAirStoreOutFlowRateSensors = new Vector<NitrogenAirStoreOutFlowRateSensor>();

    public List<WaterAirConcentrationSensor> theWaterAirConcentrationSensors = new Vector<WaterAirConcentrationSensor>();

    public List<WaterAirPressureSensor> theWaterAirPressureSensors = new Vector<WaterAirPressureSensor>();

    public List<WaterAirEnvironmentInFlowRateSensor> theWaterAirEnvironmentInFlowRateSensors = new Vector<WaterAirEnvironmentInFlowRateSensor>();

    public List<WaterAirEnvironmentOutFlowRateSensor> theWaterAirEnvironmentOutFlowRateSensors = new Vector<WaterAirEnvironmentOutFlowRateSensor>();

    public List<WaterAirStoreInFlowRateSensor> theWaterAirStoreInFlowRateSensors = new Vector<WaterAirStoreInFlowRateSensor>();

    public List<WaterAirStoreOutFlowRateSensor> theWaterAirStoreOutFlowRateSensors = new Vector<WaterAirStoreOutFlowRateSensor>();

    public List<OtherAirConcentrationSensor> theOtherAirConcentrationSensors = new Vector<OtherAirConcentrationSensor>();

    public List<OtherAirPressureSensor> theOtherAirPressureSensors = new Vector<OtherAirPressureSensor>();

    //Food
    public List<BiomassInFlowRateSensor> theBiomassInFlowRateSensors = new Vector<BiomassInFlowRateSensor>();

    public List<BiomassOutFlowRateSensor> theBiomassOutFlowRateSensors = new Vector<BiomassOutFlowRateSensor>();

    public List<BiomassStoreLevelSensor> theBiomassStoreLevelSensors = new Vector<BiomassStoreLevelSensor>();

    public List<FoodInFlowRateSensor> theFoodInFlowRateSensors = new Vector<FoodInFlowRateSensor>();

    public List<FoodOutFlowRateSensor> theFoodOutFlowRateSensors = new Vector<FoodOutFlowRateSensor>();

    public List<FoodStoreLevelSensor> theFoodStoreLevelSensors = new Vector<FoodStoreLevelSensor>();

    public List<HarvestSensor> theHarvestSensors = new Vector<HarvestSensor>();

    public List<PlantDeathSensor> thePlantDeathSensors = new Vector<PlantDeathSensor>();

    public List<BiomassStoreWaterContentSensor> theBiomassStoreWaterContentSensors = new Vector<BiomassStoreWaterContentSensor>();

    //Framework
    public List<StoreOverflowSensor> theStoreOverflowSensors = new Vector<StoreOverflowSensor>();

    public List<StoreLevelSensor> theStoreLevelSensors = new Vector<StoreLevelSensor>();

    //Power
    public List<PowerInFlowRateSensor> thePowerInFlowRateSensors = new Vector<PowerInFlowRateSensor>();

    public List<PowerOutFlowRateSensor> thePowerOutFlowRateSensors = new Vector<PowerOutFlowRateSensor>();

    public List<PowerStoreLevelSensor> thePowerStoreLevelSensors = new Vector<PowerStoreLevelSensor>();

    //Waste
    public List<DryWasteInFlowRateSensor> theDryWasteInFlowRateSensors = new Vector<DryWasteInFlowRateSensor>();

    public List<DryWasteOutFlowRateSensor> theDryWasteOutFlowRateSensors = new Vector<DryWasteOutFlowRateSensor>();

    public List<DryWasteStoreLevelSensor> theDryWasteStoreLevelSensors = new Vector<DryWasteStoreLevelSensor>();

    //Water
    public List<PotableWaterInFlowRateSensor> thePotableWaterInFlowRateSensors = new Vector<PotableWaterInFlowRateSensor>();

    public List<PotableWaterOutFlowRateSensor> thePotableWaterOutFlowRateSensors = new Vector<PotableWaterOutFlowRateSensor>();

    public List<PotableWaterStoreLevelSensor> thePotableWaterStoreLevelSensors = new Vector<PotableWaterStoreLevelSensor>();

    public List<GreyWaterInFlowRateSensor> theGreyWaterInFlowRateSensors = new Vector<GreyWaterInFlowRateSensor>();

    public List<GreyWaterOutFlowRateSensor> theGreyWaterOutFlowRateSensors = new Vector<GreyWaterOutFlowRateSensor>();

    public List<GreyWaterStoreLevelSensor> theGreyWaterStoreLevelSensors = new Vector<GreyWaterStoreLevelSensor>();

    public List<DirtyWaterInFlowRateSensor> theDirtyWaterInFlowRateSensors = new Vector<DirtyWaterInFlowRateSensor>();

    public List<DirtyWaterOutFlowRateSensor> theDirtyWaterOutFlowRateSensors = new Vector<DirtyWaterOutFlowRateSensor>();

    public List<DirtyWaterStoreLevelSensor> theDirtyWaterStoreLevelSensors = new Vector<DirtyWaterStoreLevelSensor>();

    public List<WaterInFlowRateSensor> theWaterInFlowRateSensors = new Vector<WaterInFlowRateSensor>();

    public List<WaterOutFlowRateSensor> theWaterOutFlowRateSensors = new Vector<WaterOutFlowRateSensor>();

    public List<WaterStoreLevelSensor> theWaterStoreLevelSensors = new Vector<WaterStoreLevelSensor>();

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
        if (moduleWatched == null)
            Logger.getLogger(BioHolder.class).error("Your module is null!");
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
        Logger.getLogger(BioHolder.class).error(
                "Couldn't find any sensor attached to "
                        + moduleWatched.getModuleName() + " with shelf index "
                        + shelfIndex);
        return null;
    }

    public GenericActuator getShelfActuatorAttachedTo(List sensorList,
            BioModule moduleWatched, int shelfIndex) {
        if (moduleWatched == null)
            Logger.getLogger(BioHolder.class).error("Your module is null!");
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
        Logger.getLogger(BioHolder.class).error(
                "Couldn't find any acutator attached to "
                        + moduleWatched.getModuleName() + " with shelf index "
                        + shelfIndex);
        return null;
    }

    public GenericSensor getSensorAttachedTo(List sensorList,
            BioModule moduleWatched) {
        if (moduleWatched == null)
            Logger.getLogger(BioHolder.class).error("Your module is null!");
        for (Iterator iter = sensorList.iterator(); iter.hasNext();) {
            GenericSensor currentSensor = (GenericSensor) (iter.next());
            if (currentSensor.getInputModule()._is_equivalent(moduleWatched))
                return currentSensor;
        }
        Logger.getLogger(BioHolder.class).error(
                "Couldn't find any sensor attached to "
                        + moduleWatched.getModuleName());
        return null;
    }

    public GenericActuator getActuatorAttachedTo(List actuatorList,
            BioModule moduleWatched) {
        if (moduleWatched == null)
            Logger.getLogger(BioHolder.class).error("Your module is null!");
        for (Iterator iter = actuatorList.iterator(); iter.hasNext();) {
            GenericActuator currentActuator = (GenericActuator) (iter.next());
            String debugString = currentActuator.getModuleName()
                    + " is attached to "
                    + currentActuator.getOutputModule().getModuleName();
            Logger.getLogger(BioHolder.class).debug(debugString);
            if (currentActuator.getOutputModule()._is_equivalent(moduleWatched))
                return currentActuator;
        }
        Logger.getLogger(BioHolder.class).error(
                "Couldn't find any acutator attached to "
                        + moduleWatched.getModuleName());
        return null;
    }

    protected void coallateLists() {
        Logger.getLogger(BioHolder.class).debug("coallating lists");
        //Specific Modules
        //Simulation
        //Air
        theSimModules.addAll(theAirRSModules);
        theSimModules.addAll(theOGSModules);
        theSimModules.addAll(theVCCRModules);
        theSimModules.addAll(theCRSModules);
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

        Logger.getLogger(BioHolder.class).debug(
                "theModules.size() = " + theModules.size());
        //Make named list;
        for (Iterator iter = theModules.iterator(); iter.hasNext();) {
            BioModule currentModule = (BioModule) (iter.next());
            theModulesMapped.put(currentModule.getModuleName(), currentModule);
            Logger.getLogger(BioHolder.class).debug(
                    "mapped " + currentModule.getModuleName());
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
        theCRSModules.clear();
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