package biosim.server.sensor.power;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.framework.*;

public class PowerOutFlowRateSensorImpl extends GenericSensorImpl implements PowerOutFlowRateSensorOperations{
	private PowerProducer myProducer;
	private int myIndex;
	
	public PowerOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getPowerOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(PowerProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public PowerProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (PowerOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerOutFlowRateSensor"+getID();
	}
}
