package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "AirOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface AirOutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.environment.AirProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.environment.AirProducer getInput();
	int getIndex();
}
