package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.food.FoodProducer;

public class FoodOutFlowRateSensor extends GenericSensor {
    private FoodProducer myProducer;

    private int myIndex;

    public FoodOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getFoodProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(FoodProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getFoodProducerDefinition().getMaxFlowRate(myIndex);
    }

    public FoodProducer getInput() {
        return myProducer;
    }

    public IBioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}