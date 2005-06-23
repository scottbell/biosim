package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.O2AirStoreInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.O2AirConsumer;
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
        getOutput().getO2AirConsumerDefinition().setStoreDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(O2AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getO2AirConsumerDefinition().getStoreMaxFlowRate(
                myIndex);
    }

    public BioModule getOutputModule() {
        return (myConsumer);
    }

    public O2AirConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}