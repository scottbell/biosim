package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;

public class BiomassOutFlowRateActuatorImpl extends GenericActuatorImpl implements BiomassOutFlowRateActuatorOperations{
	private BiomassProducer myProducer;
	private int myIndex;
	
	public BiomassOutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setBiomassOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(BiomassProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BiomassProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (BiomassOutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassOutFlowRateActuator"+getID();
	}
}
