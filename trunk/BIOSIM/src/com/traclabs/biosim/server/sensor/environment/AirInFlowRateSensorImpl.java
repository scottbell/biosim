package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class AirInFlowRateSensorImpl extends GenericSensorImpl implements AirInFlowRateSensorOperations{
	private AirConsumer myConsumer;
	private int myIndex;
	
	public AirInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getAirInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public AirConsumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (AirInFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "AirInFlowRateSensor"+getID();
	}
}
