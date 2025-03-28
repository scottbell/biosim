package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.air.CO2Producer;

public class CO2OutFlowRateActuator extends GenericActuator {
    private CO2Producer myProducer;

    private int myIndex;

    public CO2OutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getCO2ProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(CO2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getCO2ProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public IBioModule getOutputModule() {
        return myProducer;
    }

    public CO2Producer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getCO2ProducerDefinition().getMaxFlowRate(myIndex);
    }
}