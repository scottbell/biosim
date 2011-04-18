package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "FoodInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
