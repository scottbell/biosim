package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class BiomassOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements BiomassOutFlowRateActuatorOperations {
    private BiomassProducer myProducer;

    private int myIndex;

    public BiomassOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getBiomassProducerDefinition().setDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(BiomassProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public BiomassProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getBiomassProducerDefinition().getDesiredFlowRate(myIndex);
    }
}