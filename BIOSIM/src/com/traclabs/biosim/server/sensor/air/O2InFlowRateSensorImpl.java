package biosim.server.sensor.air;

import biosim.server.sensor.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.simulation.air.*;

public class O2FlowRateSensorImpl extends GenericSensorImpl implements O2FlowRateSensorOperations{
	private O2Producer mySource;
	private O2Consumer myDestination;
	private int myIndexSource;
	private int myIndexDestination;
	
	public O2FlowRateSensorImpl(int pID){
		super(pID);
	}

	protected void gatherData(){
		
	}
	
	protected void notifyListeners(){
		//does nothing right now
	}

	public void setInput(O2Producer pSource, int pIndexSource, O2Consumer pDestination, int pIndexDestination){
		mySource = pSource;
		myIndexSource = pIndexSource;
		myDestination = pDestination;
		myIndexDestination = pIndexDestination;
	}
	
	public O2Producer getProducer(){
		return mySource;
	}
	
	public O2Consumer getConsumer(){
		return myDestination;
	}
	
	public int getIndexProducer(){
		return myIndexSource;
	}
	
	public int getIndexConsumer(){
		return myIndexDestination;
	}
}
