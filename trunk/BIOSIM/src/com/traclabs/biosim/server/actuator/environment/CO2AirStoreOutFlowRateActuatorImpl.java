package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.CO2AirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2AirStoreOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements CO2AirStoreOutFlowRateActuatorOperations {
    private CO2AirProducer myProducer;

    private int myIndex;

    public CO2AirStoreOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getCO2AirProducerDefinition().setStoreDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(CO2AirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (myProducer);
    }

    public float getMax() {
        return myProducer.getCO2AirProducerDefinition().getStoreMaxFlowRate(
                myIndex);
    }

    public CO2AirProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}