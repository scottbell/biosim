package biosim.server.sensor.air;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.CO2Consumer;
import biosim.idl.sensor.air.CO2InFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class CO2InFlowRateSensorImpl extends GenericSensorImpl implements CO2InFlowRateSensorOperations{
	private CO2Consumer myConsumer;
	private int myIndex;
	
	public CO2InFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getCO2InputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(CO2Consumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getCO2InputMaxFlowRate(myIndex);
	}
	
	public CO2Consumer getInput(){
		return myConsumer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myConsumer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
}
