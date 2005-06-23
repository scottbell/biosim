package com.traclabs.biosim.server.sensor.environment;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreOutFlowRateSensorOperations;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducer;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenAirStoreOutFlowRateSensorImpl extends GenericSensorImpl
        implements NitrogenAirStoreOutFlowRateSensorOperations {
    private NitrogenAirProducer myProducer;

    private int myIndex;

    public NitrogenAirStoreOutFlowRateSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void gatherData() {
        float preFilteredValue = getInput().getNitrogenAirProducerDefinition()
                .getStoreActualFlowRate(myIndex);
        myValue = randomFilter(preFilteredValue);
    }

    protected void notifyListeners() {
        //does nothing right now
    }

    public void setInput(NitrogenAirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
    }

    public float getMax() {
        return myProducer.getNitrogenAirProducerDefinition()
                .getStoreMaxFlowRate(myIndex);
    }

    public NitrogenAirProducer getInput() {
        return myProducer;
    }

    public BioModule getInputModule() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}