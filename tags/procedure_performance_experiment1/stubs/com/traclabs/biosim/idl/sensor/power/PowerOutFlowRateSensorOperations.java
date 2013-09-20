package com.traclabs.biosim.idl.sensor.power;

/**
 *	Generated from IDL interface "PowerOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PowerOutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.power.PowerProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.power.PowerProducer getInput();
	int getIndex();
}
