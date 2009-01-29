package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PotableWaterInFlowRateActuatorImpl extends GenericActuatorImpl
        implements PotableWaterInFlowRateActuatorOperations {
    private PotableWaterConsumer myConsumer;

    private int myIndex;

    public PotableWaterInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getPotableWaterConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(PotableWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getPotableWaterConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public PotableWaterConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getPotableWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}