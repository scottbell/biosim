package com.traclabs.biosim.server.actuator.water;

import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class DirtyWaterOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements DirtyWaterOutFlowRateActuatorOperations {
    private DirtyWaterProducer myProducer;

    private int myIndex;

    public DirtyWaterOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput()
                .setDirtyWaterOutputDesiredFlowRate(myFilteredValue, myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(DirtyWaterProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public DirtyWaterProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myProducer.getDirtyWaterOutputMaxFlowRate(myIndex);
    }
}