package biosim.server.actuator.framework;

import biosim.server.framework.*;
import biosim.idl.actuator.framework.*;

public abstract class GenericActuatorImpl extends BioModuleImpl implements GenericActuatorOperations{
	public GenericActuatorImpl(int pID){
		super(pID);
	}
	
	protected abstract void gatherData();
	protected abstract void processData();
	public abstract double getValue();
	public void tick(){
		gatherData();
		processData();
	}
}
