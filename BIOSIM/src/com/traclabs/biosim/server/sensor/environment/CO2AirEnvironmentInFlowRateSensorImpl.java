package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;

public class CO2AirEnvironmentInFlowRateSensorImpl extends GenericSensorImpl implements CO2AirEnvironmentInFlowRateSensorOperations{
	private CO2AirConsumer myConsumer;
	private int myIndex;
	
	public CO2AirEnvironmentInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getCO2AirEnvironmentInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(CO2AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public CO2AirConsumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}
