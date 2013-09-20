package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements NitrogenOutFlowRateActuatorOperations {
    private NitrogenProducer myProducer;

    private int myIndex;

    public NitrogenOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getNitrogenProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(NitrogenProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getNitrogenProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public NitrogenProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getNitrogenProducerDefinition().getMaxFlowRate(
                myIndex);
    }
}