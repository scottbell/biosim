package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.O2AirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class O2AirEnvironmentOutFlowRateActuatorImpl extends
        GenericActuatorImpl implements
        O2AirEnvironmentOutFlowRateActuatorOperations {
    private O2AirProducer myProducer;

    private int myIndex;

    public O2AirEnvironmentOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().setO2AirEnvironmentOutputDesiredFlowRate(myFilteredValue,
                myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(O2AirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public O2AirProducer getOutput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getO2AirEnvironmentOutputMaxFlowRate(myIndex);
    }

    public int getIndex() {
        return myIndex;
    }
}