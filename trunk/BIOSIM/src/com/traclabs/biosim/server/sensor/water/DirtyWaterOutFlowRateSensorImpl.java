package biosim.server.sensor.water;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.water.*;
import biosim.idl.framework.*;

public class DirtyWaterOutFlowRateSensorImpl extends GenericSensorImpl implements DirtyWaterOutFlowRateSensorOperations{
	private DirtyWaterProducer myProducer;
	private int myIndex;
	
	public DirtyWaterOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		double preFilteredValue = getInput().getDirtyWaterOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(DirtyWaterProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public DirtyWaterProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (DirtyWaterOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "DirtyWaterOutFlowRateSensor"+getID();
	}
}
