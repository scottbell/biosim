package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenInFlowRateActuatorImpl extends GenericActuatorImpl
        implements NitrogenInFlowRateActuatorOperations {
    private NitrogenConsumer myConsumer;

    private int myIndex;

    public NitrogenInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getNitrogenConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(NitrogenConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public NitrogenConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getNitrogenConsumerDefinition().getMaxFlowRate(
                myIndex);
    }
}