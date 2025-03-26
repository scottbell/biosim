package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.food.BiomassProducer;

public class BiomassOutFlowRateActuator extends GenericActuator{
    private BiomassProducer myProducer;

    private int myIndex;

    public BiomassOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getBiomassProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(BiomassProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getBiomassProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return (myProducer);
    }

    public BiomassProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getBiomassProducerDefinition()
                .getMaxFlowRate(myIndex);
    }
}