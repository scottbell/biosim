package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "FoodOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface FoodOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.food.FoodProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.food.FoodProducer getOutput();
	int getIndex();
}
