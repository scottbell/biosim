package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public class PlantingActuatorImpl extends ShelfActuatorImpl implements PlantingActuatorOperations{
	private PlantType myType = PlantType.UNKNOWN_PLANT;
	
	public PlantingActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		//replant crops
		myShelf.replant(myType, myFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}
	
	public float getMax(){
		return myShelf.getCropAreaTotal();
	}
	
	public void setPlantType(PlantType pType){
		myType = pType;
		newValue = true;
	}
}
