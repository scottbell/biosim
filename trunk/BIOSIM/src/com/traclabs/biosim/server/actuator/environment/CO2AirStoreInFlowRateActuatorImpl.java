package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.CO2AirConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2AirStoreInFlowRateActuatorImpl extends GenericActuatorImpl
        implements CO2AirStoreInFlowRateActuatorOperations {
    private CO2AirConsumer myConsumer;

    private int myIndex;

    public CO2AirStoreInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getCO2AirConsumerDefinition().setStoreDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(CO2AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getCO2AirConsumerDefinition().getStoreMaxFlowRate(
                myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public CO2AirConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}