package com.traclabs.biosim.server.actuator.food;

import com.traclabs.biosim.idl.actuator.food.PlantingActuatorOperations;
import com.traclabs.biosim.idl.simulation.food.PlantType;

public class PlantingActuatorImpl extends ShelfActuatorImpl implements
        PlantingActuatorOperations {
    private PlantType myType = PlantType.UNKNOWN_PLANT;

    public PlantingActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        //replant crops
        myShelf.replant(myType, myFilteredValue);
    }



    public float getMax() {
        return myShelf.getCropAreaTotal();
    }

    public void setPlantType(PlantType pType) {
        myType = pType;
        newValueSet = true;
    }
}