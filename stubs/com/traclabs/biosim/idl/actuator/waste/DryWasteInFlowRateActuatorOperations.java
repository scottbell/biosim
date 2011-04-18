package com.traclabs.biosim.idl.actuator.waste;

/**
 *	Generated from IDL interface "DryWasteInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface DryWasteInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.waste.DryWasteConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.waste.DryWasteConsumer getOutput();
	int getIndex();
}
