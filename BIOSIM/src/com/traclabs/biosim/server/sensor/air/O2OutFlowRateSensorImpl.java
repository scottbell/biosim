package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public class O2OutFlowRateSensorImpl extends GenericSensorImpl implements O2OutFlowRateSensorOperations{
	private O2Producer myProducer;
	private int myIndex;
	
	public O2OutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getO2OutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(O2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public O2Producer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2OutFlowRateSensorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2OutFlowRateSensorImpl"+getID();
	}
}
