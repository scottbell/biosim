package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.MethaneInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class MethaneInFlowRateActuatorImpl extends GenericActuatorImpl
        implements MethaneInFlowRateActuatorOperations {
    private MethaneConsumer myConsumer;

    private int myIndex;

    public MethaneInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getMethaneConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(MethaneConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getMethaneConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public MethaneConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getMethaneConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}