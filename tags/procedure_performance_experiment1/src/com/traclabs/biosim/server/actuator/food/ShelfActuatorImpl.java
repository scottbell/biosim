package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.ShelfActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.food.BiomassPS;
import com.traclabs.biosim.idl.simulation.food.Shelf;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class ShelfActuatorImpl extends GenericActuatorImpl implements
        ShelfActuatorOperations {
    protected Shelf myShelf;

    private BiomassPS myModule;

    public ShelfActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    public void setOutput(BiomassPS pBiomassPS, int shelfIndex) {
        myShelf = pBiomassPS.getShelf(shelfIndex);
        myModule = pBiomassPS;
        myValue = shelfIndex;
    }

    public Shelf getOutput() {
        return myShelf;
    }

    public BioModule getOutputModule() {
        return (myModule);
    }
}