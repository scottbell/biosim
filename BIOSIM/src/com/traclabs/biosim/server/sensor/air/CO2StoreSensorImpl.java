package com.traclabs.biosim.server.sensor.air;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.air.CO2StoreSensorOperations;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public abstract class CO2StoreSensorImpl extends GenericSensorImpl implements CO2StoreSensorOperations{
	private CO2Store myCO2Store;
	
	public CO2StoreSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected abstract void gatherData();
	protected abstract void notifyListeners();

	public void setInput(CO2Store source){
		myCO2Store = source;
	}
	
	public CO2Store getInput(){
		return myCO2Store;
	}
	
	public float getMax(){
		return myCO2Store.getCapacity();
	}
	
	public BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
