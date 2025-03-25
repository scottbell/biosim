package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.simulation.power.GenericPowerConsumerOperations;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;

public class GenericPowerConsumer extends SimBioModule implements PowerConsumerOperations, GenericPowerConsumerOperations{
	private float myPowerRequired = 0f;
	private float myPowerGathered = 0f;
	
	//Consumers, Producers
    private PowerConsumerDefinition myPowerConsumerDefinition;

	public GenericPowerConsumer(int pID, String pName) {
		super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
	}
	
	public void tick() {
		super.tick();
		getPower();
	}
	
	private void getPower() {
		myPowerGathered= myPowerConsumerDefinition.getResourceFromStores(myPowerRequired);
	}

	public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition.getCorbaObject();
	}
	
	public void reset(){
		super.reset();
		myPowerConsumerDefinition.reset();
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
