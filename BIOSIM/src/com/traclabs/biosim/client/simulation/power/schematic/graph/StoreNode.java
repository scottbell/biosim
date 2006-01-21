package com.traclabs.biosim.client.simulation.power.schematic.graph;

import com.traclabs.biosim.server.sensor.framework.StoreLevelSensorImpl;



public abstract class StoreNode extends PassiveNode {
    private StoreLevelSensorImpl myLevelSensor;
    /**
     * 
     */
    public void addSensor() {
        myLevelSensor = new StoreLevelSensorImpl(0, getSimBioModule().getModuleName() + "LevelSensor");
    }

    /**
     * 
     */
    public void removeSensor() {
        myLevelSensor = null;
    }
    
    public boolean isSensed(){
        return (myLevelSensor != null);
    }
    
    public StoreLevelSensorImpl getSensorImpl(){
        return myLevelSensor;
    }
}
