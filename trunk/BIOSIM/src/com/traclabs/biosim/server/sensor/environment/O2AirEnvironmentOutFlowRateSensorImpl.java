package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class O2AirEnvironmentOutFlowRateSensorImpl extends GenericSensorImpl implements O2AirEnvironmentOutFlowRateSensorOperations{
	private O2AirProducer myProducer;
	private int myIndex;
	
	public O2AirEnvironmentOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getO2AirEnvironmentOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(O2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public O2AirProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2AirEnvironmentOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirEnvironmentOutFlowRateSensor"+getID();
	}
}
