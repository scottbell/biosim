package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducer;

public class PotableWaterOutFlowRateSensor extends GenericSensor
         {
    private PotableWaterProducer myProducer;

    private int myIndex;

    public PotableWaterOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getPotableWaterProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(PotableWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public PotableWaterProducer getInput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getPotableWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public IBioModule getInputModule() {
        return myProducer;
    }
}