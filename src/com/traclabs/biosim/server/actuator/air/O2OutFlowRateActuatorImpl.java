package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.O2Producer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class O2OutFlowRateActuatorImpl extends GenericActuatorImpl implements
        O2OutFlowRateActuatorOperations {
    private O2Producer myProducer;

    private int myIndex;

    public O2OutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getO2ProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(O2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getO2ProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public O2Producer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getO2ProducerDefinition().getMaxFlowRate(myIndex);
    }
}