package com.traclabs.biosim.idl.sensor.water;


/**
 * Generated from IDL interface "GreyWaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface GreyWaterInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.water.GreyWaterConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.GreyWaterConsumer getInput();
	int getIndex();
}
