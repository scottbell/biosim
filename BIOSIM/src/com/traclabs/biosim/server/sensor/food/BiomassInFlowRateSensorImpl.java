package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;

public class BiomassInFlowRateSensorImpl extends GenericSensorImpl implements BiomassInFlowRateSensorOperations{
	private BiomassConsumer myConsumer;
	private int myIndex;
	
	public BiomassInFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getBiomassInputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(BiomassConsumer pConsumer, int pIndex){
		myConsumer = pConsumer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myConsumer.getBiomassInputMaxFlowRate(myIndex);
	}
	
	public BiomassConsumer getInput(){
		return myConsumer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (BiomassInFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassInFlowRateSensor"+getID();
	}
}
