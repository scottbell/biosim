package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumer;

public class GreyWaterInFlowRateActuator extends GenericActuator
         {
    private GreyWaterConsumer myConsumer;

    private int myIndex;

    public GreyWaterInFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getGreyWaterConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(GreyWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getGreyWaterConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public GreyWaterConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getGreyWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}