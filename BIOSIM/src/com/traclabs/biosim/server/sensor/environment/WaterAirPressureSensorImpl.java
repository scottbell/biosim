package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.sensor.environment.WaterAirPressureSensorOperations;

public class WaterAirPressureSensorImpl extends EnvironmentSensorImpl implements
        WaterAirPressureSensorOperations {
    public WaterAirPressureSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterPressure();
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }

    public float getMax() {
        return 1f;
    }
}