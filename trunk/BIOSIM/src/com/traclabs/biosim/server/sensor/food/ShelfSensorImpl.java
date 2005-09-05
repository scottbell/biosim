package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.food.ShelfSensorOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassPS;
import com.traclabs.biosim.idl.simulation.food.Shelf;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class ShelfSensorImpl extends GenericSensorImpl implements
        ShelfSensorOperations {
    protected Shelf myShelf;

    private BiomassPS myBiomassPS;

    public ShelfSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(BiomassPS pBiomassPS, int shelfIndex) {
        myShelf = pBiomassPS.getShelf(shelfIndex);
        myBiomassPS = pBiomassPS;
    }

    public Shelf getInput() {
        return myShelf;
    }

    public BioModule getInputModule() {
        return (myBiomassPS);
    }
}