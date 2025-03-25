package com.traclabs.biosim.server.sensor.waste;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;

public class DryWasteInFlowRateSensor extends GenericSensor implements
        DryWasteInFlowRateSensorOperations {
    private DryWasteConsumer myConsumer;

    private int myIndex;

    public DryWasteInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getDryWasteConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(DryWasteConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public DryWasteConsumer getInput() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getDryWasteConsumerDefinition().getMaxFlowRate(
                myIndex);
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}