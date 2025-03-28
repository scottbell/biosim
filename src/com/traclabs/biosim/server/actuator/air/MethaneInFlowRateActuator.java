package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.air.MethaneConsumer;

public class MethaneInFlowRateActuator extends GenericActuator {
    private MethaneConsumer myConsumer;

    private int myIndex;

    public MethaneInFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getMethaneConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(MethaneConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getMethaneConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public IBioModule getOutputModule() {
        return myConsumer;
    }

    public MethaneConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getMethaneConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}