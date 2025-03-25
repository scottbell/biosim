package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.air.MethaneProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuator;

public class MethaneOutFlowRateActuator extends GenericActuator {
    private MethaneProducer myProducer;

    private int myIndex;

    public MethaneOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getMethaneProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(MethaneProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getMethaneProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public MethaneProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getMethaneProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}