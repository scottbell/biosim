package biosim.server.sensor.framework;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;

public abstract class GenericSensorImpl extends BioModuleImpl implements GenericSensorOperations{
	
	public GenericSensorImpl(int pID){
		super(pID);
	}
	
	protected abstract void gatherData();
	protected abstract void processData();
	protected abstract void notifyListeners();
	public abstract double getValue();
}
