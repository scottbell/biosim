package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirEnvironmentOutFlowRateSensorImpl extends GenericSensorImpl
        implements WaterAirEnvironmentOutFlowRateSensorOperations {
    private WaterAirProducer myProducer;

    private int myIndex;

    public WaterAirEnvironmentOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterAirProducerDefinition()
                .getEnvironmentActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(WaterAirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getWaterAirProducerDefinition().getEnvironmentMaxFlowRate(myIndex);
    }

    public WaterAirProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myProducer);
    }

    public int getIndex() {
        return myIndex;
    }
}