package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public abstract class EnvironmentSensorImpl extends GenericSensorImpl implements EnvironmentSensorOperations{
	protected SimEnvironment myEnvironment;
	
	public EnvironmentSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(SimEnvironment source){
		myEnvironment = source;
	}
	
	public SimEnvironment getInput(){
		return myEnvironment;
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
