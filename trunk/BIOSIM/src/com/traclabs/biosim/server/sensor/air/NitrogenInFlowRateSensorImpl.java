package biosim.server.sensor.air;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.NitrogenConsumer;
import biosim.idl.sensor.air.NitrogenInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenInFlowRateSensorImpl extends GenericSensorImpl implements NitrogenInFlowRateSensorOperations{
	private NitrogenConsumer myConsumer;
	private int myIndex;
	
	public NitrogenInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getNitrogenInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(NitrogenConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getNitrogenInputMaxFlowRate(myIndex);
	}
	
	public NitrogenConsumer getInput(){
		return myConsumer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
}
