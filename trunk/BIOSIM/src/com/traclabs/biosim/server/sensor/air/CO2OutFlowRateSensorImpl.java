package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public class CO2OutFlowRateSensorImpl extends GenericSensorImpl implements CO2OutFlowRateSensorOperations{
	private CO2Producer myProducer;
	private int myIndex;
	
	public CO2OutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getCO2OutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(CO2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public CO2Producer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}
