package biosim.server.sensor.food;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.food.BiomassRSSensorOperations;
import biosim.idl.simulation.food.BiomassRS;
import biosim.server.sensor.framework.GenericSensorImpl;

public abstract class BiomassRSSensorImpl extends GenericSensorImpl implements BiomassRSSensorOperations{
	protected BiomassRS myBiomassRS;
	
	public BiomassRSSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(BiomassRS source){
		myBiomassRS = source;
	}
	
	public BiomassRS getInput(){
		return myBiomassRS;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
