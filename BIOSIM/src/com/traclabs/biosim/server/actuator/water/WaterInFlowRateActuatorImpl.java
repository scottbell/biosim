package biosim.server.actuator.water;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.water.*;
import biosim.idl.framework.*;

public class WaterInFlowRateActuatorImpl extends GenericActuatorImpl implements WaterInFlowRateActuatorOperations{
	private WaterConsumer myConsumer;
	private int myIndex;
	
	public WaterInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setWaterInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(WaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public WaterConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getWaterInputMaxFlowRate(myIndex);
	}
}
