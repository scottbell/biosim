package biosim.server.sensor.power;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.simulation.power.*;

public class PowerOutFlowRateSensorImpl extends GenericSensorImpl implements PowerOutFlowRateSensorOperations{
	private PowerProducer myProducer;
	private int myIndex;
	
	public PowerOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getPowerOutputActualFlowRate(myIndex);
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
}
