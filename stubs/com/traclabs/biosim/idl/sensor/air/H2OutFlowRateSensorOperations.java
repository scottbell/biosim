package com.traclabs.biosim.idl.sensor.air;


/**
 * Generated from IDL interface "H2OutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface H2OutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.air.H2Producer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.H2Producer getInput();
	int getIndex();
}
