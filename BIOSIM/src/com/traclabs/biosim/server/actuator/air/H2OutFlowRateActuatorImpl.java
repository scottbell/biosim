package biosim.server.actuator.air;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.framework.*;

public class H2OutFlowRateActuatorImpl extends GenericActuatorImpl implements H2OutFlowRateActuatorOperations{
	private H2Producer myProducer;
	private int myIndex;
	
	public H2OutFlowRateActuatorImpl(int pID){
		super(pID);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setH2OutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(H2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	protected BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public H2Producer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myProducer.getH2OutputMaxFlowRate(myIndex);
	}
	
	/**
	* Returns the name of this module (H2OutFlowRateActuatorImpl)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "H2OutFlowRateActuator"+getID();
	}
}
