package biosim.server.sensor.air;

import biosim.idl.framework.BioModule;
import biosim.idl.framework.NitrogenProducer;
import biosim.idl.sensor.air.NitrogenOutFlowRateSensorOperations;
import biosim.server.sensor.framework.GenericSensorImpl;

public class NitrogenOutFlowRateSensorImpl extends GenericSensorImpl implements NitrogenOutFlowRateSensorOperations{
	private NitrogenProducer myProducer;
	private int myIndex;
	
	public NitrogenOutFlowRateSensorImpl(int pID, String pName){
		super(pID, pName);
	}

	protected void gatherData(){
		float preFilteredValue = getInput().getNitrogenOutputActualFlowRate(myIndex);
		myValue = randomFilter(preFilteredValue);
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(NitrogenProducer pProducer, int pIndex){
		myProducer = pProducer;
		myIndex = pIndex;
	}
	
	public float getMax(){
		return myProducer.getNitrogenOutputMaxFlowRate(myIndex);
	}
	
	public NitrogenProducer getInput(){
		return myProducer;
	}
	
	public BioModule getInputModule(){
		return (BioModule)(myProducer);
	}
	
	public int getIndex(){
		return myIndex;
	}
	
}
