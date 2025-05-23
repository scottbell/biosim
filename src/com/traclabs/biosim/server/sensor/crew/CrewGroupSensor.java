package com.traclabs.biosim.server.sensor.crew;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;

public abstract class CrewGroupSensor extends GenericSensor {
    protected CrewGroup myCrewGroup;

    public CrewGroupSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public CrewGroup getInput() {
        return myCrewGroup;
    }

    public void setInput(CrewGroup source) {
        myCrewGroup = source;
    }

    public IBioModule getInputModule() {
        return getInput();
    }
}