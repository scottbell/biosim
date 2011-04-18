package com.traclabs.biosim.idl.sensor.power;

/**
 *	Generated from IDL interface "PowerInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
