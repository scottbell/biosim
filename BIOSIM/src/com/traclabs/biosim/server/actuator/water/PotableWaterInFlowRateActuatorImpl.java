package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class PotableWaterInFlowRateActuatorImpl extends GenericActuatorImpl
        implements PotableWaterInFlowRateActuatorOperations {
    private PotableWaterConsumer myConsumer;

    private int myIndex;

    public PotableWaterInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getPotableWaterConsumerDefinition().setDesiredFlowRate(myFilteredValue,
                myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(PotableWaterConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public PotableWaterConsumer getOutput() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getPotableWaterConsumerDefinition().getDesiredFlowRate(myIndex);
    }
}