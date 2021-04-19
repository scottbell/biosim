package com.traclabs.biosim.idl.sensor.food;


/**
 * Generated from IDL interface "FoodOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface FoodOutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.food.FoodProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.food.FoodProducer getInput();
	int getIndex();
}
