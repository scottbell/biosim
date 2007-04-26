package com.traclabs.biosim.server.sensor.waste;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class DryWasteInFlowRateSensorImpl extends GenericSensorImpl implements
        DryWasteInFlowRateSensorOperations {
    private DryWasteConsumer myConsumer;

    private int myIndex;

    public DryWasteInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getDryWasteConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(DryWasteConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public DryWasteConsumer getInput() {
        return myConsumer;
    }

    public float getMax() {
        return myConsumer.getDryWasteConsumerDefinition().getMaxFlowRate(
                myIndex);
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}