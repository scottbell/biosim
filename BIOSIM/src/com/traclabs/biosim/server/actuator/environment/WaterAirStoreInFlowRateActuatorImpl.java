package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterAirStoreInFlowRateActuatorImpl extends GenericActuatorImpl
        implements WaterAirStoreInFlowRateActuatorOperations {
    private WaterAirConsumer myConsumer;

    private int myIndex;

    public WaterAirStoreInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getWaterAirConsumerDefinition().setStoreDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(WaterAirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getWaterAirConsumerDefinition().getStoreMaxFlowRate(
                myIndex);
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public WaterAirConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}