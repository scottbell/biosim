package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreInFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenAirStoreInFlowRateSensorImpl extends GenericSensorImpl
        implements NitrogenAirStoreInFlowRateSensorOperations {
    private NitrogenAirConsumer myConsumer;

    private int myIndex;

    public NitrogenAirStoreInFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getNitrogenAirConsumerDefinition()
                .getStoreActualFlowRate(myIndex);
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
        return myConsumer.getNitrogenAirConsumerDefinition()
                .getStoreMaxFlowRate(myIndex);
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