package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.food.BiomassPS;
import com.traclabs.biosim.server.simulation.food.Shelf;

public abstract class ShelfActuator extends GenericActuator {
    protected Shelf myShelf;

    private BiomassPS myModule;

    public ShelfActuator(int pID, String pName) {
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