package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class O2AirStoreInFlowRateSensorImpl extends GenericSensorImpl implements
        O2AirStoreInFlowRateSensorOperations {
    private O2AirConsumer myConsumer;

    private int myIndex;

    public O2AirStoreInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getO2AirStoreInputActualFlowRate(
                myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(O2AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getO2AirEnvironmentInputMaxFlowRate(myIndex);
    }

    public O2AirConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }

}