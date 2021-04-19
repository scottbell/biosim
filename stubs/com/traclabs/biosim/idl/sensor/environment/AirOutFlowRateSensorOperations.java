package com.traclabs.biosim.idl.sensor.environment;


/**
 * Generated from IDL interface "AirOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
