package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenAirEnvironmentInFlowRateActuatorImpl extends
        GenericActuatorImpl implements
        NitrogenAirEnvironmentInFlowRateActuatorOperations {
    private NitrogenAirConsumer myConsumer;

    private int myIndex;

    public NitrogenAirEnvironmentInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getNitorgenAirConsumerDefinition().setEnvironmentDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(NitrogenAirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public NitrogenAirConsumer getOutput() {
        return myConsumer;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getNitorgenAirConsumerDefinition().getEnvironmentMaxFlowRate(myIndex);
    }
}