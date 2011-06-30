package com.traclabs.biosim.client.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.MethaneInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.MethaneOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuator;
import com.traclabs.biosim.idl.actuator.food.PlantingActuator;
import com.traclabs.biosim.idl.actuator.food.ShelfActuator;
import com.traclabs.biosim.idl.actuator.framework.EffluentValveActuator;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.actuator.framework.InfluentValveActuator;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.WaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.WaterOutFlowRateActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensor;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.GasConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.TotalMolesSensor;
import com.traclabs.biosim.idl.sensor.environment.TotalPressureSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensor;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.HarvestSensor;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensor;
import com.traclabs.biosim.idl.sensor.food.ShelfSensor;
import com.traclabs.biosim.idl.sensor.food.TimeTillCanopyClosureSensor;
import com.traclabs.biosim.idl.sensor.framework.EffluentValveStateSensor;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.sensor.framework.InfluentValveStateSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensor;
import com.traclabs.biosim.idl.sensor.framework.TimeSensor;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensor;
import com.traclabs.biosim.idl.simulation.air.AirRS;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.CRS;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.MethaneStore;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.air.OGS;
import com.traclabs.biosim.idl.simulation.air.Pyrolizer;
import com.traclabs.biosim.idl.simulation.air.VCCR;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.environment.Dehumidifier;
import com.traclabs.biosim.idl.simulation.environment.Fan;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.BiomassPS;
import com.traclabs.biosim.idl.simulation.food.BiomassPSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.FoodProcessor;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.food.Shelf;
import com.traclabs.biosim.idl.simulation.framework.Accumulator;
import com.traclabs.biosim.idl.simulation.framework.EffluentValve;
import com.traclabs.biosim.idl.simulation.framework.InfluentValve;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.idl.simulation.framework.SimBioModule;
import com.traclabs.biosim.idl.simulation.power.GenericPowerConsumer;
import com.traclabs.biosim.idl.simulation.power.PowerPS;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.power.RPCM;
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

    public Map<String, BioModule> theModulesMapped = new Hashtable<String, BioModule>();

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

    public List<CDRSModule> theCDRSModules = new Vector<CDRSModule>();

    public List<CRS> theCRSModules = new Vector<CRS>();

    public List<Pyrolizer> thePyrolizerModules = new Vector<Pyrolizer>();

    public List<O2Store> theO2Stores = new Vector<O2Store>();

    public List<CO2Store> theCO2Stores = new Vector<CO2Store>();

    public List<H2Store> theH2Stores = new Vector<H2Store>();

    public List<NitrogenStore> theNitrogenStores = new Vector<NitrogenStore>();

    public List<MethaneStore> theMethaneStores = new Vector<MethaneStore>();

    //Crew
    public List<CrewGroup> theCrewGroups = new Vector<CrewGroup>();

    //Environment
    public List<SimEnvironment> theSimEnvironments = new Vector<SimEnvironment>();

    public List<Dehumidifier> theDehumidifiers = new Vector<Dehumidifier>();

    public List<Fan> theFans = new Vector<Fan>();

    //Food
    public List<FoodProcessor> theFoodProcessors = new Vector<FoodProcessor>();

    public List<BiomassPS> theBiomassPSModules = new Vector<BiomassPS>();

    public List<BiomassStore> theBiomassStores = new Vector<BiomassStore>();

    public List<FoodStore> theFoodStores = new Vector<FoodStore>();

    //Framework
    public List<Accumulator> theAccumulators = new Vector<Accumulator>();

    public List<Injector> theInjectors = new Vector<Injector>();

    public List<InfluentValve> theInfluentValves = new Vector<InfluentValve>();
    
    public List<EffluentValve> theEffluentValves = new Vector<EffluentValve>();

    //Power
    public List<PowerPS> thePowerPSModules = new Vector<PowerPS>();

    public List<PowerStore> thePowerStores = new Vector<PowerStore>();
    
    public List<RPCM> theRPCMs = new Vector<RPCM>();
    
    public List<GenericPowerConsumer> theGenericPowerConsumers = new Vector<GenericPowerConsumer>();

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

    public List<O2InFlowRateSensor> theO2InFlowRateSensors = new Vector<O2InFlowRateSensor>();

    public List<O2OutFlowRateSensor> theO2OutFlowRateSensors = new Vector<O2OutFlowRateSensor>();

    public List<H2InFlowRateSensor> theH2InFlowRateSensors = new Vector<H2InFlowRateSensor>();

    public List<H2OutFlowRateSensor> theH2OutFlowRateSensors = new Vector<H2OutFlowRateSensor>();

    public List<NitrogenInFlowRateSensor> theNitrogenInFlowRateSensors = new Vector<NitrogenInFlowRateSensor>();

    public List<NitrogenOutFlowRateSensor> theNitrogenOutFlowRateSensors = new Vector<NitrogenOutFlowRateSensor>();

    public List<MethaneInFlowRateSensor> theMethaneInFlowRateSensors = new Vector<MethaneInFlowRateSensor>();

    public List<MethaneOutFlowRateSensor> theMethaneOutFlowRateSensors = new Vector<MethaneOutFlowRateSensor>();
    //Crew
    public List<CrewGroupDeathSensor> theCrewGroupDeathSensors = new Vector<CrewGroupDeathSensor>();

    public List<CrewGroupProductivitySensor> theCrewGroupProductivitySensors = new Vector<CrewGroupProductivitySensor>();

    public List<CrewGroupAnyDeadSensor> theCrewGroupAnyDeadSensors = new Vector<CrewGroupAnyDeadSensor>();

    //Environment
    public List<AirInFlowRateSensor> theAirInFlowRateSensors = new Vector<AirInFlowRateSensor>();

    public List<AirOutFlowRateSensor> theAirOutFlowRateSensors = new Vector<AirOutFlowRateSensor>();

    public List<GasPressureSensor> theGasPressureSensors = new Vector<GasPressureSensor>();

    public List<GasConcentrationSensor> theGasConcentrationSensors = new Vector<GasConcentrationSensor>();

    public List<TotalMolesSensor> theTotalMolesSensors = new Vector<TotalMolesSensor>();

    public List<TotalPressureSensor> theTotalPressureSensors = new Vector<TotalPressureSensor>();

    //Food
    public List<BiomassInFlowRateSensor> theBiomassInFlowRateSensors = new Vector<BiomassInFlowRateSensor>();

    public List<BiomassOutFlowRateSensor> theBiomassOutFlowRateSensors = new Vector<BiomassOutFlowRateSensor>();

    public List<FoodInFlowRateSensor> theFoodInFlowRateSensors = new Vector<FoodInFlowRateSensor>();

    public List<FoodOutFlowRateSensor> theFoodOutFlowRateSensors = new Vector<FoodOutFlowRateSensor>();

    public List<HarvestSensor> theHarvestSensors = new Vector<HarvestSensor>();

    public List<PlantDeathSensor> thePlantDeathSensors = new Vector<PlantDeathSensor>();
    
    public List<TimeTillCanopyClosureSensor> theTimeTillCanopyClosureSensors = new Vector<TimeTillCanopyClosureSensor>();

    public List<BiomassStoreWaterContentSensor> theBiomassStoreWaterContentSensors = new Vector<BiomassStoreWaterContentSensor>();

    //Framework
    public List<StoreOverflowSensor> theStoreOverflowSensors = new Vector<StoreOverflowSensor>();

    public List<StoreLevelSensor> theStoreLevelSensors = new Vector<StoreLevelSensor>();

    public List<TimeSensor> theTimeSensors = new Vector<TimeSensor>();
    
    public List<InfluentValveStateSensor> theInfluentValveStateSensors = new Vector<InfluentValveStateSensor>();
    
    public List<EffluentValveStateSensor> theEffluentValveStateSensors = new Vector<EffluentValveStateSensor>();

    //Power
    public List<PowerInFlowRateSensor> thePowerInFlowRateSensors = new Vector<PowerInFlowRateSensor>();

    public List<PowerOutFlowRateSensor> thePowerOutFlowRateSensors = new Vector<PowerOutFlowRateSensor>();

    //Waste
    public List<DryWasteInFlowRateSensor> theDryWasteInFlowRateSensors = new Vector<DryWasteInFlowRateSensor>();

    public List<DryWasteOutFlowRateSensor> theDryWasteOutFlowRateSensors = new Vector<DryWasteOutFlowRateSensor>();

    //Water
    public List<PotableWaterInFlowRateSensor> thePotableWaterInFlowRateSensors = new Vector<PotableWaterInFlowRateSensor>();

    public List<PotableWaterOutFlowRateSensor> thePotableWaterOutFlowRateSensors = new Vector<PotableWaterOutFlowRateSensor>();

    public List<GreyWaterInFlowRateSensor> theGreyWaterInFlowRateSensors = new Vector<GreyWaterInFlowRateSensor>();

    public List<GreyWaterOutFlowRateSensor> theGreyWaterOutFlowRateSensors = new Vector<GreyWaterOutFlowRateSensor>();

    public List<DirtyWaterInFlowRateSensor> theDirtyWaterInFlowRateSensors = new Vector<DirtyWaterInFlowRateSensor>();

    public List<DirtyWaterOutFlowRateSensor> theDirtyWaterOutFlowRateSensors = new Vector<DirtyWaterOutFlowRateSensor>();

    public List<WaterInFlowRateSensor> theWaterInFlowRateSensors = new Vector<WaterInFlowRateSensor>();

    public List<WaterOutFlowRateSensor> theWaterOutFlowRateSensors = new Vector<WaterOutFlowRateSensor>();

    //Actuators
    //Air
    public List<CO2InFlowRateActuator> theCO2InFlowRateActuators = new Vector<CO2InFlowRateActuator>();

    public List<CO2OutFlowRateActuator> theCO2OutFlowRateActuators = new Vector<CO2OutFlowRateActuator>();

    public List<O2InFlowRateActuator> theO2InFlowRateActuators = new Vector<O2InFlowRateActuator>();

    public List<O2OutFlowRateActuator> theO2OutFlowRateActuators = new Vector<O2OutFlowRateActuator>();

    public List<H2InFlowRateActuator> theH2InFlowRateActuators = new Vector<H2InFlowRateActuator>();

    public List<H2OutFlowRateActuator> theH2OutFlowRateActuators = new Vector<H2OutFlowRateActuator>();

    public List<NitrogenInFlowRateActuator> theNitrogenInFlowRateActuators = new Vector<NitrogenInFlowRateActuator>();

    public List<NitrogenOutFlowRateActuator> theNitrogenOutFlowRateActuators = new Vector<NitrogenOutFlowRateActuator>();

    public List<MethaneInFlowRateActuator> theMethaneInFlowRateActuators = new Vector<MethaneInFlowRateActuator>();

    public List<MethaneOutFlowRateActuator> theMethaneOutFlowRateActuators = new Vector<MethaneOutFlowRateActuator>();

    //Environment
    public List<AirInFlowRateActuator> theAirInFlowRateActuators = new Vector<AirInFlowRateActuator>();

    public List<AirOutFlowRateActuator> theAirOutFlowRateActuators = new Vector<AirOutFlowRateActuator>();

    //Food
    public List<BiomassInFlowRateActuator> theBiomassInFlowRateActuators = new Vector<BiomassInFlowRateActuator>();

    public List<BiomassOutFlowRateActuator> theBiomassOutFlowRateActuators = new Vector<BiomassOutFlowRateActuator>();

    public List<FoodInFlowRateActuator> theFoodInFlowRateActuators = new Vector<FoodInFlowRateActuator>();

    public List<FoodOutFlowRateActuator> theFoodOutFlowRateActuators = new Vector<FoodOutFlowRateActuator>();
    
    public List<PlantingActuator> thePlantingActuators = new Vector<PlantingActuator>();

    public List<HarvestingActuator> theHarvestingActuators = new Vector<HarvestingActuator>();
    
    //Framework
    public List<InfluentValveActuator> theInfluentValveActuators = new Vector<InfluentValveActuator>();

    public List<EffluentValveActuator> theEffluentValveActuators = new Vector<EffluentValveActuator>();
    
    //Power
    public List<PowerInFlowRateActuator> thePowerInFlowRateActuators = new Vector<PowerInFlowRateActuator>();

    public List<PowerOutFlowRateActuator> thePowerOutFlowRateActuators = new Vector<PowerOutFlowRateActuator>();

    //Waste
    public List<DryWasteInFlowRateActuator> theDryWasteInFlowRateActuators = new Vector<DryWasteInFlowRateActuator>();

    public List<DryWasteOutFlowRateActuator> theDryWasteOutFlowRateActuators = new Vector<DryWasteOutFlowRateActuator>();

    //Water
    public List<PotableWaterInFlowRateActuator> thePotableWaterInFlowRateActuators = new Vector<PotableWaterInFlowRateActuator>();

    public List<PotableWaterOutFlowRateActuator> thePotableWaterOutFlowRateActuators = new Vector<PotableWaterOutFlowRateActuator>();

    public List<GreyWaterInFlowRateActuator> theGreyWaterInFlowRateActuators = new Vector<GreyWaterInFlowRateActuator>();

    public List<GreyWaterOutFlowRateActuator> theGreyWaterOutFlowRateActuators = new Vector<GreyWaterOutFlowRateActuator>();

    public List<DirtyWaterInFlowRateActuator> theDirtyWaterInFlowRateActuators = new Vector<DirtyWaterInFlowRateActuator>();

    public List<DirtyWaterOutFlowRateActuator> theDirtyWaterOutFlowRateActuators = new Vector<DirtyWaterOutFlowRateActuator>();

    public List<WaterInFlowRateActuator> theWaterInFlowRateActuators = new Vector<WaterInFlowRateActuator>();

    public List<WaterOutFlowRateActuator> theWaterOutFlowRateActuators = new Vector<WaterOutFlowRateActuator>();

    protected BioHolder() {
    }

    public GenericSensor getShelfSensorAttachedTo(List sensorList,
            BioModule moduleWatched, int shelfIndex) {
        if (moduleWatched == null)
            Logger.getLogger(BioHolder.class).error("Your module is null!");
        for (Iterator iter = sensorList.iterator(); iter.hasNext();) {
            ShelfSensor currentSensor = (ShelfSensor) (iter.next());
            if (currentSensor.getInputModule()._is_equivalent(moduleWatched)) {
                BiomassPS currentBiomassPS = BiomassPSHelper
                        .narrow(currentSensor.getInputModule());
                Shelf[] shelfArray = currentBiomassPS.getShelves();
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
                BiomassPS currentBiomassPS = BiomassPSHelper
                        .narrow(currentActuator.getOutputModule());
                Shelf[] shelfArray = currentBiomassPS.getShelves();
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
        theSimModules.addAll(theCDRSModules);
        theSimModules.addAll(thePyrolizerModules);
        theSimModules.addAll(theO2Stores);
        theSimModules.addAll(theCO2Stores);
        theSimModules.addAll(theNitrogenStores);
        theSimModules.addAll(theMethaneStores);
        theSimModules.addAll(theH2Stores);
        //Crew
        theSimModules.addAll(theCrewGroups);
        //Environment
        theSimModules.addAll(theSimEnvironments);
        theSimModules.addAll(theDehumidifiers);
        theSimModules.addAll(theFans);
        //Food
        theSimModules.addAll(theFoodProcessors);
        theSimModules.addAll(theBiomassPSModules);
        theSimModules.addAll(theBiomassStores);
        theSimModules.addAll(theFoodStores);
        //Framework
        theSimModules.addAll(theAccumulators);
        theSimModules.addAll(theInjectors);
        theSimModules.addAll(theInfluentValves);
        theSimModules.addAll(theEffluentValves);
        //Power
        theSimModules.addAll(thePowerPSModules);
        theSimModules.addAll(thePowerStores);
        theSimModules.addAll(theRPCMs);
        theSimModules.addAll(theGenericPowerConsumers);
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
        theSensors.addAll(theO2InFlowRateSensors);
        theSensors.addAll(theO2OutFlowRateSensors);
        theSensors.addAll(theH2InFlowRateSensors);
        theSensors.addAll(theH2OutFlowRateSensors);
        theSensors.addAll(theNitrogenInFlowRateSensors);
        theSensors.addAll(theNitrogenOutFlowRateSensors);
        theSensors.addAll(theMethaneInFlowRateSensors);
        theSensors.addAll(theMethaneOutFlowRateSensors);
        //Crew
        theSensors.addAll(theCrewGroupDeathSensors);
        theSensors.addAll(theCrewGroupProductivitySensors);
        theSensors.addAll(theCrewGroupAnyDeadSensors);
        //Environment
        theSensors.addAll(theAirInFlowRateSensors);
        theSensors.addAll(theAirOutFlowRateSensors);
        theSensors.addAll(theGasPressureSensors);
        theSensors.addAll(theGasConcentrationSensors);
        theSensors.addAll(theTotalMolesSensors);
        theSensors.addAll(theTotalPressureSensors);
        //Food
        theSensors.addAll(theBiomassInFlowRateSensors);
        theSensors.addAll(theBiomassOutFlowRateSensors);
        theSensors.addAll(theFoodInFlowRateSensors);
        theSensors.addAll(theFoodOutFlowRateSensors);
        theSensors.addAll(theHarvestSensors);
        theSensors.addAll(thePlantDeathSensors);
        theSensors.addAll(theBiomassStoreWaterContentSensors);
        theSensors.addAll(theTimeTillCanopyClosureSensors);
        //Framework
        theSensors.addAll(theStoreOverflowSensors);
        theSensors.addAll(theStoreLevelSensors);
        theSensors.addAll(theInfluentValveStateSensors);
        theSensors.addAll(theEffluentValveStateSensors);
        theSensors.addAll(theTimeSensors);
        //Power
        theSensors.addAll(thePowerInFlowRateSensors);
        theSensors.addAll(thePowerOutFlowRateSensors);
        //Waste
        theSensors.addAll(theDryWasteInFlowRateSensors);
        theSensors.addAll(theDryWasteOutFlowRateSensors);
        //Water
        theSensors.addAll(thePotableWaterInFlowRateSensors);
        theSensors.addAll(thePotableWaterOutFlowRateSensors);
        theSensors.addAll(theGreyWaterInFlowRateSensors);
        theSensors.addAll(theGreyWaterOutFlowRateSensors);
        theSensors.addAll(theDirtyWaterInFlowRateSensors);
        theSensors.addAll(theDirtyWaterOutFlowRateSensors);
        theSensors.addAll(theWaterInFlowRateSensors);
        theSensors.addAll(theWaterOutFlowRateSensors);
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
        theActuators.addAll(theMethaneInFlowRateActuators);
        theActuators.addAll(theMethaneOutFlowRateActuators);
        //Environment
        theActuators.addAll(theAirInFlowRateActuators);
        theActuators.addAll(theAirOutFlowRateActuators);
        //Framework
        theActuators.addAll(theInfluentValveActuators);
        theActuators.addAll(theEffluentValveActuators);
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
        theCDRSModules.clear();
        thePyrolizerModules.clear();
        theO2Stores.clear();
        theCO2Stores.clear();
        theNitrogenStores.clear();
        theMethaneStores.clear();
        theH2Stores.clear();
        //Crew
        theCrewGroups.clear();
        //Environment
        theSimEnvironments.clear();
        theDehumidifiers.clear();
        theFans.clear();
        //Food
        theFoodProcessors.clear();
        theBiomassPSModules.clear();
        theBiomassStores.clear();
        theFoodStores.clear();
        //Framework
        theAccumulators.clear();
        theInjectors.clear();
        theInfluentValves.clear();
        theEffluentValves.clear();
        //Power
        thePowerPSModules.clear();
        thePowerStores.clear();
        theRPCMs.clear();
        theGenericPowerConsumers.clear();
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
        theO2InFlowRateSensors.clear();
        theO2OutFlowRateSensors.clear();
        theH2InFlowRateSensors.clear();
        theH2OutFlowRateSensors.clear();
        theNitrogenInFlowRateSensors.clear();
        theNitrogenOutFlowRateSensors.clear();
        theMethaneInFlowRateSensors.clear();
        theMethaneOutFlowRateSensors.clear();
        //Crew
        theCrewGroupDeathSensors.clear();
        theCrewGroupProductivitySensors.clear();
        theCrewGroupAnyDeadSensors.clear();
        //Environment
        theAirInFlowRateSensors.clear();
        theAirOutFlowRateSensors.clear();
        theGasPressureSensors.clear();
        theGasConcentrationSensors.clear();
        //Food
        theBiomassInFlowRateSensors.clear();
        theBiomassOutFlowRateSensors.clear();
        theFoodInFlowRateSensors.clear();
        theFoodOutFlowRateSensors.clear();
        theHarvestSensors.clear();
        thePlantDeathSensors.clear();
        theBiomassStoreWaterContentSensors.clear();
        theTimeTillCanopyClosureSensors.clear();
        //Framework
        theStoreOverflowSensors.clear();
        theStoreLevelSensors.clear();
        theInfluentValveStateSensors.clear();
        theEffluentValveStateSensors.clear();
        theTimeSensors.clear();
        //Power
        thePowerInFlowRateSensors.clear();
        thePowerOutFlowRateSensors.clear();
        //Waste
        theDryWasteInFlowRateSensors.clear();
        theDryWasteOutFlowRateSensors.clear();
        //Water
        thePotableWaterInFlowRateSensors.clear();
        thePotableWaterOutFlowRateSensors.clear();
        theGreyWaterInFlowRateSensors.clear();
        theGreyWaterOutFlowRateSensors.clear();
        theDirtyWaterInFlowRateSensors.clear();
        theDirtyWaterOutFlowRateSensors.clear();
        theWaterInFlowRateSensors.clear();
        theWaterOutFlowRateSensors.clear();
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
        theMethaneInFlowRateActuators.clear();
        theMethaneOutFlowRateActuators.clear();
        //Environment
        theAirInFlowRateActuators.clear();
        theAirOutFlowRateActuators.clear();
        //Food
        theBiomassInFlowRateActuators.clear();
        theBiomassOutFlowRateActuators.clear();
        theFoodInFlowRateActuators.clear();
        theFoodOutFlowRateActuators.clear();
        thePlantingActuators.clear();
        theHarvestingActuators.clear();
        //Framwork
        theInfluentValveActuators.clear();
        theEffluentValveActuators.clear();
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