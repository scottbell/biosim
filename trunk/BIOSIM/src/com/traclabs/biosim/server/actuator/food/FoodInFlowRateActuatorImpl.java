package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class FoodInFlowRateActuatorImpl extends GenericActuatorImpl implements
        FoodInFlowRateActuatorOperations {
    private FoodConsumer myConsumer;

    private int myIndex;

    public FoodInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getFoodConsumerDefinition().setDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(FoodConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public FoodConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getFoodConsumerDefinition().getMaxFlowRate(myIndex);
    }
}