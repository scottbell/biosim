package com.traclabs.biosim.idl.sensor.food;

/**
 *	Generated from IDL interface "BiomassInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface BiomassInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.food.BiomassConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.food.BiomassConsumer getInput();
	int getIndex();
}
