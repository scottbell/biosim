package com.traclabs.biosim.client.framework;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

//javac -classpath
// .:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/generated/client/classes
// StateMap.java

public class StateMap {
    private Map myMap;

    private BioHolder myBioHolder;

    private DirtyWaterStore myDirtyWaterStore;

    private PotableWaterStore myPotableWaterStore;

    private GreyWaterStore myGreyWaterStore;

    private O2Store myO2Store;

    private CO2Store myCO2Store;

    private H2Store myH2Store;

    public static float[] capacities;

    public GenericSensor[] stateSources;
    
    private Logger myLogger;

    public static String[] stateNames = { "carbondioxide", "dirtywater",
            "greywater", "hydrogen", "oxygen", "potablewater" };

    public StateMap() {
	myLogger = Logger.getLogger(this.getClass());
        myMap = new TreeMap();
        myBioHolder = BioHolderInitializer.getBioHolder();
        myLogger.info(BioHolderInitializer.getID() +"");

        myDirtyWaterStore = (DirtyWaterStore) myBioHolder.theDirtyWaterStores
                .get(0);
        myPotableWaterStore = (PotableWaterStore) myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = (GreyWaterStore) myBioHolder.theGreyWaterStores
                .get(0);

        myO2Store = (O2Store) myBioHolder.theO2Stores.get(0);
        myCO2Store = (CO2Store) myBioHolder.theCO2Stores.get(0);
        myH2Store = (H2Store) myBioHolder.theH2Stores.get(0);

        stateSources = new GenericSensor[6];
        stateSources[0] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theCO2StoreLevelSensors, myCO2Store);
        stateSources[1] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theDirtyWaterStoreLevelSensors, myDirtyWaterStore);
        stateSources[2] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theGreyWaterStoreLevelSensors, myGreyWaterStore);
        stateSources[3] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theH2StoreLevelSensors, myH2Store);
        stateSources[4] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.theO2StoreLevelSensors, myO2Store);
        stateSources[5] = (GenericSensor) myBioHolder.getSensorAttachedTo(
                myBioHolder.thePotableWaterStoreLevelSensors,
                myPotableWaterStore);

        capacities = new float[6];
        capacities[0] = myCO2Store.getCapacity();
        capacities[1] = myDirtyWaterStore.getCapacity();
        capacities[2] = myDirtyWaterStore.getCapacity();
        capacities[3] = myH2Store.getCapacity();
        capacities[4] = myO2Store.getCapacity();
        capacities[5] = myPotableWaterStore.getCapacity();
    }

    public void updateState() {
        // gathers continuous valued state variables
        int i;
        GenericSensor currentSensor;

        for (i = 0; i < stateSources.length; i++) {
            currentSensor = (GenericSensor) (stateSources[i]);
            myMap.put(stateNames[i], new Float(currentSensor.getValue()));
        }

    }

    public void setStatefromVector(float[] outputvector) {
        int i;
        for (i = 0; i < stateNames.length; i++)
            myMap
                    .put(stateNames[i], new Float(capacities[i]
                            * outputvector[i]));
    }

    public Map getState() {
        return myMap;
    }

    public GenericSensor[] getStateSources() {
        return stateSources;
    }

    public float getStateValue(String name) {
        return ((Float) myMap.get(name)).floatValue();
    }

    public int size() {
        return stateSources.length;
    }
    
    public void printMe() {
        myLogger.info(myMap);
    }

    public Map getMap() {
        return myMap;
    }

}

