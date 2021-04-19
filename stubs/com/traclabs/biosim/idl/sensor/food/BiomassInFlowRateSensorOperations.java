package com.traclabs.biosim.idl.sensor.food;


/**
 * Generated from IDL interface "BiomassInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface BiomassInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.food.BiomassConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.food.BiomassConsumer getInput();
	int getIndex();
}
