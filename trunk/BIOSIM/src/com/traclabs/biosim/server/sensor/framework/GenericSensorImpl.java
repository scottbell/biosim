package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;

public abstract class GenericSensorImpl extends BioModuleImpl implements GenericSensorOperations{
	protected double myValue;
	
	public GenericSensorImpl(int pID){
		super(pID);
	}
	
	protected abstract void gatherData();
	protected abstract void notifyListeners();
	
	public double getValue(){
		return myValue;
	}
	
	public void tick(){
		gatherData();
		notifyListeners();
	}
	
	/**
	* Returns the name of this module (GenericSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "GenericSensor"+getID();
	}
}
