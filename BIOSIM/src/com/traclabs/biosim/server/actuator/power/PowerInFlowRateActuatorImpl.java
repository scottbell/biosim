package com.traclabs.biosim.server.actuator.power;

import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PowerInFlowRateActuatorImpl extends GenericActuatorImpl implements
        PowerInFlowRateActuatorOperations {
    private PowerConsumer myConsumer;

    private int myIndex;

    public PowerInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getPowerConsumerDefinition().setDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(PowerConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
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