package com.traclabs.biosim.idl.sensor.environment;


/**
 * Generated from IDL interface "AirInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
