package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class GreyWaterOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements GreyWaterOutFlowRateActuatorOperations {
    private GreyWaterProducer myProducer;

    private int myIndex;

    public GreyWaterOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getGreyWaterProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(GreyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getGreyWaterProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public GreyWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getGreyWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}