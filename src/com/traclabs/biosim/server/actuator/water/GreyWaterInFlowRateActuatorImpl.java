package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class GreyWaterInFlowRateActuatorImpl extends GenericActuatorImpl
        implements GreyWaterInFlowRateActuatorOperations {
    private GreyWaterConsumer myConsumer;

    private int myIndex;

    public GreyWaterInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getGreyWaterConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(GreyWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getGreyWaterConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public GreyWaterConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getGreyWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}