package biosim.server.sensor.power;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.simulation.power.*;

public abstract class PowerPSSensorImpl extends GenericSensorImpl implements PowerPSSensorOperations{
	protected PowerPS myPowerPS;
	
	public PowerPSSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(PowerPS source){
		myPowerPS = source;
	}
	
	public PowerPS getInput(){
		return myPowerPS;
	}
}
