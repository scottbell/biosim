package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;

public class DirtyWaterOutFlowRateActuatorImpl extends GenericActuatorImpl implements DirtyWaterOutFlowRateActuatorOperations{
	private DirtyWaterProducer myProducer;
	private int myIndex;
	
	public DirtyWaterOutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setDirtyWaterOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(DirtyWaterProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public DirtyWaterProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (DirtyWaterOutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "DirtyWaterOutFlowRateActuator"+getID();
	}
}
