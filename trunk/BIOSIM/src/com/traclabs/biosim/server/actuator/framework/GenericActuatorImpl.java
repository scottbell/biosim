package biosim.server.actuator.framework;

import biosim.server.framework.*;
import biosim.idl.actuator.framework.*;

public abstract class GenericActuator extends BioModuleImpl implements GenericActuatorOperations{
	public GenericActuator(int pID){
		super(pID);
	}
	
	protected abstract void gatherData();
	protected abstract void processData();
	protected abstract void notifyListeners();
	public abstract double getValue();
}
