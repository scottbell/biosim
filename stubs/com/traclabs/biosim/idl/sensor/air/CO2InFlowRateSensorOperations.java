package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "CO2InFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CO2InFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.air.CO2Consumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.CO2Consumer getInput();
	int getIndex();
}
