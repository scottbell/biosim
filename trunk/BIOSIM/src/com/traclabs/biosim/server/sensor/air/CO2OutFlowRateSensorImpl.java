package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2Producer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class CO2OutFlowRateSensorImpl extends GenericSensorImpl implements
        CO2OutFlowRateSensorOperations {
    private CO2Producer myProducer;

    private int myIndex;

    public CO2OutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCO2OutputActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(CO2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getCO2OutputMaxFlowRate(myIndex);
    }

    public CO2Producer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myProducer);
    }

    public int getIndex() {
        return myIndex;
    }

}