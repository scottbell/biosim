package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.NitrogenProducer;

public class NitrogenOutFlowRateActuator extends GenericActuator{
    private NitrogenProducer myProducer;

    private int myIndex;

    public NitrogenOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getNitrogenProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(NitrogenProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getNitrogenProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public NitrogenProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getNitrogenProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}