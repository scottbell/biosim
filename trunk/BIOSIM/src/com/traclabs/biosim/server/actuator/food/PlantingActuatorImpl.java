package biosim.server.actuator.food;

import biosim.idl.actuator.food.PlantingActuatorOperations;
import biosim.idl.simulation.food.PlantType;

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
