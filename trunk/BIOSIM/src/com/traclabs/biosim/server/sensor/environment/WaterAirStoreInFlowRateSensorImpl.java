package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class WaterAirStoreInFlowRateSensorImpl extends GenericSensorImpl
        implements WaterAirStoreInFlowRateSensorOperations {
    private WaterAirConsumer myConsumer;

    private int myIndex;

    public WaterAirStoreInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getWaterAirConsumerDefinition()
                .getStoreActualFlowRate(myIndex);
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
        return myConsumer.getWaterAirConsumerDefinition().getStoreMaxFlowRate(myIndex);
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