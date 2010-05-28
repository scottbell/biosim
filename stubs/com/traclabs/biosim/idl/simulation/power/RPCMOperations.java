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
	boolean isOvertripped();
	boolean isUndertripped();
	void overtrip();
	void undertrip();
	void clearTrips();
	boolean[] getSwitchStatuses();
	void setSwitches(boolean[] switchValues);
	void setSwitch(int index, boolean switchValue);
	void setInitialSwitches(boolean[] switchValues);
}
