package com.traclabs.biosim.server.actuator.waste;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;

public class DryWasteOutFlowRateActuator extends GenericActuator
         {
    private DryWasteProducer myProducer;

    private int myIndex;

    public DryWasteOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getDryWasteProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(DryWasteProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getDryWasteProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public DryWasteProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getDryWasteProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}