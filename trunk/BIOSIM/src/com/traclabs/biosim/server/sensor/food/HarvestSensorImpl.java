package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.sensor.food.HarvestSensorOperations;

public class HarvestSensorImpl extends ShelfSensorImpl implements
        HarvestSensorOperations {
    public HarvestSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        boolean preFilteredBooleanValue = getInput().isReadyForHavest();
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