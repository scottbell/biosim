package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterOutFlowRateSensorImpl extends GenericSensorImpl implements
        WaterOutFlowRateSensorOperations {
    private WaterProducer myProducer;

    private int myIndex;

    public WaterOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterOutputActualFlowRate(
                myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(WaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public WaterProducer getInput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getWaterOutputMaxFlowRate(myIndex);
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return (BioModule) (myProducer);
    }
}