package com.traclabs.biosim.idl.sensor.water;


/**
 * Generated from IDL interface "DirtyWaterOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface DirtyWaterOutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.water.DirtyWaterProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.DirtyWaterProducer getInput();
	int getIndex();
}
