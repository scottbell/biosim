package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class PotableWaterInFlowRateSensorImpl extends GenericSensorImpl
        implements PotableWaterInFlowRateSensorOperations {
    private PotableWaterConsumer myConsumer;

    private int myIndex;

    public PotableWaterInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getPotableWaterInputActualFlowRate(
                myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(PotableWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public PotableWaterConsumer getInput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public float getMax() {
        return myConsumer.getPotableWaterInputMaxFlowRate(myIndex);
    }
}