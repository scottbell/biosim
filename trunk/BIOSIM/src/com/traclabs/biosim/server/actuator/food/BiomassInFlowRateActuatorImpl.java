package biosim.server.actuator.food;

import biosim.idl.actuator.food.BiomassInFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.BiomassConsumer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class BiomassInFlowRateActuatorImpl extends GenericActuatorImpl implements BiomassInFlowRateActuatorOperations{
	private BiomassConsumer myConsumer;
	private int myIndex;
	
	public BiomassInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setBiomassInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(BiomassConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public BiomassConsumer getOutput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getBiomassInputMaxFlowRate(myIndex);
	}
}
