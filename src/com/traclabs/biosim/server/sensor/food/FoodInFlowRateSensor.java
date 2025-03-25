package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.food.FoodConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;

public class FoodInFlowRateSensor extends GenericSensor implements
        FoodInFlowRateSensorOperations {
    private FoodConsumer myConsumer;

    private int myIndex;

    public FoodInFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getFoodConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(FoodConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getFoodConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public FoodConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}