package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;

public abstract class GenericSensorImpl extends BioModuleImpl implements GenericSensorOperations{
	protected double myValue;
	
	public GenericSensorImpl(int pID){
		super(pID);
	}
	
	protected abstract void gatherData();
	protected abstract void processData();
	protected abstract void notifyListeners();
	
	public double getValue(){
		return myValue;
	}
	
	public void tick(){
		gatherData();
		processData();
		notifyListeners();
	}
}
