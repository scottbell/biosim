package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.CO2Consumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2InFlowRateActuatorImpl extends GenericActuatorImpl implements
        CO2InFlowRateActuatorOperations {
    private CO2Consumer myConsumer;

    private int myIndex;

    public CO2InFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().setCO2InputDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(CO2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public CO2Consumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getCO2InputMaxFlowRate(myIndex);
    }
}