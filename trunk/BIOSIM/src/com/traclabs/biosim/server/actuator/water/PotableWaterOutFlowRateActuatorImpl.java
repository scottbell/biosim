package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PotableWaterOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements PotableWaterOutFlowRateActuatorOperations {
    private PotableWaterProducer myProducer;

    private int myIndex;

    public PotableWaterOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getPotableWaterProducerDefinition().setDesiredFlowRate(myFilteredValue,
                myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(PotableWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public PotableWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getPotableWaterProducerDefinition().getMaxFlowRate(myIndex);
    }
}