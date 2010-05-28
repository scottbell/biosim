package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "WaterInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface WaterInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.water.WaterConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.WaterConsumer getOutput();
	int getIndex();
}
