package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.NitrogenConsumer;

public class NitrogenInFlowRateSensor extends GenericSensor {
    private NitrogenConsumer myConsumer;

    private int myIndex;

    public NitrogenInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getNitrogenConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(NitrogenConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getNitrogenConsumerDefinition().getMaxFlowRate(
                myIndex);
    }

    public NitrogenConsumer getInput() {
        return myConsumer;
    }

    public IBioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

}