package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.util.log.*;
import biosim.idl.sensor.framework.*;

public abstract class GenericSensorImpl extends BioModuleImpl implements GenericSensorOperations{
	protected float myValue;
	private LogNode valueNode;
	
	public GenericSensorImpl(int pID){
		super(pID);
	}
	
	protected abstract void gatherData();
	protected abstract void notifyListeners();
	
	public float getValue(){
		return myValue;
	}
	
	public float getMax(){
		return 0f;
	}
	
	public float getMin(){
		return 0f;
	}
	
	public void tick(){
		super.tick();
		try{
			gatherData();
			notifyListeners();
		}
		catch (Exception e){
			System.out.println(getModuleName()+" had an exception: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	* Returns the name of this module (GenericSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GenericSensor"+getID();
	}
	
	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			LogNode valueNodeHead = myLog.addChild("value");
			valueNode = valueNodeHead.addChild(""+getValue());
			logInitialized = true;
		}
		else{
			valueNode.setValue(""+getValue());
		}
		sendLog(myLog);
	}
}
