package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class FoodOutFlowRateSensorImpl extends GenericSensorImpl implements
        FoodOutFlowRateSensorOperations {
    private FoodProducer myProducer;

    private int myIndex;

    public FoodOutFlowRateSensorImpl(int pID, String pName) {
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

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}