package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorOperations;

public class BiomassStoreWaterContentSensorImpl extends BiomassStoreSensorImpl
        implements BiomassStoreWaterContentSensorOperations {
    public BiomassStoreWaterContentSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().calculateWaterContentInStore();
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    public float getMax() {
        return getInput().getCurrentCapacity();
    }

    protected void notifyListeners() {
        //does nothing now
    }

}