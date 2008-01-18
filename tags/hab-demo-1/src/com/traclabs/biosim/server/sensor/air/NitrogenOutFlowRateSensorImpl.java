package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenOutFlowRateSensorImpl extends GenericSensorImpl implements
        NitrogenOutFlowRateSensorOperations {
    private NitrogenProducer myProducer;

    private int myIndex;

    public NitrogenOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getNitrogenProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(NitrogenProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getNitrogenProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public NitrogenProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

}