package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class GreyWaterInFlowRateSensorImpl extends GenericSensorImpl implements
        GreyWaterInFlowRateSensorOperations {
    private GreyWaterConsumer myConsumer;

    private int myIndex;

    public GreyWaterInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getGreyWaterConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(GreyWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public GreyWaterConsumer getInput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getGreyWaterConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}