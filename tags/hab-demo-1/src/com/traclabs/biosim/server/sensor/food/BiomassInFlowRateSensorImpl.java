package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class BiomassInFlowRateSensorImpl extends GenericSensorImpl implements
        BiomassInFlowRateSensorOperations {
    private BiomassConsumer myConsumer;

    private int myIndex;

    public BiomassInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getBiomassConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(BiomassConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getBiomassConsumerDefinition()
                .getMaxFlowRate(myIndex);
    }

    public BiomassConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }
}