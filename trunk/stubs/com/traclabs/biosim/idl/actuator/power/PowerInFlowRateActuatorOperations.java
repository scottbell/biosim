package com.traclabs.biosim.idl.actuator.power;

/**
 *	Generated from IDL interface "PowerInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PowerInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.power.PowerConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.power.PowerConsumer getOutput();
	int getIndex();
}
