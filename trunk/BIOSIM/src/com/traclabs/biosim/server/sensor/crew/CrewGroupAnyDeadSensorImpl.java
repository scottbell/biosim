package com.traclabs.biosim.server.sensor.crew;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorOperations;

public class CrewGroupAnyDeadSensorImpl extends CrewGroupSensorImpl implements CrewGroupAnyDeadSensorOperations{
	public CrewGroupAnyDeadSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		boolean preFilteredBooleanValue = getInput().anyDead();
		if (preFilteredBooleanValue)
			myValue = randomFilter(1);
		else
			myValue = randomFilter(0);
	}
	
	public float getMax(){
		return 1f;
	}
	
	protected void notifyListeners(){
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
