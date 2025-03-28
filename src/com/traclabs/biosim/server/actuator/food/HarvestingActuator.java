package com.traclabs.biosim.server.actuator.food;


public class HarvestingActuator extends ShelfActuator {
    public HarvestingActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        //harvest crops
        myShelf.harvest();
    }


    public float getMax() {
        return 1f;
    }
}