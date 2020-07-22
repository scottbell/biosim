/**
 * 
 */
package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerProducerOperations;
import com.traclabs.biosim.idl.simulation.power.RPCMArmedStatus;
import com.traclabs.biosim.idl.simulation.power.RPCMOperations;
import com.traclabs.biosim.idl.simulation.power.RPCMSwitchState;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * @author scott
 *
 */
public class RPCMImpl extends SimBioModuleImpl implements RPCMOperations, PowerConsumerOperations, PowerProducerOperations {
	 //Consumers, Producers
    private PowerProducerDefinitionImpl myPowerProducerDefinitionImpl;
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;
    private RPCMSwitchState mySwitchState = RPCMSwitchState.closed;
    private RPCMArmedStatus myArmedStatus = RPCMArmedStatus.enabled;

	
	public RPCMImpl(int pID, String pName) {
		super(pID, pName);
        myPowerProducerDefinitionImpl = new PowerProducerDefinitionImpl(this);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
	}

	
	public void reset() {
        super.reset();
        myPowerProducerDefinitionImpl.reset();
        myPowerConsumerDefinitionImpl.reset();
    }
	
	
	public void tick() {
        super.tick();
    	float powerGathered;
        if (getSwitchState() == RPCMSwitchState.closed)
        	powerGathered = myPowerConsumerDefinitionImpl.getMostResourceFromStores();
        else
        	powerGathered = myPowerConsumerDefinitionImpl.getResourceFromStores(0);
    	myPowerProducerDefinitionImpl.pushResourceToStores(powerGathered);
    }
	
	public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
	}

	public PowerProducerDefinition getPowerProducerDefinition() {
        return myPowerProducerDefinitionImpl.getCorbaObject();
	}


	public RPCMSwitchState getSwitchState() {
		return mySwitchState;
	}


	public RPCMArmedStatus getArmedStatus() {
		return myArmedStatus;
	}

	public void setSwitchState(RPCMSwitchState state) {
		if (getArmedStatus() == RPCMArmedStatus.enabled)
			this.mySwitchState = state;
		else
			myLogger.info(getModuleName() + ": attempted to change switch state when not armed");
	}


	public void setArmedStatus(RPCMArmedStatus state) {
		this.myArmedStatus = state;
	}

	@Override
	protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "Broken";
    }
    
	@Override
    protected void performMalfunctions() {
		for (Malfunction malfunction : myMalfunctions.values()) {
			malfunction.setPerformed(true);
		}
		if (myMalfunctions.values().size() > 0) {
			myPowerConsumerDefinitionImpl.malfunction();
			myPowerProducerDefinitionImpl.malfunction();
		}
    }

}
