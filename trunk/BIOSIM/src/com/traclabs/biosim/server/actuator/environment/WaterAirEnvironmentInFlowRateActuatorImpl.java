package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.WaterAirConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterAirEnvironmentInFlowRateActuatorImpl extends
        GenericActuatorImpl implements
        WaterAirEnvironmentInFlowRateActuatorOperations {
    private WaterAirConsumer myConsumer;

    private int myIndex;

    public WaterAirEnvironmentInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().setWaterAirEnvironmentInputDesiredFlowRate(myFilteredValue,
                myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(WaterAirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public WaterAirConsumer getOutput() {
        return myConsumer;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getWaterAirEnvironmentInputMaxFlowRate(myIndex);
    }
}