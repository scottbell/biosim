package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;
import java.util.*;

public abstract class GenericSensor extends BioModuleImpl implements GenericSensorOperations{
	private List listeners;
	
	public GenericSensor(int pID){
		super(pID);
		listeners = new Vector();
	}
	
	protected abstract void gatherData();
	protected abstract void processData();
	protected abstract void notifyListeners();
	
	public abstract double getValue();
	
	public void registerListener(){
	}
}
