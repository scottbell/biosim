package com.traclabs.biosim.server.sensor.food;


public class PlantDeathSensor extends ShelfSensor implements
        PlantDeathSensorOperations {
    public PlantDeathSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        boolean preFilteredBooleanValue = getInput().isDead();
        if (preFilteredBooleanValue)
            myValue = getStochasticFilter().randomFilter(1);
        else
            myValue = getStochasticFilter().randomFilter(0);
    }

    public float getMax() {
        return 1f;
    }

    protected void notifyListeners() {
        //does nothing now
    }

}