package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "MethaneOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface MethaneOutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.air.MethaneProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.MethaneProducer getInput();
	int getIndex();
}
