package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "NitrogenInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface NitrogenInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.NitrogenConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.NitrogenConsumer getOutput();
	int getIndex();
}
