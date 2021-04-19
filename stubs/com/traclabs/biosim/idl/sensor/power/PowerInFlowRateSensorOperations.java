package com.traclabs.biosim.idl.sensor.power;


/**
 * Generated from IDL interface "PowerInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PowerInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.power.PowerConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.power.PowerConsumer getInput();
	int getIndex();
}
