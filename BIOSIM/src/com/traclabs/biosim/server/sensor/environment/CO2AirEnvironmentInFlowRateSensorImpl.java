package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class CO2AirEnvironmentInFlowRateSensorImpl extends GenericSensorImpl implements CO2AirEnvironmentInFlowRateSensorOperations{
	private CO2AirConsumer myConsumer;
	private int myIndex;
	
	public CO2AirEnvironmentInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2AirEnvironmentInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(CO2AirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getCO2AirEnvironmentInputMaxFlowRate(myIndex);
	}
	
	public CO2AirConsumer getInput(){
		return myConsumer;
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (CO2AirEnvironmentInFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirEnvironmentInFlowRateSensor"+getID();
	}
}
