package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.O2Producer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class O2OutFlowRateActuatorImpl extends GenericActuatorImpl implements
        O2OutFlowRateActuatorOperations {
    private O2Producer myProducer;

    private int myIndex;

    public O2OutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().setO2OutputDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(O2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public O2Producer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getO2OutputMaxFlowRate(myIndex);
    }
}