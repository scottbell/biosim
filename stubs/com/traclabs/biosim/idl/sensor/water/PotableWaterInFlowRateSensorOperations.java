package com.traclabs.biosim.idl.sensor.water;


/**
 * Generated from IDL interface "PotableWaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PotableWaterInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.water.PotableWaterConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.PotableWaterConsumer getInput();
	int getIndex();
}
