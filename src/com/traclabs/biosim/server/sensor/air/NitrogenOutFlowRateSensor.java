package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.NitrogenProducer;

public class NitrogenOutFlowRateSensor extends GenericSensor {
    private NitrogenProducer myProducer;

    private int myIndex;

    public NitrogenOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getNitrogenProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }


    public void setInput(NitrogenProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getNitrogenProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public NitrogenProducer getInput() {
        return myProducer;
    }

    public IBioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}