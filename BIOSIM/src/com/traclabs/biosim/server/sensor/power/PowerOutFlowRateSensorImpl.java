package com.traclabs.biosim.server.sensor.power;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.power.PowerProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class PowerOutFlowRateSensorImpl extends GenericSensorImpl implements
        PowerOutFlowRateSensorOperations {
    private PowerProducer myProducer;

    private int myIndex;

    public PowerOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getPowerProducerDefinition()
                .getActualFlowRate(myIndex);
        myValue = getStochasticFilter().randomFilter(preFilteredValue);
    }



    public void setInput(PowerProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getPowerProducerDefinition().getMaxFlowRate(myIndex);
    }

    public PowerProducer getInput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }

    public BioModule getInputModule() {
        return myProducer;
    }
}