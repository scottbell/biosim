package biosim.server.sensor.air;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.H2Consumer;
import biosim.idl.sensor.air.H2InFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class H2InFlowRateSensorImpl extends GenericSensorImpl implements H2InFlowRateSensorOperations{
	private H2Consumer myConsumer;
	private int myIndex;
	
	public H2InFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getH2InputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(H2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getH2InputMaxFlowRate(myIndex);
	}
	
	public H2Consumer getInput(){
		return myConsumer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
}
