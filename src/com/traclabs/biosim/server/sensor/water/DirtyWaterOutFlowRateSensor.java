package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducer;

public class DirtyWaterOutFlowRateSensor extends GenericSensor {
    private DirtyWaterProducer myProducer;

    private int myIndex;

    public DirtyWaterOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getDirtyWaterProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }


    public void setInput(DirtyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public DirtyWaterProducer getInput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getDirtyWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public int getIndex() {
        return myIndex;
    }

    public IBioModule getInputModule() {
        return myProducer;
    }
}