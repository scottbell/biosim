package biosim.server.sensor.power;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.PowerConsumer;
import biosim.idl.sensor.power.PowerInFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class PowerInFlowRateSensorImpl extends GenericSensorImpl implements PowerInFlowRateSensorOperations{
	private PowerConsumer myConsumer;
	private int myIndex;
	
	public PowerInFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getPowerInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(PowerConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getPowerInputMaxFlowRate(myIndex);
	}
	
	public PowerConsumer getInput(){
		return myConsumer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}
