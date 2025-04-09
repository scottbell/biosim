package com.traclabs.biosim.server.sensor.food;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.food.BiomassPS;
import com.traclabs.biosim.server.simulation.food.Shelf;

public abstract class ShelfSensor extends GenericSensor {
    protected Shelf myShelf;

    private BiomassPS myBiomassPS;

    public ShelfSensor(int pID, String pName) {
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

    public IBioModule getInputModule() {
        return (myBiomassPS);
    }
}