package com.traclabs.biosim.client.control;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

public class StateMap {
    private Map<String, Float> myMap;

    private BioHolder myBioHolder;

    private DirtyWaterStore myDirtyWaterStore;

    private PotableWaterStore myPotableWaterStore;

    private GreyWaterStore myGreyWaterStore;

    private O2Store myO2Store;

    public static float[] capacities;

    public GenericSensor[] stateSources;

    private Logger myLogger;

    public static String[] stateNames = { "dirtywater", "greywater",
            "potablewater", "oxygen" };

    public StateMap() {
        myLogger = Logger.getLogger(this.getClass());
        myMap = new TreeMap<String, Float>();
        myBioHolder = BioHolderInitializer.getBioHolder();
        myLogger.debug(BioHolderInitializer.getID() + "");

        myDirtyWaterStore = myBioHolder.theDirtyWaterStores
                .get(0);
        myPotableWaterStore = myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = myBioHolder.theGreyWaterStores
                .get(0);

        myO2Store = myBioHolder.theO2Stores.get(0);

        stateSources = new GenericSensor[4];
        stateSources[0] = myBioHolder.getSensorAttachedTo(
                myBioHolder.theStoreLevelSensors, myDirtyWaterStore);
        stateSources[1] = myBioHolder.getSensorAttachedTo(
                myBioHolder.theStoreLevelSensors, myGreyWaterStore);
        stateSources[2] = myBioHolder.getSensorAttachedTo(
                myBioHolder.theStoreLevelSensors,
                myPotableWaterStore);
        stateSources[3] = myBioHolder.getSensorAttachedTo(
                myBioHolder.theStoreLevelSensors, myO2Store);

        capacities = new float[4];
        capacities[0] = myDirtyWaterStore.getCurrentCapacity();
        capacities[1] = myGreyWaterStore.getCurrentCapacity();
        capacities[2] = myPotableWaterStore.getCurrentCapacity();
        capacities[3] = myO2Store.getCurrentCapacity();
    }

    public void updateState() {
        // gathers continuous valued state variables
        GenericSensor currentSensor;

        for (int i = 0; i < stateSources.length; i++) {
            currentSensor = (stateSources[i]);
            myMap.put(stateNames[i], new Float(currentSensor.getValue()));
        }

    }

    public float getStateValue(String name) {
        return myMap.get(name).floatValue();
    }

}

