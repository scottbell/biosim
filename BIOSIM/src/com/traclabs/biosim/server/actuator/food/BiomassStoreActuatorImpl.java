package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.simulation.food.*;

public abstract class BiomassStoreActuatorImpl extends GenericActuatorImpl implements BiomassStoreActuatorOperations{
	protected BiomassStore myBiomassStore;
	
	public BiomassStoreActuatorImpl(int pID){
		super(pID);
	}

	protected abstract void processData();
	protected abstract void notifyListeners();

	public void setOutput(BiomassStore source){
		myBiomassStore = source;
	}
	
	public BiomassStore getOutput(){
		return myBiomassStore;
	}
	
	/**
	* Returns the name of this module (BiomassStoreActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassStoreActuator"+getID();
	}
}
