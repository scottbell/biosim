package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenInFlowRateSensorImpl extends GenericSensorImpl implements
        NitrogenInFlowRateSensorOperations {
    private NitrogenConsumer myConsumer;

    private int myIndex;

    public NitrogenInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getNitrogenInputActualFlowRate(
                myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(NitrogenConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getNitrogenInputMaxFlowRate(myIndex);
    }

    public NitrogenConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }

}