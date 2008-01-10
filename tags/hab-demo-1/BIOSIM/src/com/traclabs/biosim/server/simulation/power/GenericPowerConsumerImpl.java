package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.simulation.power.GenericPowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class GenericPowerConsumerImpl extends SimBioModuleImpl implements PowerConsumerOperations, GenericPowerConsumerOperations{
	private float myPowerRequired = 0f;
	private float myPowerGathered = 0f;
	
	//Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

	public GenericPowerConsumerImpl(int pID, String pName) {
		super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
	}
	
	public void tick() {
		super.tick();
		getPower();
	}
	
	private void getPower() {
		myPowerGathered= myPowerConsumerDefinitionImpl.getResourceFromStores(myPowerRequired);
	}

	public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
	}
	
	public void reset(){
		super.reset();
		myPowerConsumerDefinitionImpl.reset();
		myPowerGathered = 0f;
	}

	public float getPercentageOfPowerAskedDelivered() {
		if (myPowerRequired <= 0)
			return 0f;
		else
			return myPowerGathered / myPowerRequired;
	}

	public void setPowerRequired(float pWatts) {
		myPowerRequired = pWatts;
	}

}
