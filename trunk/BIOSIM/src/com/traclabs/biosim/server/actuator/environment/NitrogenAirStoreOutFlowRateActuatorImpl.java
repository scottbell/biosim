package biosim.server.actuator.environment;

import biosim.idl.actuator.environment.NitrogenAirStoreOutFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.NitrogenAirProducer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenAirStoreOutFlowRateActuatorImpl extends GenericActuatorImpl implements NitrogenAirStoreOutFlowRateActuatorOperations{
	private NitrogenAirProducer myProducer;
	private int myIndex;
	
	public NitrogenAirStoreOutFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setNitrogenAirStoreOutputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(NitrogenAirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myProducer);
	}
	
	public float getMax(){
		return myProducer.getNitrogenAirStoreOutputMaxFlowRate(myIndex);
	}
	
	public NitrogenAirProducer getOutput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
}
