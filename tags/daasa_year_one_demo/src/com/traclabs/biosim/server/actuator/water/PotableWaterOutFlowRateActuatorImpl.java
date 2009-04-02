package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PotableWaterOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements PotableWaterOutFlowRateActuatorOperations {
    private PotableWaterProducer myProducer;

    private int myIndex;

    public PotableWaterOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getPotableWaterProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(PotableWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getPotableWaterProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return (myProducer);
    }

    public PotableWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getPotableWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}