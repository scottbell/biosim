package biosim.server.actuator.water;

import biosim.idl.actuator.water.PotableWaterInFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.PotableWaterConsumer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class PotableWaterInFlowRateActuatorImpl extends GenericActuatorImpl implements PotableWaterInFlowRateActuatorOperations{
	private PotableWaterConsumer myConsumer;
	private int myIndex;
	
	public PotableWaterInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setPotableWaterInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(PotableWaterConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public PotableWaterConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getPotableWaterInputMaxFlowRate(myIndex);
	}
}
