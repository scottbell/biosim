package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.air.MethaneConsumer;

public class MethaneInFlowRateSensor extends GenericSensor {
    private MethaneConsumer myConsumer;

    private int myIndex;

    public MethaneInFlowRateSensor(int pID, String pName) {
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