package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.O2Consumer;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorOperations;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class O2InFlowRateSensorImpl extends GenericSensorImpl implements
        O2InFlowRateSensorOperations {
    private O2Consumer myConsumer;

    private int myIndex;

    public O2InFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getO2InputActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(O2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public O2Consumer getInput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public float getMax() {
        return myConsumer.getO2InputMaxFlowRate(myIndex);
    }

}