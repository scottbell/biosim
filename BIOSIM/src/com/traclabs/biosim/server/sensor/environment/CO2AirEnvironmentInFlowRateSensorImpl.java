package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.CO2AirConsumer;
import biosim.idl.sensor.environment.CO2AirEnvironmentInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class CO2AirEnvironmentInFlowRateSensorImpl extends GenericSensorImpl implements CO2AirEnvironmentInFlowRateSensorOperations{
	private CO2AirConsumer myConsumer;
	private int myIndex;
	
	public CO2AirEnvironmentInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}
