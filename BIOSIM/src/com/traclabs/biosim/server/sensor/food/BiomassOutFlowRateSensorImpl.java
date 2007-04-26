package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class BiomassOutFlowRateSensorImpl extends GenericSensorImpl implements
        BiomassOutFlowRateSensorOperations {
    private BiomassProducer myProducer;

    private int myIndex;

    public BiomassOutFlowRateSensorImpl(int pID, String pName) {
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