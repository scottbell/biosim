package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.server.framework.BioModuleImpl;

public abstract class GenericActuatorImpl extends BioModuleImpl implements
        GenericActuatorOperations {
    protected float myValue;

    protected boolean newValueSet = false;

    public GenericActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }
    
    protected void notifyListeners(){
    }

    public void setValue(float pValue) {
        if (myValue > getMin())
            myValue = Math.min(pValue, getMax());
        else
            myValue = getMin();
        myValue = pValue;
        newValueSet = true;
    }

    public abstract float getMax();

    public float getMin() {
        return 0f;
    }

    public float getValue() {
        return myValue;
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

    public abstract BioModule getOutputModule();

    public void log() {
        myLogger.debug(getModuleName() + ":\toutput=" + getValue());
    }
    
}