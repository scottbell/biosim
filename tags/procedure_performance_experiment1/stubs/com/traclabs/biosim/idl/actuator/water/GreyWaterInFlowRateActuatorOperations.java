package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "GreyWaterInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GreyWaterInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.water.GreyWaterConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.GreyWaterConsumer getOutput();
	int getIndex();
}
