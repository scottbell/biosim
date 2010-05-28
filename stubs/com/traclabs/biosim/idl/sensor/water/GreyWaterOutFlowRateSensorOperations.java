package com.traclabs.biosim.idl.sensor.water;

/**
 *	Generated from IDL interface "GreyWaterOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GreyWaterOutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.water.GreyWaterProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.GreyWaterProducer getInput();
	int getIndex();
}
