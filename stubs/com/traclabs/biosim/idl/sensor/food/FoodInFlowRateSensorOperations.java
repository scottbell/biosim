package com.traclabs.biosim.idl.sensor.food;


/**
 * Generated from IDL interface "FoodInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface FoodInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.food.FoodConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.food.FoodConsumer getInput();
	int getIndex();
}
