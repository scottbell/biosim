package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.environment.EnvironmentSensorOperations;
import biosim.idl.simulation.environment.SimEnvironment;
import biosim.server.sensor.framework.GenericSensorImpl;

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
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
