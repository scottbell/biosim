package biosim.server.sensor.power;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.simulation.power.*;

public abstract class PowerStoreSensorImpl extends GenericSensorImpl implements PowerStoreSensorOperations{
	protected PowerStore myPowerStore;
	
	public PowerStoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(PowerStore source){
		myPowerStore = source;
	}
	
	public PowerStore getInput(){
		return myPowerStore;
	}
}
