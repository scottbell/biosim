package biosim.server.sensor;

import biosim.server.framework.*;
import biosim.idl.sensor.framework.*;
	public abstract class GenericSensor extends BioModuleImpl implements GenericSensorOperations{
	public GenericSensor(int pID){
		super(pID);
	}
}
