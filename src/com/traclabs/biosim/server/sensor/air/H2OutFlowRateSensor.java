package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.H2Producer;

public class H2OutFlowRateSensor extends GenericSensor {
    private H2Producer myProducer;

    private int myIndex;

    public H2OutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getH2ProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(H2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getH2ProducerDefinition().getMaxFlowRate(myIndex);
    }

    public H2Producer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}