package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuator;

public class DirtyWaterOutFlowRateActuator extends GenericActuator
         {
    private DirtyWaterProducer myProducer;

    private int myIndex;

    public DirtyWaterOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getDirtyWaterProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(DirtyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getDirtyWaterProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public DirtyWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getDirtyWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}