package biosim.server.sensor.waste;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.waste.*;
import biosim.idl.framework.*;

public class DryWasteInFlowRateSensorImpl extends GenericSensorImpl implements DryWasteInFlowRateSensorOperations{
	private DryWasteConsumer myConsumer;
	private int myIndex;
	
	public DryWasteInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getDryWasteInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(DryWasteConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public DryWasteConsumer getInput(){
		return myConsumer;
	}
	
	public float getMax(){
		return myConsumer.getDryWasteInputMaxFlowRate(myIndex);
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}
