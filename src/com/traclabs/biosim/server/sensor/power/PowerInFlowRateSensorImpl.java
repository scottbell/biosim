package com.traclabs.biosim.server.sensor.power;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class PowerInFlowRateSensorImpl extends GenericSensorImpl implements
        PowerInFlowRateSensorOperations {
    private PowerConsumer myConsumer;

    private int myIndex;

    public PowerInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getPowerConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(PowerConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getPowerConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public PowerConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}