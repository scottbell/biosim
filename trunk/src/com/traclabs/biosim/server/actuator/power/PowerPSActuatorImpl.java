package com.traclabs.biosim.server.actuator.power;

import com.traclabs.biosim.idl.actuator.power.PowerPSActuatorOperations;
import com.traclabs.biosim.idl.simulation.power.PowerPS;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class PowerPSActuatorImpl extends GenericActuatorImpl implements
        PowerPSActuatorOperations {
    protected PowerPS myPowerPS;

    public PowerPSActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    public void setOutput(PowerPS source) {
        myPowerPS = source;
    }

    public PowerPS getOutput() {
        return myPowerPS;
    }
}