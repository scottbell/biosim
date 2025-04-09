package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.air.O2Producer;

public class O2OutFlowRateActuator extends GenericActuator {
    private O2Producer myProducer;

    private int myIndex;

    public O2OutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getO2ProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(O2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getO2ProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public IBioModule getOutputModule() {
        return myProducer;
    }

    public O2Producer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getO2ProducerDefinition().getMaxFlowRate(myIndex);
    }
}