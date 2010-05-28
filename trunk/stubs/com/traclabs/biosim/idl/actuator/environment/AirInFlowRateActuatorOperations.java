package com.traclabs.biosim.idl.actuator.environment;

/**
 *	Generated from IDL interface "AirInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface AirInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.environment.AirConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.environment.AirConsumer getOutput();
	int getIndex();
}
