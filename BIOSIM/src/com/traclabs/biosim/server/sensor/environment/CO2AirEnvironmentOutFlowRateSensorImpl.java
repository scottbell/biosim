package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class CO2AirEnvironmentOutFlowRateSensorImpl extends GenericSensorImpl
        implements CO2AirEnvironmentOutFlowRateSensorOperations {
    private CO2AirProducer myProducer;

    private int myIndex;

    public CO2AirEnvironmentOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCO2AirProducerDefinition()
                .getEnvironmentActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(CO2AirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getCO2AirProducerDefinition()
                .getEnvironmentMaxFlowRate(myIndex);
    }

    public CO2AirProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myProducer);
    }

    public int getIndex() {
        return myIndex;
    }

}