package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterOutFlowRateSensorImpl extends GenericSensorImpl implements
        WaterOutFlowRateSensorOperations {
    private WaterProducer myProducer;

    private int myIndex;

    public WaterOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(WaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public WaterProducer getInput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getWaterProducerDefinition().getMaxFlowRate(myIndex);
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return myProducer;
    }
}