package com.traclabs.biosim.server.actuator.waste;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumer;

public class DryWasteInFlowRateActuator extends GenericActuator
         {
    private DryWasteConsumer myConsumer;

    private int myIndex;

    public DryWasteInFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getDryWasteConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(DryWasteConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getDryWasteConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public DryWasteConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getDryWasteConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}