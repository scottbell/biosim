package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.MethaneConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuator;

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

    public BioModule getOutputModule() {
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