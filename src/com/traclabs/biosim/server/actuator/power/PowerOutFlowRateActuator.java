package com.traclabs.biosim.server.actuator.power;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.power.PowerProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuator;

public class PowerOutFlowRateActuator extends GenericActuator implements
        PowerOutFlowRateActuatorOperations {
    private PowerProducer myProducer;

    private int myIndex;

    public PowerOutFlowRateActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getPowerProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(PowerProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getPowerProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public PowerProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getPowerProducerDefinition().getMaxFlowRate(myIndex);
    }
}