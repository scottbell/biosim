package biosim.server.sensor.food;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.food.ShelfSensorOperations;
import biosim.idl.simulation.food.BiomassRS;
import biosim.idl.simulation.food.Shelf;
import biosim.server.sensor.framework.GenericSensorImpl;

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
