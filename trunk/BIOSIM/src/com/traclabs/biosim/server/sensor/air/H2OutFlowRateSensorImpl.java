package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.framework.*;

public class H2OutFlowRateSensorImpl extends GenericSensorImpl implements H2OutFlowRateSensorOperations{
	private H2Producer myProducer;
	private int myIndex;
	
	public H2OutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getH2OutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(H2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getH2OutputMaxFlowRate(myIndex);
	}
	
	public H2Producer getInput(){
		return myProducer;
	}
	
	protected BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (H2OutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "H2OutFlowRateSensor"+getID();
	}
}
