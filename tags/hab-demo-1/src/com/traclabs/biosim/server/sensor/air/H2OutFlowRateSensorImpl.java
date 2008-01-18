package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.air.H2Producer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class H2OutFlowRateSensorImpl extends GenericSensorImpl implements
        H2OutFlowRateSensorOperations {
    private H2Producer myProducer;

    private int myIndex;

    public H2OutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getH2ProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(H2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getH2ProducerDefinition().getMaxFlowRate(myIndex);
    }

    public H2Producer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}