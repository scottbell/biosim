package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.framework.*;

public class CO2OutFlowRateActuatorImpl extends GenericActuatorImpl implements CO2OutFlowRateActuatorOperations{
	private CO2Producer myProducer;
	private int myIndex;
	
	public CO2OutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setCO2OutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(CO2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public CO2Producer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getCO2OutputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (CO2OutFlowRateActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CO2OutFlowRateActuator"+getID();
	}
}
