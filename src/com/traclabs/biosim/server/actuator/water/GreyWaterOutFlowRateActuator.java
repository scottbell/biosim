package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducer;

public class GreyWaterOutFlowRateActuator extends GenericActuator
         {
    private GreyWaterProducer myProducer;

    private int myIndex;

    public GreyWaterOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getGreyWaterProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(GreyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getGreyWaterProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public GreyWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getGreyWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}