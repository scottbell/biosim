package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.WaterAirProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirStoreOutFlowRateSensorImpl extends GenericSensorImpl
        implements WaterAirStoreOutFlowRateSensorOperations {
    private WaterAirProducer myProducer;

    private int myIndex;

    public WaterAirStoreOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterAirProducerDefinition()
                .getStoreActualFlowRate(myIndex);
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
        return myProducer.getWaterAirProducerDefinition().getStoreMaxFlowRate(
                myIndex);
    }

    public WaterAirProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}