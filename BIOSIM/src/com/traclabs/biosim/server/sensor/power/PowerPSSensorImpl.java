package biosim.server.sensor.power;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.framework.*;
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
	
	/**
	* Returns the name of this module (PowerPSSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerPSSensor"+getID();
	}
}
