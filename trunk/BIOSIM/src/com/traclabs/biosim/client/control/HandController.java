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
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.air.OGS;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterRS;

/**
 * @author Theresa Klein
 * @author Scott Bell (modified original code)
 */

public class HandController {
    //feedback loop sttuff
    private float levelToKeepO2At = 0.20f;

    private float CrewCO2Level = 0.0012f;

    private float CrewH2OLevel = 0.01f;

    private float desiredAirPressure = 101f;

    private float crewO2integral = 0f;

    private final static String TAB = "\t";

    // hand controller stuff;

    private StateMap continuousState;

    private ActionMap myActionMap;

    private Map classifiedState;

    private Map thresholdMap = new TreeMap();

    private BioDriver myBioDriver;

    private BioHolder myBioHolder;

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

    public static String[] stateNames = { "dirtywater", "greywater",
            "potablewater", "oxygen" };

    public static String[] actuatorNames = { "OGSpotable", "waterRSdirty",
            "waterRSgrey" };

    private File outFile;

    private FileWriter fw;

    private PrintWriter pw;

    private Logger myLogger;

    private DecimalFormat numFormat;

    public static final Integer HIGH = new Integer(0);

    public static final Integer LOW = new Integer(1);

    public static final Integer NORMAL = new Integer(2);

    private GenericSensor myO2AirConcentrationSensor;

    private Injector myInjector;

    private GenericActuator myO2AirStoreInInjectorAcutator;
    
    private GenericActuator myO2AirEnvironmentOutInjectorAcutator;
    
    private float myO2AirStoreInInjectorMax;

    private float myO2AirEnvironmentOutInjectorMax;

    public HandController() {
        myLogger = Logger.getLogger(this.getClass());
        numFormat = new DecimalFormat("#,##0.0;(#)");
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
        continuousState = new StateMap();
        myActionMap = new ActionMap();
        
        myO2AirEnvironmentOutInjectorMax = myO2AirEnvironmentOutInjectorAcutator.getMax();
        myO2AirStoreInInjectorMax = myO2AirStoreInInjectorAcutator.getMax();
    }

    public static void main(String[] args) {
        HandController myController = new HandController();
        myController.runSim();
    }

    private void collectReferences() {
        myBioHolder = BioHolderInitializer.getBioHolder();
        myBioDriver = myBioHolder.theBioDriver;

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
        myPowerStore = (PowerStore) myBioHolder.thePowerStores.get(0);

        myO2AirStoreInInjectorAcutator = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theO2AirStoreInFlowRateActuators,
                        myInjector));

        myO2AirEnvironmentOutInjectorAcutator = (GenericActuator) (myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theO2AirEnvironmentOutFlowRateActuators,
                        myInjector));
        
        myO2AirConcentrationSensor = (GenericSensor) (myBioHolder
                .getSensorAttachedTo(myBioHolder.theO2AirConcentrationSensors,
                        myCrewEnvironment));

    }

    public void runSim() {
        myBioDriver.setPauseSimulation(true);
        myBioDriver.startSimulation();
        myLogger.info("Controller starting run");
        while (!myBioDriver.isDone())
            stepSim();
    }

    public void stepSim() {
        if (((myBioDriver.getTicks()) % (CORE_PERIOD_MULT * ATMOSPHERIC_PERIOD)) == 0) {
            myLogger.debug(myBioDriver.getTicks() + "");
            continuousState.updateState();
            classifiedState = classifyState(continuousState);
            myActionMap.performAction(classifiedState);
        }
        doInjectors();
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

        Map dirtyWaterSubMap = new TreeMap();
        dirtyWaterSubMap.put(LOW, new Integer(dirtyWaterLowLevel));
        dirtyWaterSubMap.put(HIGH, new Integer(dirtyWaterHighLevel));
        thresholdMap.put("dirtywater", dirtyWaterSubMap);

        Map greyWaterSubMap = new TreeMap();
        greyWaterSubMap.put(LOW, new Integer(greyWaterLowLevel));
        greyWaterSubMap.put(HIGH, new Integer(greyWaterHighLevel));
        thresholdMap.put("greywater", greyWaterSubMap);

        Map oxygenSubMap = new TreeMap();
        oxygenSubMap.put(LOW, new Integer(O2StoreLowLevel));
        oxygenSubMap.put(HIGH, new Integer(O2StoreHighLevel));
        thresholdMap.put("oxygen", oxygenSubMap);

        Map potableWaterSubMap = new TreeMap();
        potableWaterSubMap.put(LOW, new Integer(potableWaterLowLevel));
        potableWaterSubMap.put(HIGH, new Integer(potableWaterHighLevel));
        thresholdMap.put("potablewater", potableWaterSubMap);
    }

    public Map classifyState(StateMap instate) {
        Map state = new TreeMap();

        Map thisSet;
        StringBuffer fileoutput;

        fileoutput = new StringBuffer(myBioDriver.getTicks());
        fileoutput.append(TAB);

        for (int i = 0; i < stateNames.length; i++) {

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
        float crewAirPressure = myCrewEnvironment.getTotalPressure();
        //crew O2 feedback control
        float crewO2p = 100f;
        float crewO2i = 5f;
        float crewO2 = myO2AirConcentrationSensor.getValue();
        float delta = levelToKeepO2At - crewO2;
        crewO2integral += delta;
        float signal = (delta * crewO2p + crewO2i * crewO2integral) + 2;
        float valueToSet = Math.min(myO2AirStoreInInjectorMax, signal);
        myO2AirStoreInInjectorAcutator.setValue(valueToSet);
        valueToSet = Math.min(myO2AirStoreInInjectorMax, signal);
        myO2AirEnvironmentOutInjectorAcutator.setValue(valueToSet);
    }
    
    /**
     * @param pO2AirEnvironmentOutInjectorMax The myO2AirEnvironmentOutInjectorMax to set.
     */
    public void setO2AirEnvironmentOutInjectorMax(
            float pO2AirEnvironmentOutInjectorMax) {
        myO2AirEnvironmentOutInjectorMax = pO2AirEnvironmentOutInjectorMax;
    }
    /**
     * @param pO2AirStoreInInjectorMax The myO2AirStoreInInjectorMax to set.
     */
    public void setO2AirStoreInInjectorMax(float pO2AirStoreInInjectorMax) {
        myO2AirStoreInInjectorMax = pO2AirStoreInInjectorMax;
    }
    
    /**
     * @param myOGSPotableWaterInFlowRateMax
     *            The myOGSPotableWaterInFlowRateMax to set.
     */
    public void setOGSPotableWaterInFlowRateMax(
            float pOGSPotableWaterInFlowRateMax) {
        myActionMap.setOGSPotableWaterInFlowRateMax(pOGSPotableWaterInFlowRateMax);
    }

    /**
     * @param myOGSPowerInFlowRateMax
     *            The myOGSPowerInFlowRateMax to set.
     */
    public void setOGSPowerInFlowRateMax(float pOGSPowerInFlowRateMax) {
        myActionMap.setOGSPowerInFlowRateMax(pOGSPowerInFlowRateMax);
    }

    /**
     * @param myWaterRSDirtyWaterInFlowRateMax
     *            The myWaterRSDirtyWaterInFlowRateMax to set.
     */
    public void setWaterRSDirtyWaterInFlowRateMax(
            float pWaterRSDirtyWaterInFlowRateMax) {
        myActionMap.setWaterRSDirtyWaterInFlowRateMax(pWaterRSDirtyWaterInFlowRateMax);
    }

    /**
     * @param myWaterRSGreyWaterInFlowRateMax
     *            The myWaterRSGreyWaterInFlowRateMax to set.
     */
    public void setWaterRSGreyWaterInFlowRateMax(
            float pWaterRSGreyWaterInFlowRateMax) {
        myActionMap.setWaterRSGreyWaterInFlowRateMax(pWaterRSGreyWaterInFlowRateMax);
    }

    /**
     * @param myWaterRSPowerInFlowRateMax
     *            The myWaterRSPowerInFlowRateMax to set.
     */
    public void setWaterRSPowerInFlowRateMax(float pWaterRSPowerInFlowRateMax) {
        myActionMap.setWaterRSPowerInFlowRateMax(pWaterRSPowerInFlowRateMax);
    }

}

