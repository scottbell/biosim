package com.traclabs.biosim.idl.sensor.water;


/**
 * Generated from IDL interface "DirtyWaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface DirtyWaterInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumer getInput();
	int getIndex();
}
