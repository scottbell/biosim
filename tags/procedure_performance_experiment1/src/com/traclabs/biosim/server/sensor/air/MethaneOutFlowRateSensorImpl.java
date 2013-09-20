package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class MethaneOutFlowRateSensorImpl extends GenericSensorImpl implements
        MethaneOutFlowRateSensorOperations {
    private MethaneProducer myProducer;

    private int myIndex;

    public MethaneOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getMethaneProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(MethaneProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getMethaneProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public MethaneProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}