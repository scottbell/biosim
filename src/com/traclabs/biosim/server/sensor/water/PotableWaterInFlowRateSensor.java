package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumer;

public class PotableWaterInFlowRateSensor extends GenericSensor
         {
    private PotableWaterConsumer myConsumer;

    private int myIndex;

    public PotableWaterInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getPotableWaterConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(PotableWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public PotableWaterConsumer getInput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public IBioModule getInputModule() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getPotableWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}