package com.traclabs.biosim.server.sensor.power;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.power.PowerProducer;

public class PowerOutFlowRateSensor extends GenericSensor {
    private PowerProducer myProducer;

    private int myIndex;

    public PowerOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getPowerProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(PowerProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getPowerProducerDefinition().getMaxFlowRate(myIndex);
    }

    public PowerProducer getInput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public IBioModule getInputModule() {
        return myProducer;
    }
}