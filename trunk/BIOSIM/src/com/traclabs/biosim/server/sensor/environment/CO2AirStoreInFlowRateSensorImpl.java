package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.CO2AirConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class CO2AirStoreInFlowRateSensorImpl extends GenericSensorImpl
        implements CO2AirStoreInFlowRateSensorOperations {
    private CO2AirConsumer myConsumer;

    private int myIndex;

    public CO2AirStoreInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getCO2AirConsumerDefinition()
                .getStoreActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(CO2AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getCO2AirConsumerDefinition().getStoreMaxFlowRate(
                myIndex);
    }

    public CO2AirConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }

}