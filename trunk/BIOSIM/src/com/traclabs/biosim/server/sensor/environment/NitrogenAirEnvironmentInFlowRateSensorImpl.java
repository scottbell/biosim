package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenAirEnvironmentInFlowRateSensorImpl extends
        GenericSensorImpl implements
        NitrogenAirEnvironmentInFlowRateSensorOperations {
    private NitrogenAirConsumer myConsumer;

    private int myIndex;

    public NitrogenAirEnvironmentInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getNitorgenAirConsumerDefinition()
                .getEnvironmentActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(NitrogenAirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myConsumer.getNitorgenAirConsumerDefinition().getEnvironmentMaxFlowRate(myIndex);
    }

    public NitrogenAirConsumer getInput() {
        return myConsumer;
    }

    public BioModule getInputModule() {
        return (BioModule) (myConsumer);
    }

    public int getIndex() {
        return myIndex;
    }
}