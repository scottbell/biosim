package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public abstract class BiomassStoreSensorImpl extends GenericSensorImpl implements BiomassStoreSensorOperations{
	protected BiomassStore myBiomassStore;
	
	public BiomassStoreSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(BiomassStore source){
		myBiomassStore = source;
	}
	
	public BiomassStore getInput(){
		return myBiomassStore;
	}
	
	public float getMax(){
		return myBiomassStore.getCapacity();
	}
	
	/**
	* Returns the name of this module (BiomassStoreSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassStoreSensor"+getID();
	}
}
