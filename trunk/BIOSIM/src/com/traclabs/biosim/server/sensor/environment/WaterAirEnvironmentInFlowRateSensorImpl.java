package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirEnvironmentInFlowRateSensorImpl extends GenericSensorImpl
        implements WaterAirEnvironmentInFlowRateSensorOperations {
    private WaterAirConsumer myConsumer;

    private int myIndex;

    public WaterAirEnvironmentInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterAirConsumerDefinition()
                .getEnvironmentActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(WaterAirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getWaterAirConsumerDefinition()
                .getEnvironmentMaxFlowRate(myIndex);
    }

    public WaterAirConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }
}