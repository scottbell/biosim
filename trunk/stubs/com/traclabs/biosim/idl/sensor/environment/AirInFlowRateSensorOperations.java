package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "AirInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface AirInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.environment.AirConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.environment.AirConsumer getInput();
	int getIndex();
}
