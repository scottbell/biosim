package biosim.server.actuator.framework;

import biosim.server.framework.*;
import biosim.idl.actuator.framework.*;

public abstract class GenericActuatorImpl extends BioModuleImpl implements GenericActuatorOperations{
	protected double myValue;
	
	public GenericActuatorImpl(int pID){
		super(pID);
	}
	
	public void setValue(double pValue){
		myValue = pValue;
	}
	
	public void tick(){
		processData();
	}
	
	protected abstract void processData();
	
	/**
	* Returns the name of this module (GenericActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GenericActuator"+getID();
	}
}
