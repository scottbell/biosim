package com.traclabs.biosim.server.sensor.power;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class PowerInFlowRateSensorImpl extends GenericSensorImpl implements
        PowerInFlowRateSensorOperations {
    private PowerConsumer myConsumer;

    private int myIndex;

    public PowerInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput()
                .getPowerInputActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(PowerConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getPowerInputMaxFlowRate(myIndex);
    }

    public PowerConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }
}