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
	
	 //Consumers, Producers
    private PowerProducerDefinitionImpl myPowerProducerDefinitionImpl;
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

	
	public RPCMImpl(int pID, String pName) {
		super(pID, pName);
        myPowerProducerDefinitionImpl = new PowerProducerDefinitionImpl();
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
	}

	public boolean isOverTripped() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isUnderTripped() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean[] getSwitchStatuses() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSwitches(boolean[] switchStatuses) {
		// TODO Auto-generated method stub

	}

	public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
	}

	public PowerProducerDefinition getPowerProducerDefinition() {
        return myPowerProducerDefinitionImpl.getCorbaObject();
	}

}
