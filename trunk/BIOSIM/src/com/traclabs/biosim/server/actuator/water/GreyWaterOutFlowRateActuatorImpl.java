package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class GreyWaterOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements GreyWaterOutFlowRateActuatorOperations {
    private GreyWaterProducer myProducer;

    private int myIndex;

    public GreyWaterOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getGreyWaterProducerDefinition().setDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(GreyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public GreyWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getGreyWaterProducerDefinition().getMaxFlowRate(myIndex);
    }
}