package com.traclabs.biosim.client.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuator;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.PlantingActuator;
import com.traclabs.biosim.idl.actuator.food.PlantingActuatorHelper;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.food.HarvestSensor;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.air.OGS;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.BiomassRS;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.FoodProcessor;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.food.Shelf;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterRS;

/**
 * @author Theresa Klein
 */

public class HandController {

    //feedback loop sttuff
    private double levelToKeepO2At = 0.20f;

    private double CrewCO2Level = 0.0012f;

    private double CrewH2OLevel = 0.01f;

    private double desiredAirPressure = 101f;

    private double crewO2integral, crewCO2integral, crewH2Ointegral;

    private final static String TAB = "\t";

    // hand controller stuff;

    private StateMap continuousState;

    private ActionMap currentAction;

    private Map classifiedState;

    private static int Runs = 0;

    private static Map thresholdMap = new TreeMap();

    private static BioDriver myBioDriver;

    private static BioHolder myBioHolder;

    private BiomassRS myBiomassRS;

    private FoodProcessor myFoodProcessor;

    private FoodStore myFoodStore;

    private BiomassStore myBiomassStore;

    private OGS myOGS;

    private WaterRS myWaterRS;

    private CrewGroup myCrew;

    private SimEnvironment myCrewEnvironment;

    private DirtyWaterStore myDirtyWaterStore;

    private GreyWaterStore myGreyWaterStore;

    private PotableWaterStore myPotableWaterStore;

    private PowerStore myPowerStore;

    private O2Store myO2Store;

    private static int ATMOSPHERIC_PERIOD = 2;

    private static int CORE_PERIOD_MULT = 5;

    private static int PLANT_PERIOD_MULT = 10;

    public static String[] stateNames = { "dirtywater", "greywater",
            "potablewater", "oxygen" };

    public static String[] actuatorNames = { "OGSpotable", "waterRSdirty",
            "waterRSgrey" };

    private File outFile;

    private FileWriter fw;

    private PrintWriter pw;

    private Logger myLogger;

    private boolean plantsExist = false;

    private DecimalFormat numFormat;

    public static final Integer HIGH = new Integer(0);

    public static final Integer LOW = new Integer(1);

    public static final Integer NORMAL = new Integer(2);

    private GenericSensor myBiomassStoreLevelSensor;

    private GenericSensor myFoodStoreLevelSensor;

    private PlantingActuator plantingActuator;

    private HarvestingActuator harvestActuator;

    private GenericSensor plantingSensor;

    private GenericSensor harvestSensor;

    private GenericActuator foodProcessorPower;

    private GenericActuator foodProcessorBiomass;

    private GenericActuator myO2AirEnvironmentOutInjectorAcutator;

    private GenericSensor myO2AirConcentrationSensor;

    private Injector myInjector;

    private GenericActuator myO2AirStoreInInjectorAcutator;

    public HandController() {
        myLogger = Logger.getLogger(this.getClass());
        numFormat = new DecimalFormat("#,##0.0;(#)");
    }

    public static void main(String[] args) {
        HandController myController = new HandController();
        myController.runSim();
    }

    private void initializeSim() {
        // ticks the sim one step at a time, observes the state, updates policy
        // and predictive model in
        // response to the current state and modifies actuators in response

        if (myLogger.isDebugEnabled()) {
            try {
                outFile = new File("handcontroller-output.txt");
                fw = new FileWriter(outFile);
            } catch (IOException e) {
            }
            pw = new PrintWriter(fw, true);
        }
        collectReferences();

        setThresholds();

        // initialize everything to off
        currentAction = new ActionMap();
        
        myBioDriver.startSimulation();

        crewO2integral = 0f;
        crewCO2integral = 0f;
        crewH2Ointegral = 0f;
        Runs++;

        continuousState = new StateMap();
        continuousState.updateState();
        classifiedState = classifyState(continuousState);
        currentAction.performAction(classifiedState);
    }

    private void collectReferences() {
        myBioHolder = BioHolderInitializer.getBioHolder();
        myBioDriver = myBioHolder.theBioDriver;
        //plantsExist = myBioHolder.theBiomassRSModules.size() > 0;
        if (plantsExist) 
            collectFoodReferences();

        myCrew = (CrewGroup) myBioHolder.theCrewGroups.get(0);
        myWaterRS = (WaterRS) myBioHolder.theWaterRSModules.get(0);
        myOGS = (OGS) myBioHolder.theOGSModules.get(0);

        myInjector = (Injector) myBioHolder.theInjectors.get(1);

        myDirtyWaterStore = (DirtyWaterStore) myBioHolder.theDirtyWaterStores
                .get(0);
        myPotableWaterStore = (PotableWaterStore) myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = (GreyWaterStore) myBioHolder.theGreyWaterStores
                .get(0);

        myO2Store = (O2Store) myBioHolder.theO2Stores.get(0);

        myCrewEnvironment = (SimEnvironment) myBioHolder.theSimEnvironments
                .get(0);
        myFoodStore = (FoodStore) myBioHolder.theFoodStores.get(0);
        myPowerStore = (PowerStore) myBioHolder.thePowerStores.get(0);
        
        myO2AirStoreInInjectorAcutator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.theO2AirStoreInFlowRateActuators, myInjector));
        
        myO2AirEnvironmentOutInjectorAcutator = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theO2AirEnvironmentOutFlowRateActuators,
                        myInjector));
        myO2AirConcentrationSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                myBioHolder.theO2AirConcentrationSensors, myCrewEnvironment));

    }

    /**
     * 
     */
    private void collectFoodReferences() {
        myBiomassRS = (BiomassRS) (myBioHolder.theBiomassRSModules.get(0));
        myBiomassRS.setAutoHarvestAndReplantEnabled(true);
        myFoodProcessor = (FoodProcessor) myBioHolder.theFoodProcessors
                .get(0);
        myBiomassStore = (BiomassStore) myBioHolder.theBiomassStores.get(0);
        
        myFoodStoreLevelSensor = (GenericSensor) (myBioHolder
                .getSensorAttachedTo(myBioHolder.theFoodStoreLevelSensors,
                        myFoodStore));
        myBiomassStoreLevelSensor = (GenericSensor) (myBioHolder
                .getSensorAttachedTo(myBioHolder.theBiomassStoreLevelSensors,
                        myBiomassStore));
        foodProcessorPower = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators,
                        myFoodProcessor));
        foodProcessorBiomass = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theBiomassInFlowRateActuators,
                        myFoodProcessor));
        plantingActuator = PlantingActuatorHelper.narrow((myBioHolder
                .getActuatorAttachedTo(myBioHolder.thePlantingActuators,
                        myBiomassRS)));
        harvestActuator = HarvestingActuatorHelper.narrow((myBioHolder
                .getActuatorAttachedTo(myBioHolder.theHarvestingActuators,
                        myBiomassRS)));
        
    }

    public void runSim() {
        initializeSim();
        myLogger.info("Controller starting run");
        while (!myBioDriver.isDone()) {
            stepSim();
        }
    }

    public void stepSim() {
        if (((myBioDriver.getTicks()) % (CORE_PERIOD_MULT * ATMOSPHERIC_PERIOD)) == 0) {
            myLogger.debug(myBioDriver.getTicks() + "");
            continuousState.printMe();
            currentAction.printMe();
            continuousState.updateState();
            classifiedState = classifyState(continuousState);
            currentAction.performAction(classifiedState);
            if (plantsExist) {
                doFoodProcessor();
                if (((myBioDriver.getTicks()) % (PLANT_PERIOD_MULT
                        * CORE_PERIOD_MULT * ATMOSPHERIC_PERIOD)) == 0) {
                    doPlants();
                }
            }
        }
        doInjectors();
        //advancing the sim n ticks
        myBioDriver.advanceOneTick();
    }

    public void setThresholds() {

        // sets up the threshold map variable
        int i;
        Map subMap;
        int dirtyWaterHighLevel = (int) myDirtyWaterStore.getCurrentCapacity();
        int dirtyWaterLowLevel = dirtyWaterHighLevel / 3;
        int greyWaterHighLevel = (int) myGreyWaterStore.getCurrentCapacity();
        int greyWaterLowLevel = greyWaterHighLevel / 3;
        int potableWaterHighLevel = (int) myPotableWaterStore
                .getCurrentCapacity();
        int potableWaterLowLevel = potableWaterHighLevel / 3;
        int O2StoreHighLevel = (int) myO2Store.getCurrentCapacity();
        int O2StoreLowLevel = O2StoreHighLevel / 3;

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(dirtyWaterLowLevel));
        subMap.put(HIGH, new Integer(dirtyWaterHighLevel));
        thresholdMap.put("dirtywater", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(greyWaterLowLevel));
        subMap.put(HIGH, new Integer(greyWaterHighLevel));
        thresholdMap.put("greywater", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(O2StoreLowLevel));
        subMap.put(HIGH, new Integer(O2StoreHighLevel));
        thresholdMap.put("oxygen", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(potableWaterLowLevel));
        subMap.put(HIGH, new Integer(potableWaterHighLevel));
        thresholdMap.put("potablewater", subMap);
    }

    public Map classifyState(StateMap instate) {

        int i;

        Map state = new TreeMap();

        Map thisSet;
        StringBuffer fileoutput;

        fileoutput = new StringBuffer(myBioDriver.getTicks());
        fileoutput.append(TAB);

        for (i = 0; i < stateNames.length; i++) {

            thisSet = (Map) thresholdMap.get(stateNames[i]);
            fileoutput.append(instate.getStateValue(stateNames[i]));
            fileoutput.append(TAB);
            if (instate.getStateValue(stateNames[i]) < ((Integer) thisSet
                    .get(LOW)).intValue())
                state.put(stateNames[i], LOW);
            else if (instate.getStateValue(stateNames[i]) > ((Integer) thisSet
                    .get(HIGH)).intValue())
                state.put(stateNames[i], HIGH);
            else
                state.put(stateNames[i], NORMAL);
        }
        return state;
    }

    private void doInjectors() {
        double delta, signal;
        double crewO2p, crewCO2p, crewH2Op;
        double crewO2i, crewCO2i, crewH2Oi;
        double crewO2, crewCO2, crewH2O;
        double crewAirPressure = myCrewEnvironment.getTotalPressure();
        //crew O2 feedback control
        crewO2p = 100f;
        crewO2i = 5f;
        crewO2 = myO2AirConcentrationSensor.getValue();
        delta = (double) (levelToKeepO2At - crewO2);
        crewO2integral += delta;
        signal = (delta * crewO2p + crewO2i * crewO2integral) + 2;
        myO2AirEnvironmentOutInjectorAcutator.setValue((float) (signal));
        myO2AirStoreInInjectorAcutator.setValue((float) (signal));
    }

    private void doFoodProcessor() {
        float food, biomass;
        int turnon = 1;
        biomass = (float) myBiomassStoreLevelSensor.getValue();
        food = (float) myFoodStoreLevelSensor.getValue();

        if (biomass <= 0 || food >= 1800)
            turnon = 0;
        else
            turnon = 1;

        myLogger.debug("Food: " + food + " Biomass " + biomass
                + "     Food Processor Power: " + turnon);

        if (turnon > 0) {
            foodProcessorPower.setValue(100);
            foodProcessorBiomass.setValue(20);
        } else {
            foodProcessorPower.setValue(0);
            foodProcessorBiomass.setValue(0);
        }

    }

    private void doPlants() {
        float overflow, new_overflow;
        float cropacres;

        int i;
        int num = myBioHolder.theHarvestSensors.size();

        for (i = 0; i < num; i++) {
            plantingSensor = (HarvestSensor) myBioHolder.theHarvestSensors
                    .get(i);
            plantingActuator = (PlantingActuator) myBioHolder.thePlantingActuators
                    .get(i);

            if (plantingSensor.getValue() == 1f) {
                myLogger.debug(" Harvest Sensor " + plantingSensor.getValue());
                myBiomassRS.getShelf(i).harvest();
                cropacres = myBiomassRS.getShelf(i).getCropAreaTotal();
                myLogger
                        .debug("Planting "
                                + cropacres
                                + " m^2."
                                + ((Shelf) myBiomassRS.getShelf(i))
                                        .getCropTypeString());
                plantingActuator.setPlantType(myBiomassRS.getShelf(i)
                        .getCropType());
                plantingActuator.setValue(cropacres);
            }
        }
    }

}

