package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.simulation.water.*;

public class PotableWaterStoreLevelActuatorImpl extends PotableWaterStoreActuatorImpl implements PotableWaterStoreLevelActuatorOperations{
	public PotableWaterStoreLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setLevel(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (PotableWaterStoreLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PotableWaterStoreLevelActuator"+getID();
	}
}
