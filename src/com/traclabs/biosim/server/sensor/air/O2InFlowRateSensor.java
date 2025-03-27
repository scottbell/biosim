package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.O2Consumer;

public class O2InFlowRateSensor extends GenericSensor {
    private O2Consumer myConsumer;

    private int myIndex;

    public O2InFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getO2ConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(O2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public O2Consumer getInput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public IBioModule getInputModule() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getO2ConsumerDefinition().getMaxFlowRate(myIndex);
    }

}