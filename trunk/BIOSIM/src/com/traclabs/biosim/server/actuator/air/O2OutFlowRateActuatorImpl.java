package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.simulation.air.*;

public class O2OutFlowRateActuatorImpl extends GenericActuatorImpl implements O2OutFlowRateActuatorOperations{
	private O2Producer myProducer;
	private int myIndex;
	
	public O2OutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setO2OutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(O2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public O2Producer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (O2OutFlowRateActuatorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "O2OutFlowRateActuator"+getID();
	}
}
