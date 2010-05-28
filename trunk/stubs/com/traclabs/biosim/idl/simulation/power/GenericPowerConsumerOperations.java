package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "GenericPowerConsumer"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GenericPowerConsumerOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations
{
	/* constants */
	/* operations  */
	float getPercentageOfPowerAskedDelivered();
	void setPowerRequired(float pWatts);
}
