package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.O2Consumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class O2InFlowRateActuatorImpl extends GenericActuatorImpl implements
        O2InFlowRateActuatorOperations {
    private O2Consumer myConsumer;

    private int myIndex;

    public O2InFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getO2ConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(O2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getO2ConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
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