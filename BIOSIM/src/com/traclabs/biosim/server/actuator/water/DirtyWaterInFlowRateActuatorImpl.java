package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;

public class DirtyWaterInFlowRateActuatorImpl extends GenericActuatorImpl implements DirtyWaterInFlowRateActuatorOperations{
	private DirtyWaterConsumer myConsumer;
	private int myIndex;
	
	public DirtyWaterInFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setDirtyWaterInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(DirtyWaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public DirtyWaterConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getDirtyWaterInputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (DirtyWaterInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "DirtyWaterInFlowRateActuator"+getID();
	}
}
