package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenAirStoreOutFlowRateActuatorImpl extends
        GenericActuatorImpl implements
        NitrogenAirStoreOutFlowRateActuatorOperations {
    private NitrogenAirProducer myProducer;

    private int myIndex;

    public NitrogenAirStoreOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getNitrogenAirProducerDefinition().setStoreDesiredFlowRate(
                myFilteredValue, myIndex);
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
                .getStoreMaxFlowRate(myIndex);
    }

    public NitrogenAirProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}