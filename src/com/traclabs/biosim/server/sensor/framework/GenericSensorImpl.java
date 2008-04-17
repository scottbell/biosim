package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;

public abstract class GenericSensorImpl extends BioModuleImpl implements
        GenericSensorOperations {
    protected float myValue;

    public GenericSensorImpl(int pID, String pName) {
        super(pID, pName);
        if (isBionetEnabled()){
        	//register this sensor
        }
    }

    protected abstract void gatherData();

    protected void notifyListeners(){
    }

    public float getValue() {
        return myValue;
    }

    public abstract float getMax();

    public float getMin() {
        return 0f;
    }

    public void tick() {
        super.tick();
        try {
            gatherData();
            notifyListeners();
        } catch (Exception e) {
            myLogger.error(getModuleName() + " had an exception: " + e);
            e.printStackTrace();
        }
    }

    public abstract BioModule getInputModule();

    public void log() {
        myLogger.debug("value=" + getValue());
    }
}