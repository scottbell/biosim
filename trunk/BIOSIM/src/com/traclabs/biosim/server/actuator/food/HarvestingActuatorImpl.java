package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

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
