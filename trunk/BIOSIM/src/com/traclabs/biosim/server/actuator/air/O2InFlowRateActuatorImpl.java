package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.O2Consumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class O2InFlowRateActuatorImpl extends GenericActuatorImpl implements
        O2InFlowRateActuatorOperations {
    private O2Consumer myConsumer;

    private int myIndex;

    public O2InFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getO2ConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(O2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public O2Consumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getO2ConsumerDefinition().getMaxFlowRate(myIndex);
    }
}