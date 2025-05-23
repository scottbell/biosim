package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.framework.IBioModule;

public abstract class GenericActuator extends BioModule {
    protected float myValue;

    protected boolean newValueSet = false;

    public GenericActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void notifyListeners() {
    }

    public abstract float getMax();

    public float getMin() {
        return 0f;
    }

    public float getValue() {
        return myValue;
    }

    public void setValue(float pValue) {
        if (myValue > getMin())
            myValue = Math.min(pValue, getMax());
        else
            myValue = getMin();
        myValue = pValue;
        newValueSet = true;
    }

    public void tick() {
        super.tick();
        if (newValueSet) {
            processData();
            notifyListeners();
            newValueSet = false;
        }
    }

    protected abstract void processData();

    public abstract IBioModule getOutputModule();

    public void log() {
        myLogger.debug(getModuleName() + ":\toutput=" + getValue());
    }

}