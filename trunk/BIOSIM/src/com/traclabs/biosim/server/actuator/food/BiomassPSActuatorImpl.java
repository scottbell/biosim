package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.BiomassPSActuatorOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassPS;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class BiomassPSActuatorImpl extends GenericActuatorImpl
        implements BiomassPSActuatorOperations {
    protected BiomassPS myBiomassPS;

    public BiomassPSActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    public void setOutput(BiomassPS source) {
        myBiomassPS = source;
    }

    public BiomassPS getOutput() {
        return myBiomassPS;
    }
}