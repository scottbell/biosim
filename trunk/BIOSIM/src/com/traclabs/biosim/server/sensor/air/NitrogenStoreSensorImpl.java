package biosim.server.sensor.air;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.air.NitrogenStoreSensorOperations;
import biosim.idl.simulation.air.NitrogenStore;
import biosim.server.sensor.framework.GenericSensorImpl;

public abstract class NitrogenStoreSensorImpl extends GenericSensorImpl implements NitrogenStoreSensorOperations{
	private NitrogenStore myNitrogenStore;
	
	public NitrogenStoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(NitrogenStore source){
		myNitrogenStore = source;
	}
	
	public NitrogenStore getInput(){
		return myNitrogenStore;
	}
	
	public float getMax(){
		return myNitrogenStore.getCapacity();
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
