package com.traclabs.biosim.server.sensor.waste;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;

public class DryWasteOutFlowRateSensor extends GenericSensor {
    private DryWasteProducer myProducer;

    private int myIndex;

    public DryWasteOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getDryWasteProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }


    public void setInput(DryWasteProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public DryWasteProducer getInput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getDryWasteProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public int getIndex() {
        return myIndex;
    }

    public IBioModule getInputModule() {
        return myProducer;
    }
}