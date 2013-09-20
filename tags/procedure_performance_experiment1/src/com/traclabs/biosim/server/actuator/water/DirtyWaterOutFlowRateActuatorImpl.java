package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class DirtyWaterOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements DirtyWaterOutFlowRateActuatorOperations {
    private DirtyWaterProducer myProducer;

    private int myIndex;

    public DirtyWaterOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getDirtyWaterProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(DirtyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getDirtyWaterProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public DirtyWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getDirtyWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}