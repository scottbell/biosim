package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;

public abstract class GenericSensorImpl extends BioModuleImpl implements GenericSensorOperations{
	protected float myValue;
	
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
