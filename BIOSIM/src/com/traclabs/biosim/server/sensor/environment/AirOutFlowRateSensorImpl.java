package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class AirOutFlowRateSensorImpl extends GenericSensorImpl implements AirOutFlowRateSensorOperations{
	private AirProducer myProducer;
	private int myIndex;
	
	public AirOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getAirOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getAirOutputMaxFlowRate(myIndex);
	}
	
	public AirProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (AirOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "AirOutFlowRateSensor"+getID();
	}
}
