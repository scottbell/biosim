package biosim.server.sensor.food;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.food.*;
import biosim.idl.framework.*;

public class BiomassOutFlowRateSensorImpl extends GenericSensorImpl implements BiomassOutFlowRateSensorOperations{
	private BiomassProducer myProducer;
	private int myIndex;
	
	public BiomassOutFlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getBiomassOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(BiomassProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getBiomassOutputMaxFlowRate(myIndex);
	}
	
	public BiomassProducer getInput(){
		return myProducer;
	}
	
	public int getIndex(){
		return myIndex;
	}
	
	/**
	* Returns the name of this module (BiomassOutFlowRateSensor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassOutFlowRateSensor"+getID();
	}
}
