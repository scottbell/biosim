package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public abstract class EnvironmentSensorImpl extends GenericSensorImpl implements EnvironmentSensorOperations{
	protected SimEnvironment myEnvironment;
	
	public EnvironmentSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(SimEnvironment source){
		myEnvironment = source;
	}
	
	public SimEnvironment getInput(){
		return myEnvironment;
	}
}
