package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public class O2InFlowRateSensorImpl extends GenericSensorImpl implements O2InFlowRateSensorOperations{
	private O2Consumer myConsumer;
	private int myIndex;
	
	public O2InFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){	
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(O2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public O2Consumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}
