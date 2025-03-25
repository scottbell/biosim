package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.O2Producer;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;

public class O2OutFlowRateSensor extends GenericSensor implements
        O2OutFlowRateSensorOperations {
    private O2Producer myProducer;

    private int myIndex;

    public O2OutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getO2ProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
		myLogger.debug("sensor value = "+myValue);
    }

    public void setInput(O2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getO2ProducerDefinition().getMaxFlowRate(myIndex);
    }

    public O2Producer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}