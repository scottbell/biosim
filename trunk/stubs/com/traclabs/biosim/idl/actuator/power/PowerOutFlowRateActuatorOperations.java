package com.traclabs.biosim.idl.actuator.power;

/**
 *	Generated from IDL interface "PowerOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PowerOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.power.PowerProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.power.PowerProducer getOutput();
	int getIndex();
}
