package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "O2OutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface O2OutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.O2Producer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.O2Producer getOutput();
	int getIndex();
}
