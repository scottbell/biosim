package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class PotableWaterOutFlowRateSensorImpl extends GenericSensorImpl
        implements PotableWaterOutFlowRateSensorOperations {
    private PotableWaterProducer myProducer;

    private int myIndex;

    public PotableWaterOutFlowRateSensorImpl(int pID, String pName) {
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

    public BioModule getInputModule() {
        return myProducer;
    }
}