package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.environment.AirConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;

public class AirInFlowRateSensor extends GenericSensor implements
        AirInFlowRateSensorOperations {
    private AirConsumer myConsumer;

    private int myIndex;

    public AirInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getAirConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }

    public float getMax() {
        return myConsumer.getAirConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public void setInput(AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public AirConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}