package biosim.server.actuator.framework;

import biosim.server.framework.*;
import biosim.idl.actuator.framework.*;

public abstract class GenericActuatorImpl extends BioModuleImpl implements GenericActuatorOperations{
	protected float myValue;
	protected boolean newValue = false;
	
	public GenericActuatorImpl(int pID){
		super(pID);
	}
	
	public void setValue(float pValue){
		myValue = pValue;
		newValue = true;
	}
	
	public float getValue(){
		return myValue;
	}
	
	public void tick(){
		if (newValue){
			processData();
			newValue = false;
		}
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
