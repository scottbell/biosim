package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.air.CO2Consumer;

public class CO2InFlowRateActuator extends GenericActuator {
    private CO2Consumer myConsumer;

    private int myIndex;

    public CO2InFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getCO2ConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(CO2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getCO2ConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public IBioModule getOutputModule() {
        return (myConsumer);
    }

    public CO2Consumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getCO2ConsumerDefinition().getMaxFlowRate(myIndex);
    }
}