package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.food.BiomassConsumer;

public class BiomassInFlowRateActuator extends GenericActuator {
    private BiomassConsumer myConsumer;

    private int myIndex;

    public BiomassInFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getBiomassConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(BiomassConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getBiomassConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public IBioModule getOutputModule() {
        return myConsumer;
    }

    public BiomassConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getBiomassConsumerDefinition()
                .getMaxFlowRate(myIndex);
    }
}