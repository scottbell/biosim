package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.food.FoodProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class FoodOutFlowRateActuatorImpl extends GenericActuatorImpl implements
        FoodOutFlowRateActuatorOperations {
    private FoodProducer myProducer;

    private int myIndex;

    public FoodOutFlowRateActuatorImpl(int pID, String pName) {
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