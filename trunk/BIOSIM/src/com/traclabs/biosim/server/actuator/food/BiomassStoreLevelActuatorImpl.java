package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.simulation.food.*;

public class BiomassStoreLevelActuatorImpl extends BiomassStoreActuatorImpl implements BiomassStoreLevelActuatorOperations{
	public BiomassStoreLevelActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setLevel(myFilteredValue);
	}
	
	protected void notifyListeners(){
	}
	
	/**
	* Returns the name of this module (BiomassStoreLevelActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassStoreLevelActuator"+getID();
	}
}
