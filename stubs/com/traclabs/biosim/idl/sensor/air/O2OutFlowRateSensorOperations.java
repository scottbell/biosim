package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "O2OutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface O2OutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.air.O2Producer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.O2Producer getInput();
	int getIndex();
}
