package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.WaterInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.WaterConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterInFlowRateActuatorImpl extends GenericActuatorImpl implements
        WaterInFlowRateActuatorOperations {
    private WaterConsumer myConsumer;

    private int myIndex;

    public WaterInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getWaterConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(WaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getWaterConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public WaterConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getWaterConsumerDefinition().getMaxFlowRate(myIndex);
    }
}