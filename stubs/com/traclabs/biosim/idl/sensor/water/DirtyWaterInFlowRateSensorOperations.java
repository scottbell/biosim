package com.traclabs.biosim.idl.sensor.water;

/**
 *	Generated from IDL interface "DirtyWaterInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
