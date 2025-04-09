package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.food.BiomassConsumer;

public class BiomassInFlowRateSensor extends GenericSensor {
    private BiomassConsumer myConsumer;

    private int myIndex;

    public BiomassInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getBiomassConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }


    public void setInput(BiomassConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getBiomassConsumerDefinition()
                .getMaxFlowRate(myIndex);
    }

    public BiomassConsumer getInput() {
        return myConsumer;
    }

    public IBioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}