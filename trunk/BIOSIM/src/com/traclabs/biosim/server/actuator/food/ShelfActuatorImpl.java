package biosim.server.actuator.food;

import biosim.idl.actuator.food.ShelfActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.simulation.food.BiomassRS;
import biosim.idl.simulation.food.Shelf;
import biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class ShelfActuatorImpl extends GenericActuatorImpl implements ShelfActuatorOperations{
	protected Shelf myShelf;
	private BiomassRS myModule;
	
	public ShelfActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	public void setOutput(BiomassRS pBiomassRS, int shelfIndex){
		myShelf = pBiomassRS.getShelf(shelfIndex);
		myModule = pBiomassRS;
	}
	
	public Shelf getOutput(){
		return myShelf;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myModule);
	}
}
