package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;

public abstract class FlowRateSensorImpl extends GenericSensorImpl implements GenericSensorOperations{
	private LogNode valueNode;
	private LogNode outputNode;
	
	public FlowRateSensorImpl(int pID){
		super(pID);
	}
	
	protected abstract FlowRateControllable getFlowOuput();
	
	/**
	* Returns the name of this module (GenericSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FlowRateSensor"+getID();
	}
	
	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			LogNode valueNodeHead = myLog.addChild("value");
			valueNode = valueNodeHead.addChild(""+getValue());
			LogNode outputNodeHead = myLog.addChild("output");
			outputNode = outputNodeHead.addChild(""+getFlowOuput().getModuleName());
			logInitialized = true;
		}
		else{
			valueNode.setValue(""+getValue());
			outputNode.setValue(""+getFlowOuput().getModuleName());
		}
		sendLog(myLog);
	}
}
