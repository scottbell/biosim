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
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensor;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.H2Store;
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
    private int dirtyWater = 0;

    private int greyWater = 0;

    private int CO2 = 0;

    private int potableWater = 0;

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

    private CO2Store myCO2Store;

    private H2Store myH2Store;

    private static int ATMOSPHERIC_PERIOD = 2;

    private static int CORE_PERIOD_MULT = 5;

    private static int PLANT_PERIOD_MULT = 10;

    public static String[] stateNames = { "carbondioxide", "dirtywater",
            "greywater", "hydrogen", "oxygen", "potablewater" };

    public static String[] actuatorNames = { "OGSpotable", "waterRSdirty",
            "waterRSgrey" };

    private File outFile;

    private FileWriter fw;

    private PrintWriter pw;

    private Logger myLogger;

    private boolean plantsExist;

    private DecimalFormat numFormat;
    
    private static final Integer HIGH = new Integer(0);
    private static final Integer LOW = new Integer(1);
    private static final Integer NORMAL = new Integer(2);

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
        int i;
        float tmp2, oldvalue, newvalue;
        GenericActuator currentActuator;

        if (myLogger.isDebugEnabled()) {
            try { 
                outFile = new File("handcontroller-output.txt");
                fw = new FileWriter(outFile);
            } catch (IOException e) {
            }
            pw = new PrintWriter(fw, true);
        }
        myBioHolder = BioHolderInitializer.getBioHolder();
        myBioDriver = myBioHolder.theBioDriver;
        plantsExist = myBioHolder.theBiomassRSModules.size() > 0;
        if (plantsExist) {
            myBiomassRS = (BiomassRS) (myBioHolder.theBiomassRSModules.get(0));
            myBiomassRS.setAutoHarvestAndReplantEnabled(true);
            myFoodProcessor = (FoodProcessor) myBioHolder.theFoodProcessors
                    .get(0);
            myBiomassStore = (BiomassStore) myBioHolder.theBiomassStores.get(0);
        }

        myCrew = (CrewGroup) myBioHolder.theCrewGroups.get(0);
        myWaterRS = (WaterRS) myBioHolder.theWaterRSModules.get(0);
        myOGS = (OGS) myBioHolder.theOGSModules.get(0);

        myDirtyWaterStore = (DirtyWaterStore) myBioHolder.theDirtyWaterStores
                .get(0);
        myPotableWaterStore = (PotableWaterStore) myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = (GreyWaterStore) myBioHolder.theGreyWaterStores
                .get(0);

        myO2Store = (O2Store) myBioHolder.theO2Stores.get(0);
        myCO2Store = (CO2Store) myBioHolder.theCO2Stores.get(0);
        myH2Store = (H2Store) myBioHolder.theH2Stores.get(0);

        myCrewEnvironment = (SimEnvironment) myBioHolder.theSimEnvironments
                .get(0);
        myFoodStore = (FoodStore) myBioHolder.theFoodStores.get(0);
        myPowerStore = (PowerStore) myBioHolder.thePowerStores.get(0);
        //supply power
        /*currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.thePowerInFlowRateActuators, myWaterRS));
        currentActuator.setValue(500);
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.thePowerInFlowRateActuators, myOGS));
        currentActuator.setValue(300);
        */
        //set values for other inputs and outputs
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.thePotableWaterOutFlowRateActuators, myWaterRS));
        currentActuator.setValue(10);
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.theH2OutFlowRateActuators, myOGS));
        currentActuator.setValue(10);
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.theO2OutFlowRateActuators, myOGS));
        currentActuator.setValue(10);
        if (plantsExist) {
            currentActuator = (GenericActuator) (myBioHolder
                    .getActuatorAttachedTo(
                            myBioHolder.theBiomassInFlowRateActuators,
                            myFoodProcessor));
            currentActuator.setValue(0);
            currentActuator = (GenericActuator) (myBioHolder
                    .getActuatorAttachedTo(
                            myBioHolder.theGreyWaterInFlowRateActuators,
                            myBiomassRS));
            currentActuator.setValue(2);
        }

        setThresholds();

        // initialize everything to off
        currentActuator.setValue(0);
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.thePotableWaterInFlowRateActuators, myOGS));
        currentActuator.setValue(0);
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.theDirtyWaterInFlowRateActuators, myWaterRS));
        currentActuator.setValue(0);
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.theGreyWaterInFlowRateActuators, myWaterRS));
        currentActuator.setValue(0);

        myBioDriver.startSimulation();

        crewO2integral = 0f;
        crewCO2integral = 0f;
        crewH2Ointegral = 0f;
        Runs++;

        continuousState = new StateMap();
        continuousState.updateState();
        classifiedState = classifyState(continuousState);
        currentAction = findActionMap(classifiedState);
        setActuators(currentAction);

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
            currentAction = findActionMap(classifiedState);
            setActuators(currentAction);
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
        int dirtyWaterLowLevel = dirtyWaterHighLevel / 40;
        int greyWaterHighLevel = (int) myGreyWaterStore.getCurrentCapacity();
        int greyWaterLowLevel = greyWaterHighLevel / 40;
        int potableWaterHighLevel = (int) myPotableWaterStore
                .getCurrentCapacity();
        int potableWaterLowLevel = potableWaterHighLevel / 40;
        int O2StoreHighLevel = (int) myO2Store.getCurrentCapacity();
        int O2StoreLowLevel = O2StoreHighLevel / 4;
        int CO2StoreHighLevel = (int) myCO2Store.getCurrentCapacity();
        int CO2StoreLowLevel = CO2StoreHighLevel / 40;
        int H2StoreHighLevel = (int) myH2Store.getCurrentCapacity();
        int H2StoreLowLevel = H2StoreHighLevel / 100;

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(dirtyWaterLowLevel));
        subMap.put(HIGH, new Integer(dirtyWaterHighLevel));
        thresholdMap.put("dirtywater", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(greyWaterLowLevel));
        subMap.put(HIGH, new Integer(greyWaterHighLevel));
        thresholdMap.put("greywater", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(potableWaterLowLevel));
        subMap.put(HIGH, new Integer(potableWaterHighLevel));
        thresholdMap.put("potablewater", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(O2StoreLowLevel));
        subMap.put(HIGH, new Integer(O2StoreHighLevel));
        thresholdMap.put("oxygen", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(CO2StoreLowLevel));
        subMap.put(HIGH, new Integer(CO2StoreHighLevel));
        thresholdMap.put("carbondioxide", subMap);

        subMap = new TreeMap();
        subMap.put(LOW, new Integer(H2StoreLowLevel));
        subMap.put(HIGH, new Integer(H2StoreHighLevel));
        thresholdMap.put("hydrogen", subMap);

    }

    public Map classifyState(StateMap instate) {

        int i;

        Map state = new TreeMap();

        Map thisSet;
        GenericSensor currentSensor;
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

        if (plantsExist) {
            currentSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                    myBioHolder.theBiomassStoreLevelSensors, myBiomassStore));
            fileoutput.append(currentSensor.getValue());
            fileoutput.append(TAB);
        }

        currentSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                myBioHolder.theFoodStoreLevelSensors, myFoodStore));
        fileoutput.append(currentSensor.getValue());
        fileoutput.append(TAB);

        currentSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                myBioHolder.theO2AirConcentrationSensors, myCrewEnvironment));
        myLogger.debug("Crew O2..." + currentSensor.getValue());
        fileoutput.append(currentSensor.getValue());
        fileoutput.append(TAB);

        currentSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                myBioHolder.theCO2AirConcentrationSensors, myCrewEnvironment));
        myLogger.debug("Crew CO2..." + currentSensor.getValue());
        fileoutput.append(currentSensor.getValue());
        fileoutput.append(TAB);

        currentSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                myBioHolder.theWaterAirConcentrationSensors, myCrewEnvironment));
        myLogger.debug("Crew Water Vapor..." + currentSensor.getValue());
        fileoutput.append(currentSensor.getValue());
        fileoutput.append(TAB);

        currentSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                myBioHolder.thePowerStoreLevelSensors, myPowerStore));
        myLogger.debug("Power..." + currentSensor.getValue());

        if (myLogger.isDebugEnabled())
            pw.println(fileoutput);

        return state;
    }

    public void setActuators(ActionMap currentAction) {

        // sets actuator values according to the current policy

        GenericActuator currentActuator;
        int i;

        String[] names = ActionMap.actuatorNames;

        for (i = 0; i < names.length; i++) {
            currentActuator = (GenericActuator) (ActionMap.actuators[i]);
            currentActuator.setValue((float) currentAction
                    .getActuatorValue(names[i]));
            myLogger.debug("Setting " + names[i] + " to "
                    + currentAction.getActuatorValue(names[i]));
        }

    }

    private void doInjectors() {

        // a crude feedback controller for the accumulators and injectors
        GenericSensor levelSensor, rateSensor;
        GenericActuator currentActuator;
        String printout;

        double delta, signal;
        double crewO2p, crewCO2p, crewH2Op;
        double crewO2i, crewCO2i, crewH2Oi;
        double crewO2, crewCO2, crewH2O;

        Injector myInjector = (Injector) myBioHolder.theInjectors.get(1);

        double crewAirPressure = myCrewEnvironment.getTotalPressure();
        //crew O2 feedback control
        crewO2p = 100f;
        crewO2i = 5f;
        levelSensor = (GenericSensor) (myBioHolder.getSensorAttachedTo(
                myBioHolder.theO2AirConcentrationSensors, myCrewEnvironment));
        crewO2 = levelSensor.getValue();
        delta = (double) (levelToKeepO2At - crewO2);
        crewO2integral += delta;
        signal = (delta * crewO2p + crewO2i * crewO2integral) + 2;
        /*
        myLogger.info("levelToKeepO2At = "+levelToKeepO2At);
        myLogger.info("crewO2 = "+crewO2);
        myLogger.info("crewO2integral = "+crewO2integral);
        myLogger.info("delta = "+delta);
        myLogger.info("O2 tank ("
                + numFormat.format(myO2Store.getCurrentLevel())
                + ") flow to Crew environment: " + numFormat.format(signal));
        */
        currentActuator = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theO2AirEnvironmentOutFlowRateActuators,
                        myInjector));
        if (currentActuator == null)
            myLogger.error("currentActuator = null!!");
        currentActuator.setValue((float) (signal));
        currentActuator = (GenericActuator) (myBioHolder.getActuatorAttachedTo(
                myBioHolder.theO2AirStoreInFlowRateActuators, myInjector));
        currentActuator.setValue((float) (signal));
    }

    private void doFoodProcessor() {
        GenericActuator foodProcessorPower, foodProcessorBiomass;
        float food, biomass;
        int turnon = 1;

        GenericSensor myFoodStoreLevelSensor = (GenericSensor) (myBioHolder
                .getSensorAttachedTo(myBioHolder.theFoodStoreLevelSensors,
                        myFoodStore));
        GenericSensor myBiomassStoreLevelSensor = (GenericSensor) (myBioHolder
                .getSensorAttachedTo(myBioHolder.theBiomassStoreLevelSensors,
                        myBiomassStore));
        foodProcessorPower = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(myBioHolder.thePowerInFlowRateActuators,
                        myFoodProcessor));
        foodProcessorBiomass = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theBiomassInFlowRateActuators,
                        myFoodProcessor));
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

    private ActionMap findActionMap(Map SimState) {
        String printout = "";
        ActionMap myAction;
        int i;
        if (SimState.get("potablewater") == LOW) {
            dirtyWater = 1;
        } else if (SimState.get("dirtywater") == HIGH
                && SimState.get("potablewater") != HIGH) {
            dirtyWater = 1;
        } else {
            dirtyWater = 0;
        }
        if (SimState.get("potablewater") == LOW
                && SimState.get("greywater") != LOW) {
            greyWater = 1;
        } else if (SimState.get("greywater") == HIGH
                && SimState.get("potablewater") != HIGH) {
            greyWater = 1;
        } else {
            greyWater = 0;
        }

        if (SimState.get("carbondioxide") == LOW) {
            CO2 = 0;
        }
        if (SimState.get("hydrogen") == LOW) {
            CO2 = 0;
        }
        if (SimState.get("hydrogen") == HIGH) {
            CO2 = 1;
            potableWater = 0;
        }
        if (SimState.get("oxygen") == LOW) {
            potableWater = 1;
        }
        if (SimState.get("oxygen") == HIGH) {
            potableWater = 0;
        }
        if (SimState.get("carbondioxide") == HIGH) {
            CO2 = 1;
        }

        myLogger.debug("CRS: " + CO2 + " OGS: " + potableWater + " Dirty Water: "
                + dirtyWater + " Grey Water: " + greyWater);
        myAction = new ActionMap(new int[] { potableWater, dirtyWater, greyWater });
        return myAction;

    }

    private void doPlants() {
        float overflow, new_overflow;
        float cropacres;
        GenericSensor harvestSensor;
        GenericSensor currentSensor;
        PlantingActuator currentActuator;
        HarvestingActuator harvestActuator;
        StoreOverflowSensor co2OverflowSensor, o2OverflowSensor;

        currentActuator = PlantingActuatorHelper.narrow((myBioHolder
                .getActuatorAttachedTo(myBioHolder.thePlantingActuators,
                        myBiomassRS)));
        harvestActuator = HarvestingActuatorHelper.narrow((myBioHolder
                .getActuatorAttachedTo(myBioHolder.theHarvestingActuators,
                        myBiomassRS)));

        co2OverflowSensor = (StoreOverflowSensor) myBioHolder
                .getSensorAttachedTo(myBioHolder.theStoreOverflowSensors,
                        myCO2Store);
        o2OverflowSensor = (StoreOverflowSensor) myBioHolder
                .getSensorAttachedTo(myBioHolder.theStoreOverflowSensors,
                        myO2Store);

        int i;
        int num = myBioHolder.theHarvestSensors.size();

        for (i = 0; i < num; i++) {
            currentSensor = (HarvestSensor) myBioHolder.theHarvestSensors
                    .get(i);
            currentActuator = (PlantingActuator) myBioHolder.thePlantingActuators
                    .get(i);

            if (currentSensor.getValue() == 1f) {
                myLogger.debug(" Harvest Sensor " + currentSensor.getValue());
                myLogger.debug(" CO2 Tank Overflow: "
                        + co2OverflowSensor.getValue() + " O2 Tank Overflow: "
                        + o2OverflowSensor.getValue());
                myBiomassRS.getShelf(i).harvest();
                cropacres = myBiomassRS.getShelf(i).getCropAreaTotal();
                myLogger
                        .debug("Planting "
                                + cropacres
                                + " m^2."
                                + ((Shelf) myBiomassRS.getShelf(i))
                                        .getCropTypeString());
                currentActuator.setPlantType(myBiomassRS.getShelf(i)
                        .getCropType());
                currentActuator.setValue(cropacres);
            }
        }
    }

}

