package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.framework.*;

public class H2InFlowRateSensorImpl extends GenericSensorImpl implements H2InFlowRateSensorOperations{
	private H2Consumer myConsumer;
	private int myIndex;
	
	public H2InFlowRateSensorImpl(int pID){
		super(pID);
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
	
	protected BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (H2InFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "H2InFlowRateSensor"+getID();
	}
}
