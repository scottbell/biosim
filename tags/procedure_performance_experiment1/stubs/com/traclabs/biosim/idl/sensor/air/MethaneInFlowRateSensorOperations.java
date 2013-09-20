package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "MethaneInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface MethaneInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.air.MethaneConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.MethaneConsumer getInput();
	int getIndex();
}
