package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public abstract class ShelfSensorImpl extends GenericSensorImpl implements ShelfSensorOperations{
	protected Shelf myShelf;
	private BiomassRS myBiomassRS;
	
	public ShelfSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(BiomassRS pBiomassRS, int shelfIndex){
		myShelf = pBiomassRS.getShelf(shelfIndex);
		myBiomassRS = pBiomassRS;
	}
	
	public Shelf getInput(){
		return myShelf;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myBiomassRS);
	}
}
