package biosim.server.actuator.environment;

import biosim.idl.actuator.environment.NitrogenAirEnvironmentInFlowRateActuatorOperations;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.NitrogenAirConsumer;
import biosim.server.actuator.framework.GenericActuatorImpl;

public class NitrogenAirEnvironmentInFlowRateActuatorImpl extends GenericActuatorImpl implements NitrogenAirEnvironmentInFlowRateActuatorOperations{
	private NitrogenAirConsumer myConsumer;
	private int myIndex;
	
	public NitrogenAirEnvironmentInFlowRateActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void processData(){
		float myFilteredValue = randomFilter(myValue);
		getOutput().setNitrogenAirEnvironmentInputDesiredFlowRate(myFilteredValue, myIndex);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setOutput(NitrogenAirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public NitrogenAirConsumer getOutput(){
		return myConsumer;
	}
	
	public BioModule getOutputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public float getMax(){
		return myConsumer.getNitrogenAirEnvironmentInputMaxFlowRate(myIndex);
	}
}
