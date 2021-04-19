package com.traclabs.biosim.idl.simulation.power;


/**
 * Generated from IDL interface "GenericPowerConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface GenericPowerConsumerOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations
{
	/* constants */
	/* operations  */
	float getPercentageOfPowerAskedDelivered();
	void setPowerRequired(float pWatts);
}
