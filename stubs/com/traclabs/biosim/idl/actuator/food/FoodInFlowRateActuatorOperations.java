package com.traclabs.biosim.idl.actuator.food;


/**
 * Generated from IDL interface "FoodInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface FoodInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.food.FoodConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.food.FoodConsumer getOutput();
	int getIndex();
}
