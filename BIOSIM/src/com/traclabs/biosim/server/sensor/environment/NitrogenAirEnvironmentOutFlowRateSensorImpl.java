package biosim.server.sensor.environment;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.NitrogenAirProducer;
import biosim.idl.sensor.environment.NitrogenAirEnvironmentOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenAirEnvironmentOutFlowRateSensorImpl extends GenericSensorImpl implements NitrogenAirEnvironmentOutFlowRateSensorOperations{
	private NitrogenAirProducer myProducer;
	private int myIndex;
	
	public NitrogenAirEnvironmentOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getNitrogenAirEnvironmentOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(NitrogenAirProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getNitrogenAirEnvironmentOutputMaxFlowRate(myIndex);
	}
	
	public NitrogenAirProducer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
}
