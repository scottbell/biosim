package biosim.server.actuator.food;

import biosim.idl.actuator.food.HarvestingActuatorOperations;

public class HarvestingActuatorImpl extends ShelfActuatorImpl implements HarvestingActuatorOperations{
	public HarvestingActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		//harvest crops
		myShelf.harvest();
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}
	
	public float getMax(){
		return 1f;
	}
}
