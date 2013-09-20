package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.CO2Consumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class CO2InFlowRateActuatorImpl extends GenericActuatorImpl implements
        CO2InFlowRateActuatorOperations {
    private CO2Consumer myConsumer;

    private int myIndex;

    public CO2InFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getCO2ConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(CO2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getCO2ConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return (myConsumer);
    }

    public CO2Consumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getCO2ConsumerDefinition().getMaxFlowRate(myIndex);
    }
}