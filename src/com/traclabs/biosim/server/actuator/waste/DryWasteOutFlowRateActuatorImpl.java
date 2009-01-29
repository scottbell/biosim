package com.traclabs.biosim.server.actuator.waste;

import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class DryWasteOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements DryWasteOutFlowRateActuatorOperations {
    private DryWasteProducer myProducer;

    private int myIndex;

    public DryWasteOutFlowRateActuatorImpl(int pID, String pName) {
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