/**
 * 
 */
package com.traclabs.biosim.server.simulation.power;

import java.util.Arrays;

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
	
	private boolean myOvertripped = false;
	private boolean myUndertripped = false;
	private boolean[] myInitalSwitches;
	private boolean[] mySwitches;
	
	private static final float TRIP_THRESHOLD = 5;
	
	 //Consumers, Producers
    private PowerProducerDefinitionImpl myPowerProducerDefinitionImpl;
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

	
	public RPCMImpl(int pID, String pName) {
		super(pID, pName);
        myPowerProducerDefinitionImpl = new PowerProducerDefinitionImpl(this);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
	}

	public boolean isOvertripped() {
		return myOvertripped;
	}

	public boolean isUndertripped() {
		return myUndertripped;
	}
	
	public void overtrip(){
		myOvertripped = true;
	}
	
	public void undertrip(){
		myUndertripped = true;
	}

	public boolean[] getSwitchStatuses() {
		return mySwitches;
	}
	
	public void turnOff(){
		for (int i = 0; i < myPowerProducerDefinitionImpl.getFlowRateCardinality(); i++)
			myPowerProducerDefinitionImpl.setDesiredFlowRate(0f, i);
	}
	
	public void reset() {
        super.reset();
        myOvertripped = false;
        myUndertripped = false;
        myPowerProducerDefinitionImpl.reset();
        myPowerConsumerDefinitionImpl.reset();
		setSwitches(myInitalSwitches);
    }
	
	public void clearTrips(){
    	myUndertripped = false;
    	myOvertripped = false;
    	for (int i = 0; i < myPowerConsumerDefinitionImpl.getDesiredFlowRates().length; i++){
    		float initialDesiredFlowRate = myPowerConsumerDefinitionImpl.getInitialDesiredFlowRates()[i];
    		myPowerConsumerDefinitionImpl.setDesiredFlowRate(initialDesiredFlowRate, i);
    	}
    	for (int i = 0; i < myPowerProducerDefinitionImpl.getDesiredFlowRates().length; i++){
    		float initialDesiredFlowRate = myPowerProducerDefinitionImpl.getInitialDesiredFlowRates()[i];
    		myPowerProducerDefinitionImpl.setDesiredFlowRate(initialDesiredFlowRate, i);
    	}
	}
	
	public void tick() {
        super.tick();
        if (myUndertripped || myOvertripped)
        	return;
        setSwitches(getSwitchStatuses());
        float powerGathered = myPowerConsumerDefinitionImpl.getMostResourceFromStores();
        //check to see if difference is greater than threshold
        if ((powerGathered - TRIP_THRESHOLD) > myPowerConsumerDefinitionImpl.getTotalDesiredFlowRate()){
        	myOvertripped = true;
        	turnOff();
            myLogger.info("RPCM over tripped");
        }
        else if ((powerGathered + TRIP_THRESHOLD) < myPowerConsumerDefinitionImpl.getTotalDesiredFlowRate()){
        	myUndertripped = true;
        	turnOff();
            myLogger.info("RPCM under tripped");
        }
        //if we're pulling more than we can produce, overtrip
        else if ((myPowerProducerDefinitionImpl.getTotalDesiredFlowRate() > (myPowerConsumerDefinitionImpl.getTotalDesiredFlowRate() + TRIP_THRESHOLD))){
        	myOvertripped = true;
        	turnOff();
            myLogger.info("RPCM over tripped");
        }
        myPowerProducerDefinitionImpl.pushResourceToStores(powerGathered);
    }
	
	public void setInitialSwitches(boolean[] switchValues) {
		myInitalSwitches = switchValues;
		if (myInitalSwitches.length != myPowerProducerDefinitionImpl.getFlowRateCardinality()){
			myInitalSwitches = new boolean[myPowerProducerDefinitionImpl.getFlowRateCardinality()];
			Arrays.fill(myInitalSwitches, true);
		}
		setSwitches(myInitalSwitches);
	}
	
	public void setSwitch(int index, boolean switchValue){
		if ((index < 0) || index > mySwitches.length)
			return;
		mySwitches[index] = switchValue;
		//set to max
		if (mySwitches[index])
			myPowerProducerDefinitionImpl.setDesiredFlowRate(myPowerProducerDefinitionImpl.getMaxFlowRate(index), index);
		//set to 0
		else
			myPowerProducerDefinitionImpl.setDesiredFlowRate(0, index);
	}

	public void setSwitches(boolean[] switchValues) {
		mySwitches = switchValues;
		for (int i = 0; i < mySwitches.length; i++){
			setSwitch(i, switchValues[i]);
		}
	}

	public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
	}

	public PowerProducerDefinition getPowerProducerDefinition() {
        return myPowerProducerDefinitionImpl.getCorbaObject();
	}

}
