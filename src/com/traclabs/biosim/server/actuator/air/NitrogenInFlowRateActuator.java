package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.NitrogenConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuator;

public class NitrogenInFlowRateActuator extends GenericActuator {
    private NitrogenConsumer myConsumer;

    private int myIndex;

    public NitrogenInFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getNitrogenConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(NitrogenConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getNitrogenConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public NitrogenConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getNitrogenConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}