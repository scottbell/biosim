package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.WaterAirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class WaterAirStoreOutFlowRateActuatorImpl extends GenericActuatorImpl
        implements WaterAirStoreOutFlowRateActuatorOperations {
    private WaterAirProducer myProducer;

    private int myIndex;

    public WaterAirStoreOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = randomFilter(myValue);
        getOutput().setWaterAirStoreOutputDesiredFlowRate(myFilteredValue,
                myIndex);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setOutput(WaterAirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public BioModule getOutputModule() {
        return (BioModule) (myProducer);
    }

    public float getMax() {
        return myProducer.getWaterAirStoreOutputMaxFlowRate(myIndex);
    }

    public WaterAirProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}