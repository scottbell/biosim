package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterAirEnvironmentOutFlowRateActuatorImpl extends
        GenericActuatorImpl implements
        WaterAirEnvironmentOutFlowRateActuatorOperations {
    private WaterAirProducer myProducer;

    private int myIndex;

    public WaterAirEnvironmentOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getWaterAirProducerDefinition()
                .setEnvironmentDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(WaterAirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public float getMax() {
        return myProducer.getWaterAirProducerDefinition()
                .getEnvironmentMaxFlowRate(myIndex);
    }

    public WaterAirProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}