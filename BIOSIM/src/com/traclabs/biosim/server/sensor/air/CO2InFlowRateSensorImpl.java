package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public class CO2InFlowRateSensorImpl extends GenericSensorImpl implements CO2InFlowRateSensorOperations{
	private CO2Consumer myConsumer;
	private int myIndex;
	
	public CO2InFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getCO2InputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(CO2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public CO2Consumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}
