package com.traclabs.biosim.idl.sensor.water;

/**
 *	Generated from IDL interface "PotableWaterInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
