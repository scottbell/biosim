package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.CO2Producer;

public class CO2OutFlowRateSensor extends GenericSensor {
    private CO2Producer myProducer;

    private int myIndex;

    public CO2OutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCO2ProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }


    public void setInput(CO2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getCO2ProducerDefinition().getMaxFlowRate(myIndex);
    }

    public CO2Producer getInput() {
        return myProducer;
    }

    public IBioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}