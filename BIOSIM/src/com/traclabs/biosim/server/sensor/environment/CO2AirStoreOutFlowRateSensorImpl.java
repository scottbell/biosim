package biosim.server.sensor.environment;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.framework.*;

public class CO2AirStoreOutFlowRateSensorImpl extends GenericSensorImpl implements CO2AirStoreOutFlowRateSensorOperations{
	private CO2AirProducer myProducer;
	private int myIndex;
	
	public CO2AirStoreOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2AirStoreOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(CO2AirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getCO2AirStoreOutputMaxFlowRate(myIndex);
	}
	
	public CO2AirProducer getInput(){
		return myProducer;
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (CO2AirStoreOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2AirStoreOutFlowRateSensor"+getID();
	}
}
