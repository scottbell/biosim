package com.traclabs.biosim.server.actuator.waste;

import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class DryWasteInFlowRateActuatorImpl extends GenericActuatorImpl
        implements DryWasteInFlowRateActuatorOperations {
    private DryWasteConsumer myConsumer;

    private int myIndex;

    public DryWasteInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getDryWasteConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(DryWasteConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getDryWasteConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public DryWasteConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getDryWasteConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}