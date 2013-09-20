package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.WaterOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.water.WaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterOutFlowRateActuatorImpl extends GenericActuatorImpl implements
        WaterOutFlowRateActuatorOperations {
    private WaterProducer myProducer;

    private int myIndex;

    public WaterOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getWaterProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(WaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getWaterProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return (myProducer);
    }

    public WaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getWaterProducerDefinition().getMaxFlowRate(myIndex);
    }
}