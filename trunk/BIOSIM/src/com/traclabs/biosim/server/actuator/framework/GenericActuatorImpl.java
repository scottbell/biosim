package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.server.framework.BioModuleImpl;

public abstract class GenericActuatorImpl extends BioModuleImpl implements
        GenericActuatorOperations {
    protected float myValue;

    protected boolean newValue = false;

    public GenericActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    public void setValue(float pValue) {
        myValue = pValue;
        newValue = true;
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
        if (newValue) {
            processData();
            newValue = false;
        }
    }

    protected abstract void processData();

    public abstract BioModule getOutputModule();

    public void log() {
        /*
         * LogNode outputNodeHead = myLog.addChild("output"); outputNode =
         * outputNodeHead.addChild(""+getOutputModule().getModuleName());
         * LogNode valueNodeHead = myLog.addChild("value");
         */
    }
}