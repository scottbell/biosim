package biosim.server.sensor.power;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.PowerProducer;
import biosim.idl.sensor.power.PowerOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class PowerOutFlowRateSensorImpl extends GenericSensorImpl implements PowerOutFlowRateSensorOperations{
	private PowerProducer myProducer;
	private int myIndex;
	
	public PowerOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getPowerOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(PowerProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getPowerOutputMaxFlowRate(myIndex);
	}
	
	public PowerProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
}
