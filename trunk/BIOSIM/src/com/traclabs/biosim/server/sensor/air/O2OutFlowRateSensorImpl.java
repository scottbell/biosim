package biosim.server.sensor.air;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.O2Producer;
import biosim.idl.sensor.air.O2OutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class O2OutFlowRateSensorImpl extends GenericSensorImpl implements O2OutFlowRateSensorOperations{
	private O2Producer myProducer;
	private int myIndex;
	
	public O2OutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getO2OutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(O2Producer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getO2OutputMaxFlowRate(myIndex);
	}
	
	public O2Producer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
}
