package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class DirtyWaterInFlowRateActuatorImpl extends GenericActuatorImpl
        implements DirtyWaterInFlowRateActuatorOperations {
    private DirtyWaterConsumer myConsumer;

    private int myIndex;

    public DirtyWaterInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getDirtyWaterConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(DirtyWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getDirtyWaterConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public DirtyWaterConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getDirtyWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}