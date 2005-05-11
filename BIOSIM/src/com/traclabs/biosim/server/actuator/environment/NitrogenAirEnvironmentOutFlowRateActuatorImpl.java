package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenAirEnvironmentOutFlowRateActuatorImpl extends
        GenericActuatorImpl implements
        NitrogenAirEnvironmentOutFlowRateActuatorOperations {
    private NitrogenAirProducer myProducer;

    private int myIndex;

    public NitrogenAirEnvironmentOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getNitrogenAirProducerDefinition()
                .setEnvironmentDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(NitrogenAirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public float getMax() {
        return myProducer.getNitrogenAirProducerDefinition()
                .getEnvironmentMaxFlowRate(myIndex);
    }

    public NitrogenAirProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}