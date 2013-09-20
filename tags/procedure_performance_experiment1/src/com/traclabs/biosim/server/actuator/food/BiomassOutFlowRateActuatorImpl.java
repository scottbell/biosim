package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.food.BiomassProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class BiomassOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements BiomassOutFlowRateActuatorOperations {
    private BiomassProducer myProducer;

    private int myIndex;

    public BiomassOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getBiomassProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(BiomassProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getBiomassProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return (myProducer);
    }

    public BiomassProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getBiomassProducerDefinition()
                .getMaxFlowRate(myIndex);
    }
}