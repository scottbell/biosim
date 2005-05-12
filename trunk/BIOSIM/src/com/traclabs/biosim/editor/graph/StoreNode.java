package com.traclabs.biosim.editor.graph;

import com.traclabs.biosim.server.sensor.framework.StoreLevelSensorImpl;



public abstract class StoreNode extends PassiveNode {
    private StoreLevelSensorImpl myLevelSensor;
    /**
     * 
     */
    public void addSensor() {
        myLevelSensor = new StoreLevelSensorImpl(0, getSimBioModuleImpl().getModuleName() + "LevelSensor");
    }

    /**
     * 
     */
    public void removeSensor() {
        myLevelSensor = null;
    }
}
