package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.AirConsumer;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorOperations;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class AirInFlowRateSensorImpl extends GenericSensorImpl implements
        AirInFlowRateSensorOperations {
    private AirConsumer myConsumer;

    private int myIndex;

    public AirInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getAirInputActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public float getMax() {
        return myConsumer.getAirInputMaxFlowRate(myIndex);
    }

    public void setInput(AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public AirConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }
}