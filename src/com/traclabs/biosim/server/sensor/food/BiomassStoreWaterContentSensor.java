package com.traclabs.biosim.server.sensor.food;


public class BiomassStoreWaterContentSensor extends BiomassStoreSensor {
    public BiomassStoreWaterContentSensor(int pID, String pName) {
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