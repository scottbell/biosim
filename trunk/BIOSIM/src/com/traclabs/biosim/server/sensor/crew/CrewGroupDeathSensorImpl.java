package biosim.server.sensor.crew;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.framework.*;

public class CrewGroupDeathSensorImpl extends CrewGroupSensorImpl implements CrewGroupDeathSensorOperations{
	public CrewGroupDeathSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		boolean preFilteredBooleanValue = getInput().isDead();
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
