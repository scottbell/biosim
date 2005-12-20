package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorOperations;

public class HarvestingActuatorImpl extends ShelfActuatorImpl implements
        HarvestingActuatorOperations {
    public HarvestingActuatorImpl(int pID, String pName) {
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