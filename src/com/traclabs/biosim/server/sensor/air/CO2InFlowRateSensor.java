package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.CO2Consumer;

public class CO2InFlowRateSensor extends GenericSensor {
    private CO2Consumer myConsumer;

    private int myIndex;

    public CO2InFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCO2ConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }


    public void setInput(CO2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getCO2ConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public CO2Consumer getInput() {
        return myConsumer;
    }

    public IBioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

}