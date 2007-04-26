package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.air.H2Consumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class H2InFlowRateSensorImpl extends GenericSensorImpl implements
        H2InFlowRateSensorOperations {
    private H2Consumer myConsumer;

    private int myIndex;

    public H2InFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getH2ConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(H2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getH2ConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public H2Consumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

}