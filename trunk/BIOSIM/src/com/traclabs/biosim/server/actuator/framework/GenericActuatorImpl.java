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
	
	public GenericActuatorImpl(int pID, String pName){
		super(pID, pName);
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
	
	protected abstract BioModule getOutputModule();
	
	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			LogNode outputNodeHead = myLog.addChild("output");
			outputNode = outputNodeHead.addChild(""+getOutputModule().getModuleName());
			LogNode valueNodeHead = myLog.addChild("value");
			valueNode = valueNodeHead.addChild(""+getValue());
			logInitialized = true;
		}
		else{
			outputNode.setValue(""+getOutputModule().getModuleName());
			valueNode.setValue(""+getValue());
		}
		sendLog(myLog);
	}
}
