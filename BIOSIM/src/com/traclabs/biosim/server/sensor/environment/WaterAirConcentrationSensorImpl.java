package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.sensor.environment.WaterAirConcentrationSensorOperations;

public class WaterAirConcentrationSensorImpl extends EnvironmentSensorImpl
        implements WaterAirConcentrationSensorOperations {
    public WaterAirConcentrationSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterMoles()
                / getInput().getTotalMoles();
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
    }

    public float getMax() {
        return 1f;
    }

}