package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.environment.AirProducer;

public class AirOutFlowRateActuator extends GenericActuator {
    private AirProducer myProducer;

    private int myIndex;

    public AirOutFlowRateActuator() {
        super(0, "Unnamed AirOutFlowRateActuator");
    }

    public AirOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getAirProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(AirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getAirProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public IBioModule getOutputModule() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getAirProducerDefinition().getMaxFlowRate(myIndex);
    }

    public AirProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}