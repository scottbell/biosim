package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class FoodInFlowRateSensorImpl extends GenericSensorImpl implements
        FoodInFlowRateSensorOperations {
    private FoodConsumer myConsumer;

    private int myIndex;

    public FoodInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getFoodConsumerDefinition().getActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
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
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }
}