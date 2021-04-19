package com.traclabs.biosim.idl.sensor.water;


/**
 * Generated from IDL interface "WaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface WaterInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.water.WaterConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.WaterConsumer getInput();
	int getIndex();
}
