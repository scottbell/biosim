package biosim.server.sensor.food;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.BiomassProducer;
import biosim.idl.sensor.food.BiomassOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class BiomassOutFlowRateSensorImpl extends GenericSensorImpl implements BiomassOutFlowRateSensorOperations{
	private BiomassProducer myProducer;
	private int myIndex;
	
	public BiomassOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
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
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}
