package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.water.DirtyWaterConsumer;

public class DirtyWaterInFlowRateSensor extends GenericSensor {
    private DirtyWaterConsumer myConsumer;

    private int myIndex;

    public DirtyWaterInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getDirtyWaterConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(DirtyWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public DirtyWaterConsumer getInput() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getDirtyWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}