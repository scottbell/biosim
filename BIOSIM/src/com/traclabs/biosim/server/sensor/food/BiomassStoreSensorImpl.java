package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public abstract class BiomassStoreSensorImpl extends GenericSensorImpl implements BiomassStoreSensorOperations{
	protected BiomassStore myBiomassStore;
	
	public BiomassStoreSensorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
