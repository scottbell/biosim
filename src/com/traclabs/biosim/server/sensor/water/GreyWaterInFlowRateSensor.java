package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumer;

public class GreyWaterInFlowRateSensor extends GenericSensor {
    private GreyWaterConsumer myConsumer;

    private int myIndex;

    public GreyWaterInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getGreyWaterConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(GreyWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public GreyWaterConsumer getInput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public IBioModule getInputModule() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getGreyWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}