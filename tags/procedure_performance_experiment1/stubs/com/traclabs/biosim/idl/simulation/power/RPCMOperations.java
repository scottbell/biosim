package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "RPCM"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface RPCMOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations , com.traclabs.biosim.idl.simulation.power.PowerProducerOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.power.RPCMSwitchState getSwitchState();
	com.traclabs.biosim.idl.simulation.power.RPCMArmedStatus getArmedStatus();
	void setSwitchState(com.traclabs.biosim.idl.simulation.power.RPCMSwitchState state);
	void setArmedStatus(com.traclabs.biosim.idl.simulation.power.RPCMArmedStatus state);
}
