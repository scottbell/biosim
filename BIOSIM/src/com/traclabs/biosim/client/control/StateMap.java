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
    private Map myMap;

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
        myMap = new TreeMap();
        myBioHolder = BioHolderInitializer.getBioHolder();
        myLogger.debug(BioHolderInitializer.getID() + "");

        myDirtyWaterStore = (DirtyWaterStore) myBioHolder.theDirtyWaterStores
                .get(0);
        myPotableWaterStore = (PotableWaterStore) myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = (GreyWaterStore) myBioHolder.theGreyWaterStores
                .get(0);

        myO2Store = (O2Store) myBioHolder.theO2Stores.get(0);

        stateSources = new GenericSensor[4];
        stateSources[0] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theDirtyWaterStoreLevelSensors, myDirtyWaterStore);
        stateSources[1] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theGreyWaterStoreLevelSensors, myGreyWaterStore);
        stateSources[2] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.thePotableWaterStoreLevelSensors,
                myPotableWaterStore);
        stateSources[3] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theO2StoreLevelSensors, myO2Store);

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
            currentSensor = (GenericSensor) (stateSources[i]);
            myMap.put(stateNames[i], new Float(currentSensor.getValue()));
        }

    }

    public float getStateValue(String name) {
        return ((Float) myMap.get(name)).floatValue();
    }

}

