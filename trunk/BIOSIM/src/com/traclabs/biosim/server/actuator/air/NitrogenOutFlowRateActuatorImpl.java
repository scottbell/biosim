package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements NitrogenOutFlowRateActuatorOperations {
    private NitrogenProducer myProducer;

    private int myIndex;

    public NitrogenOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().getNitrogenProducerDefinition().setDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(NitrogenProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public NitrogenProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getNitrogenProducerDefinition().getMaxFlowRate(myIndex);
    }
}