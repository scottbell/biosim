package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class MethaneInFlowRateSensorImpl extends GenericSensorImpl implements
        MethaneInFlowRateSensorOperations {
    private MethaneConsumer myConsumer;

    private int myIndex;

    public MethaneInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getMethaneConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(MethaneConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getMethaneConsumerDefinition().getMaxFlowRate(
                myIndex);
    }

    public MethaneConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

}