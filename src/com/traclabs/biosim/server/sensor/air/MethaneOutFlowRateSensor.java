package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.MethaneProducer;

public class MethaneOutFlowRateSensor extends GenericSensor{
    private MethaneProducer myProducer;

    private int myIndex;

    public MethaneOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getMethaneProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(MethaneProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getMethaneProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public MethaneProducer getInput() {
        return myProducer;
    }

    public IBioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}