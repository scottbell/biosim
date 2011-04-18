package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "CO2InFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CO2InFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.CO2Consumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.CO2Consumer getOutput();
	int getIndex();
}
