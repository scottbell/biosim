package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.CO2Producer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2OutFlowRateActuatorImpl extends GenericActuatorImpl implements
        CO2OutFlowRateActuatorOperations {
    private CO2Producer myProducer;

    private int myIndex;

    public CO2OutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getCO2ProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(CO2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getCO2ProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public CO2Producer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getCO2ProducerDefinition().getMaxFlowRate(myIndex);
    }
}