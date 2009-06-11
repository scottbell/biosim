package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.util.MathUtils;

public abstract class GenericSensorImpl extends BioModuleImpl implements
        GenericSensorOperations {
    protected float myValue;

    public GenericSensorImpl(int pID, String pName) {
        super(pID, pName);
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
        gatherData();
        super.tick();
        notifyListeners();
    }

    public abstract BioModule getInputModule();

    public void log() {
        myLogger.debug("value=" + getValue());
    }
    
    protected void performMalfunctions() {
    	if (!myMalfunctions.isEmpty()){
            Double noisyValue = MathUtils.gaussian(myValue, 100);
    		myValue = noisyValue.floatValue();
    	}
    }
}