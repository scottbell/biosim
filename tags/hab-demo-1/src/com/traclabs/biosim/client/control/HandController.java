package com.traclabs.biosim.client.control;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

/**
 * @author Theresa Klein
 * @author Scott Bell (modified original code)
 */

public class HandController implements BiosimController{
    //feedback loop sttuff
    private float levelToKeepO2At = 0.20f;

    private float levelToKeepCO2At = 0.00111f;

    private float crewO2integral = 0f;

    private float crewCO2integral = 0f;

    private final static String TAB = "\t";

    // hand controller stuff;

    private StateMap continuousState;

    private ActionMap myActionMap;

    private Map classifiedState;

    private Map<String, Map> thresholdMap = new TreeMap<String, Map>();

    private BioDriver myBioDriver;

    private BioHolder myBioHolder;

    private SimEnvironment myCrewEnvironment;

    private DirtyWaterStore myDirtyWaterStore;

    private GreyWaterStore myGreyWaterStore;

    private PotableWaterStore myPotableWaterStore;

    private O2Store myO2Store;

    private static int ATMOSPHERIC_PERIOD = 2;

    private static int CORE_PERIOD_MULT = 5;

    public static String[] stateNames = { "dirtywater", "greywater",
            "potablewater", "oxygen" };

    public static String[] actuatorNames = { "OGSpotable", "waterRSdirty",
            "waterRSgrey" };

    private Logger myLogger;

    public static final Integer HIGH = new Integer(0);

    public static final Integer LOW = new Integer(1);

    public static final Integer NORMAL = new Integer(2);

    private GenericSensor myO2AirConcentrationSensor;

    private GenericSensor myCO2AirConcentrationSensor;

    private Injector myO2Injector;

    private Injector myCO2Injector;

    private GenericActuator myO2InInjectorAcutator;

    private GenericActuator myO2AirEnvironmentOutInjectorAcutator;

    private GenericActuator myCO2AirStoreInInjectorAcutator;

    private GenericActuator myCO2AirEnvironmentOutInjectorAcutator;

    private float myO2AirStoreInInjectorMax;

    private float myCO2AirStoreInInjectorMax;

    public HandController() {
        myLogger = Logger.getLogger(this.getClass());
        setThresholds();
        continuousState = new StateMap();
        myActionMap = new ActionMap();

        myO2AirStoreInInjectorMax = myO2InInjectorAcutator.getMax();

        if (myCO2Injector != null) {
            myCO2AirStoreInInjectorMax = myCO2AirStoreInInjectorAcutator
                    .getMax();
        }
    }

    public static void main(String[] args) {
        HandController myController = new HandController();
        myController.collectReferences();
        myController.runSim();
    }

    public void collectReferences() {
        myBioHolder = BioHolderInitializer.getBioHolder();
        myBioDriver = myBioHolder.theBioDriver;

        myO2Injector = myBioHolder.theInjectors.get(1);

        if (myBioHolder.theInjectors.size() >= 3) {
            myCO2Injector = myBioHolder.theInjectors.get(2);
        }

        myDirtyWaterStore = myBioHolder.theDirtyWaterStores
                .get(0);
        myPotableWaterStore = myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = myBioHolder.theGreyWaterStores
                .get(0);

        myO2Store = myBioHolder.theO2Stores.get(0);

        myCrewEnvironment = myBioHolder.theSimEnvironments
                .get(0);

        myO2InInjectorAcutator = (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theO2InFlowRateActuators,
                        myO2Injector));

        myO2AirEnvironmentOutInjectorAcutator = (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theO2OutFlowRateActuators,
                        myO2Injector));

        myO2AirConcentrationSensor = (myBioHolder
                .getSensorAttachedTo(myBioHolder.theGasConcentrationSensors,
                        myCrewEnvironment.getO2Store()));

        if (myCO2Injector != null) {
            myCO2AirStoreInInjectorAcutator = (myBioHolder
                    .getActuatorAttachedTo(
                            myBioHolder.theCO2InFlowRateActuators,
                            myCO2Injector));

            myCO2AirEnvironmentOutInjectorAcutator = (myBioHolder
                    .getActuatorAttachedTo(
                            myBioHolder.theCO2OutFlowRateActuators,
                            myCO2Injector));

            myCO2AirConcentrationSensor = (myBioHolder
                    .getSensorAttachedTo(
                            myBioHolder.theGasConcentrationSensors,
                            myCrewEnvironment.getCO2Store()));
        }
    }

    public void runSim() {
        myBioDriver.setPauseSimulation(true);
        myBioDriver.startSimulation();
        myLogger.info("Controller starting run");
        while (!myBioDriver.isDone())
            stepSim();
        myLogger.info("Controller ended on tick "+myBioDriver.getTicks());
    }

    public void stepSim() {
        if (((myBioDriver.getTicks()) % (CORE_PERIOD_MULT * ATMOSPHERIC_PERIOD)) == 0) {
            myLogger.debug(myBioDriver.getTicks() + "");
            continuousState.updateState();
            classifiedState = classifyState(continuousState);
            myActionMap.performAction(classifiedState);
        }
        doO2Injector();
        if (myCO2Injector != null)
            doCO2Injector();
        //advancing the sim 1 tick
        myBioDriver.advanceOneTick();
    }

    public void setThresholds() {
        // sets up the threshold map variable
        int dirtyWaterHighLevel = (int) myDirtyWaterStore.getCurrentCapacity();
        int dirtyWaterLowLevel = dirtyWaterHighLevel / 3;
        int greyWaterHighLevel = (int) myGreyWaterStore.getCurrentCapacity();
        int greyWaterLowLevel = greyWaterHighLevel / 3;
        int potableWaterHighLevel = (int) myPotableWaterStore
                .getCurrentCapacity();
        int potableWaterLowLevel = potableWaterHighLevel / 3;
        int O2StoreHighLevel = (int) myO2Store.getCurrentCapacity();
        int O2StoreLowLevel = O2StoreHighLevel / 3;

        Map<Integer, Integer> dirtyWaterSubMap = new TreeMap<Integer, Integer>();
        dirtyWaterSubMap.put(LOW, new Integer(dirtyWaterLowLevel));
        dirtyWaterSubMap.put(HIGH, new Integer(dirtyWaterHighLevel));
        thresholdMap.put("dirtywater", dirtyWaterSubMap);

        Map<Integer, Integer> greyWaterSubMap = new TreeMap<Integer, Integer>();
        greyWaterSubMap.put(LOW, new Integer(greyWaterLowLevel));
        greyWaterSubMap.put(HIGH, new Integer(greyWaterHighLevel));
        thresholdMap.put("greywater", greyWaterSubMap);

        Map<Integer, Integer> oxygenSubMap = new TreeMap<Integer, Integer>();
        oxygenSubMap.put(LOW, new Integer(O2StoreLowLevel));
        oxygenSubMap.put(HIGH, new Integer(O2StoreHighLevel));
        thresholdMap.put("oxygen", oxygenSubMap);

        Map<Integer, Integer> potableWaterSubMap = new TreeMap<Integer, Integer>();
        potableWaterSubMap.put(LOW, new Integer(potableWaterLowLevel));
        potableWaterSubMap.put(HIGH, new Integer(potableWaterHighLevel));
        thresholdMap.put("potablewater", potableWaterSubMap);
    }

    public Map classifyState(StateMap instate) {
        Map<String, Integer> state = new TreeMap<String, Integer>();

        Map thisSet;
        StringBuffer fileoutput;

        fileoutput = new StringBuffer(myBioDriver.getTicks());
        fileoutput.append(TAB);

        for (int i = 0; i < stateNames.length; i++) {

            thisSet = thresholdMap.get(stateNames[i]);
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

    private void doO2Injector() {
        //crew O2 feedback control
        float crewO2p = 100f;
        float crewO2i = 5f;
        float crewO2 = myO2AirConcentrationSensor.getValue();
        float delta = levelToKeepO2At - crewO2;
        crewO2integral += delta;
        float signal = (delta * crewO2p + crewO2i * crewO2integral);
        float valueToSet = Math.min(myO2AirStoreInInjectorMax, signal);
        myLogger.debug("setting O2 injector to " + valueToSet);
        valueToSet = Math.min(myO2AirStoreInInjectorMax, signal);
        myO2InInjectorAcutator.setValue(valueToSet);
        myO2AirEnvironmentOutInjectorAcutator.setValue(valueToSet);
    }

    private void doCO2Injector() {
        //crew O2 feedback control
        float crewCO2p = 100f;
        float crewCO2i = 5f;
        float crewCO2 = myCO2AirConcentrationSensor.getValue();
        float delta = levelToKeepCO2At - crewCO2;
        crewCO2integral += delta;
        float signal = (delta * crewCO2p + crewCO2i * crewCO2integral);
        float valueToSet = Math.min(myCO2AirStoreInInjectorMax, signal);
        myLogger.debug("setting CO2 injector to " + valueToSet);
        valueToSet = Math.min(myCO2AirStoreInInjectorMax, signal);
        myCO2AirStoreInInjectorAcutator.setValue(valueToSet);
        myCO2AirEnvironmentOutInjectorAcutator.setValue(valueToSet);
    }

    /**
     * @param pO2AirStoreInInjectorMax
     *            The myO2AirStoreInInjectorMax to set.
     */
    public void setO2AirStoreInInjectorMax(float pO2AirStoreInInjectorMax) {
        myO2AirStoreInInjectorMax = pO2AirStoreInInjectorMax;
    }

    /**
     * @param pO2AirStoreInInjectorMax
     *            The myO2AirStoreInInjectorMax to set.
     */
    public void setCO2AirStoreInInjectorMax(float pCO2AirStoreInInjectorMax) {
        myCO2AirStoreInInjectorMax = pCO2AirStoreInInjectorMax;
    }

}

