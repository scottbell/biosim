package com.traclabs.biosim.server.sensor.waste;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class DryWasteOutFlowRateSensorImpl extends GenericSensorImpl implements
        DryWasteOutFlowRateSensorOperations {
    private DryWasteProducer myProducer;

    private int myIndex;

    public DryWasteOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getDryWasteOutputActualFlowRate(
                myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(DryWasteProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public DryWasteProducer getInput() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getDryWasteOutputMaxFlowRate(myIndex);
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return (BioModule) (myProducer);
    }
}