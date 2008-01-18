package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.air.CO2Consumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class CO2InFlowRateSensorImpl extends GenericSensorImpl implements
        CO2InFlowRateSensorOperations {
    private CO2Consumer myConsumer;

    private int myIndex;

    public CO2InFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCO2ConsumerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(CO2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getCO2ConsumerDefinition().getMaxFlowRate(myIndex);
    }

    public CO2Consumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

}