package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class O2AirStoreInFlowRateSensorImpl extends GenericSensorImpl implements O2AirStoreInFlowRateSensorOperations{
	private O2AirConsumer myConsumer;
	private int myIndex;
	
	public O2AirStoreInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getO2AirStoreInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(O2AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getO2AirEnvironmentInputMaxFlowRate(myIndex);
	}
	
	public O2AirConsumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2AirStoreInFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2AirStoreInFlowRateSensor"+getID();
	}
}
