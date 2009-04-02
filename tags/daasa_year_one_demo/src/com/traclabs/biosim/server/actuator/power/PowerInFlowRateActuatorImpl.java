package com.traclabs.biosim.server.actuator.power;

import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PowerInFlowRateActuatorImpl extends GenericActuatorImpl implements
        PowerInFlowRateActuatorOperations {
    private PowerConsumer myConsumer;

    private int myIndex;

    public PowerInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getPowerConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(PowerConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getPowerConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public PowerConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getPowerConsumerDefinition().getMaxFlowRate(myIndex);
    }
}