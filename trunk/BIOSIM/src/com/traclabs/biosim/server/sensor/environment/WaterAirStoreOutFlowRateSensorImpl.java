package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.WaterAirProducer;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreOutFlowRateSensorOperations;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirStoreOutFlowRateSensorImpl extends GenericSensorImpl
        implements WaterAirStoreOutFlowRateSensorOperations {
    private WaterAirProducer myProducer;

    private int myIndex;

    public WaterAirStoreOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput()
                .getWaterAirStoreOutputActualFlowRate(myIndex);
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
        return myProducer.getWaterAirStoreOutputMaxFlowRate(myIndex);
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