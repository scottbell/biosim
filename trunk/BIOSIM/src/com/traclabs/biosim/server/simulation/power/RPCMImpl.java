/**
 * 
 */
package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerProducerOperations;
import com.traclabs.biosim.idl.simulation.power.RPCMOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * @author scott
 *
 */
public class RPCMImpl extends SimBioModuleImpl implements RPCMOperations,
		PowerConsumerOperations, PowerProducerOperations {
	
	private boolean myOverTripped = false;
	private boolean myUnderTripped = false;
	
	private static final float TRIP_THRESHOLD = 5;
	
	 //Consumers, Producers
    private PowerProducerDefinitionImpl myPowerProducerDefinitionImpl;
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

	
	public RPCMImpl(int pID, String pName) {
		super(pID, pName);
        myPowerProducerDefinitionImpl = new PowerProducerDefinitionImpl();
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
	}

	public boolean isOverTripped() {
		return myOverTripped;
	}

	public boolean isUnderTripped() {
		return myUnderTripped;
	}

	public boolean[] getSwitchStatuses() {
		boolean[] switchStatuses = new boolean[myPowerProducerDefinitionImpl.getFlowRateCardinality()];
		for (int i = 0; i < switchStatuses.length; i++)
			switchStatuses[i] = myPowerProducerDefinitionImpl.getDesiredFlowRate(i) > 0;
	    return switchStatuses;
	}
	
	public void turnOff(){
		for (int i = 0; i < myPowerProducerDefinitionImpl.getFlowRateCardinality(); i++)
			myPowerProducerDefinitionImpl.setDesiredFlowRate(0f, i);
	}
	
	public void reset() {
        super.reset();
        myOverTripped = false;
        myUnderTripped = false;
        myPowerProducerDefinitionImpl.reset();
        myPowerConsumerDefinitionImpl.reset();
    }
	
	public void clear(){
    	myUnderTripped = false;
    	myOverTripped = false;
	}
	
	public void tick() {
        super.tick();
        if (myUnderTripped || myOverTripped)
        	return;
        float powerGathered = myPowerConsumerDefinitionImpl.getMostResourceFromStores();
        //check to see if difference is greater than threshold
        if ((powerGathered - TRIP_THRESHOLD) > myPowerProducerDefinitionImpl.getTotalDesiredFlowRate()){
        	myOverTripped = true;
        	turnOff();
            myLogger.info("RPCM over tripped");
        }
        else if ((powerGathered + TRIP_THRESHOLD) < myPowerProducerDefinitionImpl.getTotalDesiredFlowRate()){
        	myUnderTripped = true;
        	turnOff();
            myLogger.info("RPCM under tripped");
        }
        myPowerProducerDefinitionImpl.pushResourceToStores(powerGathered);
    }

	public void setSwitches(boolean[] switchValues) {
		if (switchValues.length != myPowerProducerDefinitionImpl.getFlowRateCardinality())
			return;
		for (int i = 0; i < switchValues.length; i++){
			//set to max
			if (switchValues[i])
				myPowerProducerDefinitionImpl.setDesiredFlowRate(myPowerProducerDefinitionImpl.getMaxFlowRate(i), i);
			//set to 0
			else
				myPowerProducerDefinitionImpl.setDesiredFlowRate(0, i);
		}
	}

	public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
	}

	public PowerProducerDefinition getPowerProducerDefinition() {
        return myPowerProducerDefinitionImpl.getCorbaObject();
	}

}
