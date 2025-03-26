package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.food.FoodProducer;

public class FoodOutFlowRateActuator extends GenericActuator {
    private FoodProducer myProducer;

    private int myIndex;

    public FoodOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getFoodProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(FoodProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getFoodProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public FoodProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getFoodProducerDefinition().getMaxFlowRate(myIndex);
    }
}