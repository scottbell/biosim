package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducer;

public class PotableWaterOutFlowRateActuator extends GenericActuator {
    private PotableWaterProducer myProducer;

    private int myIndex;

    public PotableWaterOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getPotableWaterProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }


    public void setOutput(PotableWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getPotableWaterProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public IBioModule getOutputModule() {
        return (myProducer);
    }

    public PotableWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getPotableWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}