package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.BiomassRSActuatorOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassRS;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class BiomassRSActuatorImpl extends GenericActuatorImpl
        implements BiomassRSActuatorOperations {
    protected BiomassRS myBiomassRS;

    public BiomassRSActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    public void setOutput(BiomassRS source) {
        myBiomassRS = source;
    }

    public BiomassRS getOutput() {
        return myBiomassRS;
    }
}