package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.NitrogenAirConsumer;
import biosim.idl.sensor.environment.NitrogenAirStoreInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenAirStoreInFlowRateSensorImpl extends GenericSensorImpl implements NitrogenAirStoreInFlowRateSensorOperations{
	private NitrogenAirConsumer myConsumer;
	private int myIndex;
	
	public NitrogenAirStoreInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getNitrogenAirStoreInputActualFlowRate(myIndex);
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
