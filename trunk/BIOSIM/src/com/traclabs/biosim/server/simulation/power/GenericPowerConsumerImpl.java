package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.simulation.power.GenericPowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class GenericPowerConsumerImpl extends SimBioModuleImpl implements PowerConsumerOperations, GenericPowerConsumerOperations{
	 //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

	public GenericPowerConsumerImpl(int pID, String pName) {
		super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
	}
	
	public void tick() {
		super.tick();
		getPower();
	}
	
	private void getPower() {
		float powerGathered = myPowerConsumerDefinitionImpl
				.getMostResourceFromStores();
	}

	public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
	}
	
	public void reset(){
		super.reset();
		myPowerConsumerDefinitionImpl.reset();
	}

}
