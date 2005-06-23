package com.traclabs.biosim.server.sensor.water;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class GreyWaterOutFlowRateSensorImpl extends GenericSensorImpl implements
        GreyWaterOutFlowRateSensorOperations {
    private GreyWaterProducer myProducer;

    private int myIndex;

    public GreyWaterOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getGreyWaterProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(GreyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public GreyWaterProducer getInput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getGreyWaterProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return myProducer;
    }
}