package com.traclabs.biosim.idl.sensor.air;


/**
 * Generated from IDL interface "O2OutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
