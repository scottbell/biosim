package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;

public class BiomassInFlowRateActuatorImpl extends GenericActuatorImpl implements BiomassInFlowRateActuatorOperations{
	private BiomassConsumer myConsumer;
	private int myIndex;
	
	public BiomassInFlowRateActuatorImpl(int pID){
		super(pID);
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
	
	protected BioModule getOutputModule(){
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
	
	/**
	* Returns the name of this module (BiomassInFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassInFlowRateActuator"+getID();
	}
}
