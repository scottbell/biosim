package biosim.server.actuator.framework;

import biosim.server.framework.*;
import biosim.idl.actuator.framework.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;

public abstract class GenericActuatorImpl extends BioModuleImpl implements GenericActuatorOperations{
	protected float myValue;
	protected boolean newValue = false;
	private LogNode valueNode;
	private LogNode outputNode;
	
	public GenericActuatorImpl(int pID){
		super(pID);
	}
	
	public void setValue(float pValue){
		myValue = pValue;
		newValue = true;
	}
	
	public abstract float getMax();
	
	public float getMin(){
		return 0f;
	}
	
	public float getValue(){
		return myValue;
	}
	
	public void tick(){
		super.tick();
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
	
	protected abstract BioModule getOutputModule();
	
	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			LogNode valueNodeHead = myLog.addChild("value");
			valueNode = valueNodeHead.addChild(""+getValue());
			LogNode outputNodeHead = myLog.addChild("output");
			outputNode = outputNodeHead.addChild(""+getOutputModule().getModuleName());
			logInitialized = true;
		}
		else{
			valueNode.setValue(""+getValue());
			outputNode.setValue(""+getOutputModule().getModuleName());
		}
		sendLog(myLog);
	}
}
