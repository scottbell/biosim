package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterInFlowRateSensorImpl extends GenericSensorImpl implements
        WaterInFlowRateSensorOperations {
    private WaterConsumer myConsumer;

    private int myIndex;

    public WaterInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(WaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public WaterConsumer getInput() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getWaterConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}