package com.traclabs.biosim.idl.sensor.air;


/**
 * Generated from IDL interface "H2InFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface H2InFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.air.H2Consumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.H2Consumer getInput();
	int getIndex();
}
