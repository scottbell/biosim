package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.food.BiomassProducer;

public class BiomassOutFlowRateSensor extends GenericSensor implements
        BiomassOutFlowRateSensorOperations {
    private BiomassProducer myProducer;

    private int myIndex;

    public BiomassOutFlowRateSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getBiomassProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(BiomassProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getBiomassProducerDefinition()
                .getMaxFlowRate(myIndex);
    }

    public BiomassProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}