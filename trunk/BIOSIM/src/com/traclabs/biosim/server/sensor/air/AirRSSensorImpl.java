package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.AirRSSensorOperations;
import com.traclabs.biosim.idl.simulation.air.AirRS;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class AirRSSensorImpl extends GenericSensorImpl implements
        AirRSSensorOperations {
    protected AirRS myAirRS;

    public AirRSSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    public void setInput(AirRS source) {
        myAirRS = source;
    }

    public AirRS getInput() {
        return myAirRS;
    }

    public BioModule getInputModule() {
        return (BioModule) (getInput());
    }
}