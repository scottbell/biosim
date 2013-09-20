package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.H2Producer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class H2OutFlowRateActuatorImpl extends GenericActuatorImpl implements
        H2OutFlowRateActuatorOperations {
    private H2Producer myProducer;

    private int myIndex;

    public H2OutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getH2ProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(H2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getH2ProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public H2Producer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getH2ProducerDefinition().getMaxFlowRate(myIndex);
    }
}