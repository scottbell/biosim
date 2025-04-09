package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.environment.AirConsumer;

public class AirInFlowRateActuator extends GenericActuator {
    private AirConsumer myConsumer;

    private int myIndex;

    public AirInFlowRateActuator() {
        super(0, "Unnamed AirInFlowRateActuator");
    }

    public AirInFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getAirConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getAirConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public AirConsumer getOutput() {
        return myConsumer;
    }

    public IBioModule getOutputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getAirConsumerDefinition().getMaxFlowRate(myIndex);
    }
}