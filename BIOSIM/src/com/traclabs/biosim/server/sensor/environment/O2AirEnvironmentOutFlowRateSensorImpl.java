package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class O2AirEnvironmentOutFlowRateSensorImpl extends GenericSensorImpl
        implements O2AirEnvironmentOutFlowRateSensorOperations {
    private O2AirProducer myProducer;

    private int myIndex;

    public O2AirEnvironmentOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getO2AirProducerDefinition()
                .getEnvironmentActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(O2AirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getO2AirProducerDefinition().getEnvironmentMaxFlowRate(myIndex);
    }

    public O2AirProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myProducer);
    }

    public int getIndex() {
        return myIndex;
    }
}