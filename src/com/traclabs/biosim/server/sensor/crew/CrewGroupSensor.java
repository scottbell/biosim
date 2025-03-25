package com.traclabs.biosim.server.sensor.crew;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;

public abstract class CrewGroupSensor extends GenericSensor implements
        CrewGroupSensorOperations {
    protected CrewGroup myCrewGroup;

    public CrewGroupSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected abstract void notifyListeners();

    public void setInput(CrewGroup source) {
        myCrewGroup = source;
    }

    public CrewGroup getInput() {
        return myCrewGroup;
    }

    public BioModule getInputModule() {
        return getInput();
    }
}