package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;
import biosim.idl.framework.*;

public abstract class AirRSSensorImpl extends GenericSensorImpl implements AirRSSensorOperations{
	protected AirRS myAirRS;
	
	public AirRSSensorImpl(int pID){
		super(pID);
	}

	public void setInput(AirRS source){
		myAirRS = source;
	}
	
	public AirRS getInput(){
		return myAirRS;
	}
	
	/**
	* Returns the name of this module (AirRSSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "AirRSSensor"+getID();
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(getInput());
	}
}
