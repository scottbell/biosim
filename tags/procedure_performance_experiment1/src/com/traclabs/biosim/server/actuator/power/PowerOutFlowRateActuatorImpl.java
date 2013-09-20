package com.traclabs.biosim.server.actuator.power;

import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.power.PowerProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PowerOutFlowRateActuatorImpl extends GenericActuatorImpl implements
        PowerOutFlowRateActuatorOperations {
    private PowerProducer myProducer;

    private int myIndex;

    public PowerOutFlowRateActuatorImpl(int pID, String pName) {
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