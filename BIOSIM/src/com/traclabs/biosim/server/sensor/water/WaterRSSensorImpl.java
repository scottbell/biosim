package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.WaterRSSensorOperations;
import com.traclabs.biosim.idl.simulation.water.WaterRS;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class WaterRSSensorImpl extends GenericSensorImpl implements
        WaterRSSensorOperations {
    protected WaterRS myWaterRS;

    public WaterRSSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(WaterRS source) {
        myWaterRS = source;
    }

    public WaterRS getInput() {
        return myWaterRS;
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}