package com.traclabs.biosim.server.sensor.waste;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class DryWasteOutFlowRateSensorImpl extends GenericSensorImpl implements
        DryWasteOutFlowRateSensorOperations {
    private DryWasteProducer myProducer;

    private int myIndex;

    public DryWasteOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getDryWasteProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(DryWasteProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public DryWasteProducer getInput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getDryWasteProducerDefinition().getMaxFlowRate(
                myIndex);
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return myProducer;
    }
}