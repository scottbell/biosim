package biosim.server.sensor.framework;

import biosim.idl.framework.BioModule;
import biosim.idl.sensor.framework.GenericSensorOperations;
import biosim.idl.util.log.LogNode;
import biosim.server.framework.BioModuleImpl;

public abstract class GenericSensorImpl extends BioModuleImpl implements GenericSensorOperations{
	protected float myValue;
	private LogNode valueNode;
	private LogNode inputNode;
	
	public GenericSensorImpl(int pID, String pName){
		super(pID, pName);
	}
	
	protected abstract void gatherData();
	protected abstract void notifyListeners();
	
	public float getValue(){
		return myValue;
	}
	
	public abstract float getMax();
	
	public float getMin(){
		return 0f;
	}
	
	public void tick(){
		super.tick();
		//System.out.println(getModuleName()+": moduleLogging="+moduleLogging);
		try{
			gatherData();
			notifyListeners();
		}
		catch (Exception e){
			System.out.println(getModuleName()+" had an exception: "+e);
			e.printStackTrace();
		}
	}
	
	public abstract BioModule getInputModule();
	
	public void log(){
		/*
		//If not initialized, fill in the log
			LogNode inputNodeHead = myLog.addChild("input");
			valueNode = valueNodeHead.addChild(""+getValue());
		*/
	}
}
