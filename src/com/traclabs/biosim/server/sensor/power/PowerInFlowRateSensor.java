package com.traclabs.biosim.server.sensor.power;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;

public class PowerInFlowRateSensor extends GenericSensor {
    private PowerConsumer myConsumer;

    private int myIndex;

    public PowerInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getPowerConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(PowerConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getPowerConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public PowerConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}