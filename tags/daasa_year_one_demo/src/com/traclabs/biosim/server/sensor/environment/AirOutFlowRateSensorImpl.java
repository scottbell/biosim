package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class AirOutFlowRateSensorImpl extends GenericSensorImpl implements
        AirOutFlowRateSensorOperations {
    private AirProducer myProducer;

    private int myIndex;

    public AirOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getAirProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(AirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getAirProducerDefinition().getMaxFlowRate(myIndex);
    }

    public AirProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}