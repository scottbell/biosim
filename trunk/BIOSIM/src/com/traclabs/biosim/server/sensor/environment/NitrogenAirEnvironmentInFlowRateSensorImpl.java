package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.NitrogenAirConsumer;
import biosim.idl.sensor.environment.NitrogenAirEnvironmentInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenAirEnvironmentInFlowRateSensorImpl extends GenericSensorImpl implements NitrogenAirEnvironmentInFlowRateSensorOperations{
	private NitrogenAirConsumer myConsumer;
	private int myIndex;
	
	public NitrogenAirEnvironmentInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getNitrogenAirEnvironmentInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(NitrogenAirConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getNitrogenAirEnvironmentInputMaxFlowRate(myIndex);
	}
	
	public NitrogenAirConsumer getInput(){
		return myConsumer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}
