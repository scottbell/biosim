package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.O2AirStoreInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.O2AirConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class O2AirStoreInFlowRateActuatorImpl extends GenericActuatorImpl
        implements O2AirStoreInFlowRateActuatorOperations {
    private O2AirConsumer myConsumer;

    private int myIndex;

    public O2AirStoreInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().setO2AirStoreInputDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(O2AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getO2AirEnvironmentInputMaxFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public O2AirConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}