package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.H2Consumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class H2InFlowRateActuatorImpl extends GenericActuatorImpl implements
        H2InFlowRateActuatorOperations {
    private H2Consumer myConsumer;

    private int myIndex;

    public H2InFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getH2ConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(H2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getH2ConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public H2Consumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getH2ConsumerDefinition().getMaxFlowRate(myIndex);
    }
}