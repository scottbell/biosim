package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.simulation.food.*;

public abstract class BiomassRSSensorImpl extends GenericSensorImpl implements BiomassRSSensorOperations{
	protected BiomassRS myBiomassRS;
	
	public BiomassRSSensorImpl(int pID){
		super(pID);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(BiomassRS source){
		myBiomassRS = source;
	}
	
	public BiomassRS getInput(){
		return myBiomassRS;
	}
}
